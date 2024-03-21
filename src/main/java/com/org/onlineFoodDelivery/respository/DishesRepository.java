package com.org.onlineFoodDelivery.respository;

import com.org.onlineFoodDelivery.entity.Dishes;
import com.org.onlineFoodDelivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DishesRepository extends JpaRepository<Dishes, Long> {

    Optional<List<Dishes>> findByRestaurantIdAndAvailable(long restaurant_id, Boolean available);
    @Query(value = "select distinct restaurant from Dishes where name like %:name%")
    Optional<List<Dishes>> findByName(String name);

    Optional<Dishes> findByIdAndRestaurantIdAndAvailable(Long id, long restaurantId, boolean available);
}
