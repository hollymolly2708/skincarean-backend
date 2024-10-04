package com.skincareMall.skincareMall.controller.payment_method;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.payment_method.request.CreatePaymentMethodRequest;
import com.skincareMall.skincareMall.model.payment_method.request.UpdatePaymentMethodRequest;
import com.skincareMall.skincareMall.model.payment_method.response.PaymentMethodResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.payment_method.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping(path = "/api/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> createPaymentMethod(Admin admin, @RequestBody CreatePaymentMethodRequest request){
        paymentMethodService.createPaymentMethod(admin,request);
        return WebResponse.<String>builder().data("Payment method has succesfully created").build();
    }

    @GetMapping(path = "/api/payment-methods",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<PaymentMethodResponse>> getAllPaymentMethods(){
        List<PaymentMethodResponse> allPaymentMethod = paymentMethodService.getAllPaymentMethod();
        return WebResponse.<List<PaymentMethodResponse>>builder().data(allPaymentMethod).build();
    }

    @DeleteMapping(path = "/api/payment-methods/{paymentMethodId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deletePaymentMethod(Admin admin, @PathVariable("paymentMethodId") Long paymentMethodId){
        paymentMethodService.deletePaymentMethod(admin, paymentMethodId);
        return WebResponse.<String>builder().data("Payment method has successfully deleted").build();
    }

    @PatchMapping (path = "/api/payment-methods/{paymentMethodId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<PaymentMethodResponse> updatePaymentMethod(Admin admin, @PathVariable("paymentMethodId") Long paymentMethodId, @RequestBody UpdatePaymentMethodRequest updatePaymentMethodRequest){
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.updatePaymentMethod(admin, paymentMethodId, updatePaymentMethodRequest);
        return WebResponse.<PaymentMethodResponse>builder().data(paymentMethodResponse).build();
    }
}
