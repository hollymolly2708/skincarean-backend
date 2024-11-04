package com.skincarean.skincarean.service.payment_method;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.entity.PaymentMethod;
import com.skincarean.skincarean.model.payment_method.request.CreatePaymentMethodRequest;
import com.skincarean.skincarean.model.payment_method.request.UpdatePaymentMethodRequest;
import com.skincarean.skincarean.model.payment_method.response.PaymentMethodResponse;
import com.skincarean.skincarean.repository.PaymentMethodRepository;
import com.skincarean.skincarean.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private ValidationService validationService;

    @Override
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


    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodResponse> getAllPaymentMethod() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        return paymentMethods.stream().map(paymentMethod -> {
            return PaymentMethodResponse.builder().id(paymentMethod.getId()).name(paymentMethod.getName()).image(paymentMethod.getImage()).description(paymentMethod.getDescription()).build();
        }).toList();
    }


    @Override
    @Transactional
    public void deletePaymentMethod(Admin admin, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));
        paymentMethodRepository.deleteById(paymentMethod.getId());
    }


    @Override
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
