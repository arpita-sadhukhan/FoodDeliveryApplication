package com.org.onlineFoodDelivery.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_history")
@Data
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<Dishes> dishes;

    @ManyToOne
    private User user;

    @ManyToOne
    private Restaurant restaurant;

    @Column(name = "order_total")
    private double orderTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    private Date orderTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "restaurant_confirmation")
    private RestaurantConfirmation confirmation;

}

