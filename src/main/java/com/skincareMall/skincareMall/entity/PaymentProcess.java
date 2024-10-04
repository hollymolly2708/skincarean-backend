package com.skincareMall.skincareMall.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_process")
public class PaymentProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "payment_code")
    private String paymentCode;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "paid_date")
    private Timestamp paidDate;
    @Column(name = "total_paid")
    private BigDecimal totalPaid;
    @ManyToOne
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    private PaymentMethod paymentMethod;
    @OneToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order order;
}
