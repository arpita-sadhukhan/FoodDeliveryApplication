package com.org.onlineFoodDelivery.dto;

import com.org.onlineFoodDelivery.entity.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
public class CuisineDTO {

    private long id;
    private List<RestaurantDTO> restaurants;
    private String name;
}
