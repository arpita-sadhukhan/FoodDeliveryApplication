package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.OrderDTO;

import java.util.List;

public interface IOrderHistoryService {

    OrderDTO placeOrder(long userId, long restaurantId);
    OrderDTO confirmOrder(OrderDTO inputOrder, long orderId);
    List<OrderDTO> getPendingOrders(long restaurantId);
}
