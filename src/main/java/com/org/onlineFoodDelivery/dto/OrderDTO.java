package com.org.onlineFoodDelivery.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private long orderId;
    private List<DishDTO> dishes;
    private RestaurantDTO restaurant;
    private double orderTotal;
    private String paymentStatus;
    private String restaurantConfirmation;
    private Date orderTime;
}
