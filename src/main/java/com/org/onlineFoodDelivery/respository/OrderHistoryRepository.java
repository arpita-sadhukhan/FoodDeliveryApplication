package com.org.onlineFoodDelivery.respository;

import com.org.onlineFoodDelivery.entity.OrderHistory;
import com.org.onlineFoodDelivery.entity.RestaurantConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    Optional<List<OrderHistory>> findByRestaurantIdAndConfirmation(long restaurantId, RestaurantConfirmation confirmation);
}
