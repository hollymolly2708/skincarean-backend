package com.skincarean.skincarean.controller.payment;

import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.service.payment_confirm.PaymentConfirmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    PaymentConfirmServiceImpl paymentConfirmServiceImpl;

    @PostMapping(path = "/api/payments/confirm", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> confirmPayments(@RequestParam String paymentCode) {
        paymentConfirmServiceImpl.confirmPayment(paymentCode);
        return WebResponse.<String>builder().data("Pembayaran berhasil dibayarkan").build();
    }

}
