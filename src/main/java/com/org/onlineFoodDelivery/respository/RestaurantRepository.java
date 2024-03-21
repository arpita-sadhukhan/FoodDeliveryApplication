package com.org.onlineFoodDelivery.respository;

import com.org.onlineFoodDelivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "from Restaurant where name like %:name%")
    Optional<List<Restaurant>> findByName(String name);

    Optional<List<Restaurant>> findRestaurantByCuisinesId(Long cuisineId);
}
