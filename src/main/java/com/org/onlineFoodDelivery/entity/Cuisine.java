package com.org.onlineFoodDelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuisine")
@Getter
@Setter
public class Cuisine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(mappedBy = "cuisines", fetch = FetchType.LAZY)
    private List<Restaurant> restaurants;

    @Column(name = "name")
    private String name;

    public void addRestaurant(Restaurant restaurant){
        if(null == restaurants){
            restaurants = new ArrayList<>();
        }
        restaurants.add(restaurant);
    }

}
