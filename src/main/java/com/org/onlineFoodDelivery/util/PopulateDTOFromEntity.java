package com.org.onlineFoodDelivery.util;

import com.org.onlineFoodDelivery.dto.DishDTO;
import com.org.onlineFoodDelivery.dto.OrderDTO;
import com.org.onlineFoodDelivery.dto.RestaurantDTO;
import com.org.onlineFoodDelivery.entity.Dishes;
import com.org.onlineFoodDelivery.entity.OrderHistory;
import com.org.onlineFoodDelivery.entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class PopulateDTOFromEntity {

    public RestaurantDTO populateRestaurant(Restaurant restaurant) {
        RestaurantDTO dto= new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setDeliveringStatus(restaurant.getDeliveringStatus());
        return dto;
    }

    public List<DishDTO> populateDishes(List<Dishes> dishes) {
        List<DishDTO> dishList = new ArrayList<>();
        for(Dishes dish : dishes){
            DishDTO dto = new DishDTO();
            dto.setId(dish.getId());
            dto.setName(dish.getName());
            dto.setPrice(dish.getPrice());
            dto.setAvailable(dish.getAvailable());
            dishList.add(dto);
        }
        return dishList;
    }

    public OrderDTO populateOrderDTO(OrderHistory order){
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setOrderTime(order.getOrderTime());
        dto.setOrderTotal(order.getOrderTotal());
        dto.setPaymentStatus(order.getPaymentStatus().getStatus());
        List<DishDTO> dishes = populateDishes(order.getDishes());
        dto.setDishes(dishes);
        RestaurantDTO restaurant = populateRestaurant(order.getRestaurant());
        dto.setRestaurant(restaurant);
        dto.setRestaurantConfirmation(order.getConfirmation().getStatus());
        return dto;
    }

    public List<OrderDTO> populateOrderDTOList(List<OrderHistory> orders){
        List<OrderDTO> orderList = new ArrayList<>();
        for(OrderHistory order : orders){
            OrderDTO dto = populateOrderDTO(order);
            orderList.add(dto);
        }
        return orderList;
    }
}
