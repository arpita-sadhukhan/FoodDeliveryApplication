package com.org.onlineFoodDelivery.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<Dishes> dishes;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @Column(name = "cart_value", nullable = false)
    private double cartValue;

    @CreationTimestamp
    private Date created_date;

    public void addDish(Dishes dish){
        if(dishes == null)
            dishes = new ArrayList<>();
        dishes.add(dish);
    }

}
