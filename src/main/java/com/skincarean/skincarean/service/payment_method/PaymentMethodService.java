package com.skincarean.skincarean.service.payment_method;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.payment_method.request.CreatePaymentMethodRequest;
import com.skincarean.skincarean.model.payment_method.request.UpdatePaymentMethodRequest;
import com.skincarean.skincarean.model.payment_method.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    void createPaymentMethod(Admin admin, CreatePaymentMethodRequest createPaymentMethodRequest);
    List<PaymentMethodResponse> getAllPaymentMethod();
    void deletePaymentMethod(Admin admin, Long paymentMethodId);
    PaymentMethodResponse updatePaymentMethod(Admin admin, Long paymentMethodId, UpdatePaymentMethodRequest request);
}
