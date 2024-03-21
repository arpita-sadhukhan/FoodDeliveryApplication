package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.CuisineDTO;
import com.org.onlineFoodDelivery.dto.DishDTO;
import com.org.onlineFoodDelivery.dto.RestaurantDTO;
import com.org.onlineFoodDelivery.entity.Cuisine;
import com.org.onlineFoodDelivery.entity.Dishes;
import com.org.onlineFoodDelivery.entity.Restaurant;
import com.org.onlineFoodDelivery.exception.ObjectCreationException;
import com.org.onlineFoodDelivery.exception.ObjectNotFoundException;
import com.org.onlineFoodDelivery.respository.CuisineRepository;
import com.org.onlineFoodDelivery.respository.DishesRepository;
import com.org.onlineFoodDelivery.respository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DishesService implements IDishesService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestaurantRepository restaurantRepo;

    @Autowired
    DishesRepository dishRepo;

    @Autowired
    CuisineRepository cuisineRepo;

    @Override
    public List<DishDTO> addDishes(List<DishDTO> dishes) {
        List<DishDTO> savedDishes = new ArrayList<>();

        for(DishDTO dishDto : dishes){

            RestaurantDTO restaurantDTO = dishDto.getRestaurant();
            Optional<Restaurant> restaurantOptional = restaurantRepo.findById(restaurantDTO.getId());

            if(restaurantOptional.isEmpty())
                throw new ObjectCreationException("Restaurant : "+ restaurantDTO.getName()+" is not present");

            Restaurant restaurant = restaurantOptional.get();
            Dishes dishToSave = modelMapper.map(dishDto, Dishes.class);


            Cuisine cuisine = getCuisine(dishToSave.getCuisine());
            if(null == cuisine.getRestaurants()){
                cuisine.addRestaurant(restaurant);
            }

            if(restaurant.getCuisines() == null || !restaurant.getCuisines().contains(cuisine)){
                restaurant.addCuisine(cuisine);
            }

            dishToSave.setRestaurant(restaurant);
            dishToSave.setCuisine(cuisine);
            Dishes savedDish = dishRepo.save(dishToSave);

            savedDish.setRestaurant(restaurant);
            savedDishes.add(modelMapper.map(savedDish, DishDTO.class));
        }
        return savedDishes;
    }

    private Cuisine getCuisine(Cuisine cuisine) {
        Optional<Cuisine> cuisineOpt = cuisineRepo.findByName(cuisine.getName());
        return cuisineOpt.orElseGet(() -> cuisineRepo.save(cuisine));

    }

    @Override
    public void deleteDish(Long id){
        Optional<Dishes> dishOpt = dishRepo.findById(id);
        Dishes dish = dishOpt.orElseThrow(() -> new ObjectNotFoundException("No dish found with id : "+id));
        dishRepo.delete(dish);
    }

    @Override
    public List<DishDTO> getDishByRestaurant(Long restaurantId) {
        Optional<List<Dishes>> dishesListOpt = dishRepo.findByRestaurantIdAndAvailable(restaurantId, Boolean.TRUE);
        List<Dishes> dishesList = dishesListOpt
                                            .orElseThrow(() -> new ObjectNotFoundException("No dishes are deliverable from restaurant with id : "+restaurantId));
        return dishesList
                .stream()
                .map(this::populateDishDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> getRestaurantsByDish(String name) {
        Optional<List<Dishes>> restaurantListOpt = dishRepo.findByName(name);
        List<Dishes> dishList = restaurantListOpt.orElseThrow(() -> new ObjectNotFoundException("No Restaurants Found for dish - "+name));
        return dishList.stream().map(Dishes::getRestaurant).map(restaurant -> modelMapper.map(restaurant, RestaurantDTO.class)).collect(Collectors.toList());
    }

    private DishDTO populateDishDto(Dishes dish){
        DishDTO dishDto = new DishDTO();
        dishDto.setId(dish.getId());
        dishDto.setName(dish.getName());
        dishDto.setDescription(dish.getDescription());
        dishDto.setPrice(dish.getPrice());
        dishDto.setAvailable(dish.getAvailable());
        if(dish.getRestaurant() != null){
            RestaurantDTO dto = new RestaurantDTO();
            dto.setId(dish.getRestaurant().getId());
            dto.setName(dish.getRestaurant().getName());
            dto.setDeliveringStatus(dish.getRestaurant().getDeliveringStatus());
            dishDto.setRestaurant(dto);
        }
        if(dish.getCuisine() != null){
            CuisineDTO dto = new CuisineDTO();
            dto.setId(dish.getCuisine().getId());
            dto.setName(dish.getCuisine().getName());
            dishDto.setCuisine(dto);
        }
        return dishDto;
    }
}
