package com.skincareMall.skincareMall.service.google_auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.google_auth.request.TokenRequest;
import com.skincareMall.skincareMall.model.google_auth.response.VerificationResponse;
import com.skincareMall.skincareMall.model.user.response.UserResponse;
import com.skincareMall.skincareMall.repository.UserRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoogleAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;
    private static final String CLIENT_ID = "671324987024-bc5r4pm5vsmuers2otis2h5bot0tj0bb.apps.googleusercontent.com";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();


    @Transactional
    public VerificationResponse verifyToken(TokenRequest tokenRequest) {
        validationService.validate(tokenRequest);
        String idTokenString = tokenRequest.getToken();

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

                Optional<User> userByEmail = userRepository.findByEmail(email);

                User user = userByEmail.orElseGet(() -> {
                            User addUser = new User();
                            addUser.setFullName(name);
                            addUser.setUsernameUser(email);
                            addUser.setEmail(email);
                            addUser.setCreatedAt(Utilities.changeFormatToTimeStamp());
                            addUser.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
                            addUser.setToken(UUID.randomUUID().toString());
                            addUser.setTokenCreatedAt(System.currentTimeMillis());
                            addUser.setTokenExpiredAt(Utilities.next30days());
                            if (pictureUrl != null) {
                                addUser.setPhotoProfile(pictureUrl);
                            }

                            userRepository.save(addUser);
                            return addUser;

                        }
                );

                return VerificationResponse.builder().valid(true)
                        .user(UserResponse
                                .builder()
                                .email(user.getEmail())
                                .profilePicture(user.getPhotoProfile())
                                .fullName(user.getFullName())
                                .token(user.getToken())
                                .phone(user.getPhone())
                                .lastUpdatedAt(user.getLastUpdatedAt())
                                .createdAt(user.getCreatedAt())
                                .address(user.getAddress())
                                .tokenExpiredAt(Utilities.changeFormatToTimeStamp(user.getTokenExpiredAt()))
                                .tokenCreatedAt(Utilities.changeFormatToTimeStamp(user.getTokenCreatedAt()))
                                .username(user.getEmail())


                                .build())
                        .build();

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User dengan alamat email tersebut tidak ditemukan");
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return VerificationResponse.builder().valid(false).error("Masalah keamanan saat memverifikasi akun").build();
        }
        catch ( IOException e){
            e.printStackTrace();
            return VerificationResponse.builder().valid(false).error("Masalah I/O saat memverifikasi akun").build();
        }
        catch (Exception e){
            e.printStackTrace();
            return VerificationResponse.builder().valid(false).error("Kesalahan tidak terduga saat memverifikasi akun").build();
        }
    }
}
