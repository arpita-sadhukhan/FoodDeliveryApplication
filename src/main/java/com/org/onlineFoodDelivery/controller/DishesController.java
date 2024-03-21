package com.org.onlineFoodDelivery.controller;

import com.org.onlineFoodDelivery.dto.DishDTO;
import com.org.onlineFoodDelivery.dto.RestaurantDTO;
import com.org.onlineFoodDelivery.entity.Dishes;
import com.org.onlineFoodDelivery.service.IDishesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(
        name = "API list for Dishes"
)
public class DishesController {

    @Autowired
    IDishesService service;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/addDishes")
    @Operation(
            summary = "Add dishes",
            description = "Add serving dishes for a restaurant"
    )
    public ResponseEntity<List<DishDTO>> addDishes(@RequestBody @NotNull List<DishDTO> dishDtoList){

        List<DishDTO> addedDishes = service.addDishes(dishDtoList);
        return ResponseEntity.status(HttpStatus.OK).body(addedDishes);

    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("dishes/{id}")
    @Operation(
            summary = "Delete a dish",
            description = "Delete a dish from a restaurant."
    )
    public ResponseEntity<String> deleteDish(@PathVariable @Parameter(name = "id", description = "Dish I'd") Long id){

        service.deleteDish(id);
        return new ResponseEntity<>("Successfully deleted dish with id : "+id, HttpStatus.OK);
    }



    @GetMapping("/dish")
    @Operation(
            summary = "Get serving restaurants",
            description = "Get the list of restaurants that serve the dish passed as input."
    )
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByDish(@RequestParam @Parameter(name = "name", description = "Dish name") String name){

        List<RestaurantDTO> restaurantList = service.getRestaurantsByDish(name);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantList);
    }
}
