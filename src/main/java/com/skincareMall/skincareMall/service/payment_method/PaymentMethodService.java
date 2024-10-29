package com.skincareMall.skincareMall.service.payment_method;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.payment_method.request.CreatePaymentMethodRequest;
import com.skincareMall.skincareMall.model.payment_method.request.UpdatePaymentMethodRequest;
import com.skincareMall.skincareMall.model.payment_method.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    void createPaymentMethod(Admin admin, CreatePaymentMethodRequest createPaymentMethodRequest);
    List<PaymentMethodResponse> getAllPaymentMethod();
    void deletePaymentMethod(Admin admin, Long paymentMethodId);
    PaymentMethodResponse updatePaymentMethod(Admin admin, Long paymentMethodId, UpdatePaymentMethodRequest request);
}
