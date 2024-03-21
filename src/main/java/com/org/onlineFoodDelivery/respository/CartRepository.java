package com.org.onlineFoodDelivery.respository;

import com.org.onlineFoodDelivery.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(long userId);
    Optional<Cart> findByUserIdAndRestaurantId(long userId, long restaurantId);
}
