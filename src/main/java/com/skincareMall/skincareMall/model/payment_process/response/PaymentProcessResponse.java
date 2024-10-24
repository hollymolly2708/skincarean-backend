package com.skincareMall.skincareMall.model.payment_process.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentProcessResponse {

    private String paymentCode;
    private String paymentStatus;
    private Long paymentMethodId;
    private String paymentMethodName;
    private Timestamp paidDate;
    private BigDecimal totalPaid;
}
