package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.CartDTO;

public interface ICartService {

    CartDTO addToCart(CartDTO dto, long userId, long restaurantId);

    CartDTO viewCart(long userId, long restaurantId);
}
