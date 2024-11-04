package com.skincarean.skincarean.service.user;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.google_auth.request.GoogleLoginTokenRequest;
import com.skincarean.skincarean.model.user.request.LoginUserRequest;
import com.skincarean.skincarean.model.user.request.RegisterUserRequest;
import com.skincarean.skincarean.model.user.response.TokenResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.repository.UserRepository;
import com.skincarean.skincarean.security.BCrypt;
import com.skincarean.skincarean.utils.Utilities;
import com.skincarean.skincarean.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import static com.skincarean.skincarean.utils.Utilities.changeFormatToTimeStamp;
import static com.skincarean.skincarean.utils.Utilities.next30days;


@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;

    private static final String CLIENT_ID = "671324987024-bc5r4pm5vsmuers2otis2h5bot0tj0bb.apps.googleusercontent.com";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Override
    @Transactional
    public WebResponse<String> registerUser(RegisterUserRequest request) {
        validationService.validate(request);
        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username sudah terdaftar");
        }
        if (!Objects.equals(request.getConfirmPassword(), request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password dan konfirmasi password tidak sama");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email sudah terdaftar");
        }


        User user = new User();
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPasswordUser(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setUsernameUser(request.getUsername());
        user.setCreatedAt(changeFormatToTimeStamp());
        user.setLastUpdatedAt(changeFormatToTimeStamp());
        user.setAddress(request.getAddress());

        try {
            userRepository.save(user);
            return WebResponse.<String>builder().isSuccess(true).data("Register berhasil").build();
        } catch (Exception e) {
            return WebResponse.<String>builder().isSuccess(false).errors("Register gagal").build();
        }


    }
    @Override
    @Transactional
    public WebResponse<TokenResponse> login(LoginUserRequest loginUserRequest) {
        validationService.validate(loginUserRequest);
        User user = userRepository.findById(loginUserRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah"));


        if (user.getPasswordUser() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "username atau password salah");
        }
        if (BCrypt.checkpw(loginUserRequest.getPassword(), user.getPasswordUser())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30days());
            user.setTokenCreatedAt(System.currentTimeMillis());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah");

        }

        //simpan token kedalam database
        try {
            userRepository.save(user);
            return WebResponse.<TokenResponse>builder().isSuccess(true).data(TokenResponse.builder().token(user.getToken()).tokenCreatedAt(user.getTokenCreatedAt()).tokenExpiredAt(user.getTokenExpiredAt()).build()).build();

        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.<TokenResponse>builder().isSuccess(false).errors("Kesalahan saat memproses login").build();
        }

    }
    @Override
    @Transactional
    public WebResponse<String> logoutUser(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);
        user.setTokenCreatedAt(null);

        //hapus session dari database
        try {
            userRepository.save(user);
            return WebResponse.<String>builder().isSuccess(true).data("Logout berhasil").build();
        } catch (Exception e) {
            return WebResponse.<String>builder().isSuccess(false).errors("Gagal menghapus session").build();
        }


    }

    @Override
    @Transactional
    public WebResponse<TokenResponse> verifyToken(GoogleLoginTokenRequest googleLoginTokenRequest) {
        validationService.validate(googleLoginTokenRequest);
        String idTokenString = googleLoginTokenRequest.getToken();

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");

                User userByEmail = userRepository.findByEmail(email).orElse(null);

                if (userByEmail == null) {
                    User addUser = new User();
                    addUser.setFullName(name);
                    addUser.setToken(UUID.randomUUID().toString());
                    addUser.setUsernameUser(email);
                    addUser.setEmail(email);
                    addUser.setCreatedAt(Utilities.changeFormatToTimeStamp());
                    addUser.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
                    addUser.setTokenCreatedAt(System.currentTimeMillis());
                    addUser.setTokenExpiredAt(Utilities.next30days());
                    if (pictureUrl != null) {
                        addUser.setPhotoProfile(pictureUrl);
                    }
                    userRepository.save(addUser);
                } else {
                    userByEmail.setToken(UUID.randomUUID().toString());
                    userByEmail.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
                    userByEmail.setTokenCreatedAt(System.currentTimeMillis());
                    userByEmail.setTokenExpiredAt(Utilities.next30days());
                    userRepository.save(userByEmail);
                }



                return WebResponse.<TokenResponse>builder()
                        .data(TokenResponse
                                .builder()
                                .token(userByEmail.getToken())
                                .tokenExpiredAt(next30days())
                                .tokenCreatedAt(System.currentTimeMillis())
                                .build())
                        .isSuccess(true)
                        .build();

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User dengan alamat email tersebut tidak ditemukan");
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return WebResponse.<TokenResponse>builder().errors("Masalah keamanan saat memverifikasi akun").isSuccess(false).build();
        } catch (com.google.common.io.BaseEncoding.DecodingException e) {
            return WebResponse.<TokenResponse>builder().errors("Masalah I/O saat memverifikasi akun").isSuccess(false).build();
        } catch (IOException e) {
            e.printStackTrace();
            return WebResponse.<TokenResponse>builder().errors("Masalah I/O saat memverifikasi akun").isSuccess(false).build();
        } catch (Exception e) {
            return WebResponse.<TokenResponse>builder().errors("Kesalahan server saat memverifikasi akun").isSuccess(false).build();
        }
    }
}

