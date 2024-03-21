package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.AddressDTO;
import com.org.onlineFoodDelivery.dto.RestaurantDTO;
import com.org.onlineFoodDelivery.entity.Cuisine;
import com.org.onlineFoodDelivery.entity.Restaurant;
import com.org.onlineFoodDelivery.entity.User;
import com.org.onlineFoodDelivery.exception.InvalidRequestException;
import com.org.onlineFoodDelivery.exception.ObjectCreationException;
import com.org.onlineFoodDelivery.exception.ObjectNotFoundException;
import com.org.onlineFoodDelivery.respository.CuisineRepository;
import com.org.onlineFoodDelivery.respository.RestaurantRepository;
import com.org.onlineFoodDelivery.respository.UserRegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService implements IRestaurantService{

    @Autowired
    RestaurantRepository repo;

    @Autowired
    UserRegistrationRepository userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CuisineRepository cuisineRepo;

    public RestaurantDTO addRestaurant(RestaurantDTO dto){
        Restaurant restaurant = modelMapper.map(dto, Restaurant.class);
        long restaurantOwnerId = dto.getRestaurantOwner().getId();
        User restaurantOwner = userRepo.findById(restaurantOwnerId).orElseThrow(() -> new ObjectCreationException("Restaurant owner "+ dto.getRestaurantOwner().getFirstName()+ " is not registered"));
        restaurant.setRestaurantOwner(restaurantOwner);
        Restaurant newRestaurant = repo.save(restaurant);
        dto.setId(newRestaurant.getId());
        dto.setLocation(modelMapper.map(newRestaurant.getLocation(), AddressDTO.class));

        return dto;
    }

    @Override
    public RestaurantDTO updateRestaurantStatus(RestaurantDTO dto) {

        Optional<Restaurant> restOpt = repo.findById(dto.getId());
        restOpt.orElseThrow(() -> new ObjectCreationException("No restaurant found with id : " + dto.getId()));
        Restaurant restaurant = restOpt.get();
        if(restaurant.getDeliveringStatus().equals(dto.getDeliveringStatus()))
            throw new ObjectCreationException("Restaurant status is already : " + dto.getDeliveringStatus());

       restaurant.setDeliveringStatus(dto.getDeliveringStatus());
       return modelMapper.map(repo.save(restaurant), RestaurantDTO.class);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> allRestaurants = repo.findAll();
        if(allRestaurants.isEmpty())
            throw new InvalidRequestException("No Restaurants found");
        return allRestaurants
                .stream()
                .map(rest -> modelMapper.map(rest, RestaurantDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> getAllRestaurantsByName(String name) {
        Optional<List<Restaurant>> list = repo.findByName(name);
        list.orElseThrow(() -> new InvalidRequestException("No Restaurants found with name like : " +name));
        return list.get().stream().map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> getAllRestaurantsByCuisine(String cuisineName) {

        Optional<Cuisine> cuisineOpt = cuisineRepo.findByName(cuisineName);
        Cuisine cuisine = cuisineOpt.orElseThrow(() -> new ObjectNotFoundException("No cuisine found with name : "+cuisineName));
        Optional<List<Restaurant>> restaurantListOpt = repo.findRestaurantByCuisinesId(cuisine.getId());
        List<Restaurant> restaurantList = restaurantListOpt.orElseThrow(() -> new ObjectNotFoundException("No restaurants found that serve cuisine : "+cuisineName));
        return restaurantList.stream().map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class)).collect(Collectors.toList());
    }


}
