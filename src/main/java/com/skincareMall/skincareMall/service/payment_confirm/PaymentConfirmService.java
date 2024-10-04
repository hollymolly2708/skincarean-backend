package com.skincareMall.skincareMall.service.payment_confirm;

import com.skincareMall.skincareMall.entity.Order;
import com.skincareMall.skincareMall.entity.PaymentProcess;
import com.skincareMall.skincareMall.repository.OrderRepository;
import com.skincareMall.skincareMall.repository.PaymentProcessRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentConfirmService {
    @Autowired
    private PaymentProcessRepository paymentProcessRepository;
    @Autowired
    private OrderRepository orderRepository;

    public void confirmPayment(String paymentCode) {
        PaymentProcess paymentProcess = paymentProcessRepository.findByPaymentCode(paymentCode).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kode pembayaran tidak ditemukan"));
        Order order = orderRepository.findById(paymentProcess.getOrder().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        paymentProcess.setPaymentStatus("Lunas");
        paymentProcess.setPaidDate(Utilities.changeFormatToTimeStamp());
        paymentProcess.setTotalPaid(order.getTotalPrice());
        paymentProcessRepository.save(paymentProcess);
    }
}
