package com.skincareMall.skincareMall.service.payment_method;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.PaymentMethod;
import com.skincareMall.skincareMall.model.payment_method.request.CreatePaymentMethodRequest;
import com.skincareMall.skincareMall.model.payment_method.request.UpdatePaymentMethodRequest;
import com.skincareMall.skincareMall.model.payment_method.response.PaymentMethodResponse;
import com.skincareMall.skincareMall.repository.PaymentMethodRepository;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private ValidationService validationService;

    @Transactional
    public void createPaymentMethod(Admin admin, CreatePaymentMethodRequest createPaymentMethodRequest) {
        validationService.validate(createPaymentMethodRequest);

        if (paymentMethodRepository.existsByName(createPaymentMethodRequest.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Metode pembayaran sudah terdaftar");
        }
        PaymentMethod paymentMethod2 = new PaymentMethod();
        paymentMethod2.setName(createPaymentMethodRequest.getName());
        paymentMethod2.setDescription(createPaymentMethodRequest.getDescription());
        paymentMethod2.setImage(createPaymentMethodRequest.getImage());
        paymentMethodRepository.save(paymentMethod2);

    }

    @Transactional(readOnly = true)
    public List<PaymentMethodResponse> getAllPaymentMethod() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        return paymentMethods.stream().map(paymentMethod -> {
            return PaymentMethodResponse.builder().id(paymentMethod.getId()).name(paymentMethod.getName()).image(paymentMethod.getImage()).description(paymentMethod.getDescription()).build();
        }).toList();
    }

    @Transactional
    public void deletePaymentMethod(Admin admin, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));
        paymentMethodRepository.deleteById(paymentMethod.getId());
    }

    @Transactional
    public PaymentMethodResponse updatePaymentMethod(Admin admin, Long paymentMethodId, UpdatePaymentMethodRequest request) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));

        if(Objects.nonNull(request.getImage())){
            paymentMethod.setImage(request.getImage());
        }

        if(Objects.nonNull(request.getName())){
            paymentMethod.setName(request.getName());
        }

        if(Objects.nonNull(request.getDescription())){
            paymentMethod.setDescription(request.getDescription());
        }


        paymentMethodRepository.save(paymentMethod);

       return PaymentMethodResponse.builder()
                .name(paymentMethod.getName())
                .id(paymentMethod.getId())
                .build();
    }
}
