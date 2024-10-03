package com.skincareMall.skincareMall.service.payment_method;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.PaymentMethod;
import com.skincareMall.skincareMall.model.payment_method.request.CreatePaymentMethodRequest;
import com.skincareMall.skincareMall.model.payment_method.request.UpdatePaymentMethodRequest;
import com.skincareMall.skincareMall.model.payment_method.response.PaymentMethodResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.repository.PaymentMethodRepository;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private ValidationService validationService;

    public void createPaymentMethod(Admin admin, CreatePaymentMethodRequest createPaymentMethodRequest) {
        validationService.validate(createPaymentMethodRequest);

        if (paymentMethodRepository.existsByName(createPaymentMethodRequest.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment already registered");
        }
        PaymentMethod paymentMethod2 = new PaymentMethod();
        paymentMethod2.setName(createPaymentMethodRequest.getName());
        paymentMethod2.setImage(createPaymentMethodRequest.getImage());
        paymentMethodRepository.save(paymentMethod2);

    }

    public List<PaymentMethodResponse> getAllPaymentMethod() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        return paymentMethods.stream().map(paymentMethod -> {
            return PaymentMethodResponse.builder().id(paymentMethod.getId()).name(paymentMethod.getName()).build();
        }).toList();
    }

    public void deletePaymentMethod(Admin admin, Long paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment is'nt found"));
        paymentMethodRepository.deleteById(paymentMethod.getId());
    }

    public PaymentMethodResponse updatePaymentMethod(Admin admin, Long paymentMethodId, UpdatePaymentMethodRequest request) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment is'nt found"));
        paymentMethod.setName(request.getName());
        paymentMethodRepository.save(paymentMethod);

       return PaymentMethodResponse.builder()
                .name(paymentMethod.getName())
                .id(paymentMethod.getId())
                .build();
    }
}
