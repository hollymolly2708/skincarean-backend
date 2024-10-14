package com.skincareMall.skincareMall.service.payment_confirm;

import com.skincareMall.skincareMall.entity.Order;
import com.skincareMall.skincareMall.entity.OrderItem;
import com.skincareMall.skincareMall.entity.PaymentProcess;
import com.skincareMall.skincareMall.repository.OrderItemRepository;
import com.skincareMall.skincareMall.repository.OrderRepository;
import com.skincareMall.skincareMall.repository.PaymentProcessRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class PaymentConfirmService {
    @Autowired
    private PaymentProcessRepository paymentProcessRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void confirmPayment(String paymentCode) {
        PaymentProcess paymentProcess = paymentProcessRepository.findByPaymentCode(paymentCode).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kode pembayaran tidak ditemukan"));


        if (Objects.equals(paymentProcess.getPaymentStatus(), "Lunas")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kode pembayaran tidak valid lagi");
        }
        Order order = orderRepository.findById(paymentProcess.getOrder().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order tidak ditemukan"));

        paymentProcess.setPaymentStatus("Lunas");
        order.setOrderStatus("Selesai");
        paymentProcess.setPaidDate(Utilities.changeFormatToTimeStamp());
        paymentProcess.setTotalPaid(order.getTotalPrice());
        orderRepository.save(order);
        paymentProcessRepository.save(paymentProcess);
    }
}
