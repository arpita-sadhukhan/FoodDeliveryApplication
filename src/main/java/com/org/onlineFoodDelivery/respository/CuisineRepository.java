package com.org.onlineFoodDelivery.respository;

import com.org.onlineFoodDelivery.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

    Optional<Cuisine> findByName(String name);
}
