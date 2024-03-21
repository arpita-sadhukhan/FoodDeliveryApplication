package com.org.onlineFoodDelivery.controller;

import com.org.onlineFoodDelivery.dto.DishDTO;
import com.org.onlineFoodDelivery.dto.OrderDTO;
import com.org.onlineFoodDelivery.dto.RestaurantDTO;
import com.org.onlineFoodDelivery.service.IDishesService;
import com.org.onlineFoodDelivery.service.IOrderHistoryService;
import com.org.onlineFoodDelivery.service.IRestaurantService;
import com.org.onlineFoodDelivery.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@Tag(
        name = "Restaurant API list"
)
public class RestaurantController {

    @Autowired
    private IRestaurantService service;

    @Autowired
    private IOrderHistoryService orderService;

    @Autowired
    private IDishesService dishService;

    @PostMapping
    @Operation(
            summary = "Enroll a restaurant",
            description = "Enroll a new restaurant"
    )
    public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody @NotNull RestaurantDTO dto){

        RestaurantDTO newRestaurant = service.addRestaurant(dto);
        return ResponseEntity.status(HttpStatus.OK).body(newRestaurant);
    }

    @PutMapping("/{id}/updateStatus")
    @Operation(
            summary = "Update status of restaurant",
            description = "Update delivering status of a restaurant to 'Active'. Default set to 'Inactive'."
    )
    public ResponseEntity<RestaurantDTO> updateRestaurantStatus(@RequestBody RestaurantDTO dto,
                                                                @PathVariable @NotNull @Parameter(name = "id",
                                                                description = "Restaurant I'd") long id){

        dto.setId(id);
        RestaurantDTO updatedDto = service.updateRestaurantStatus(dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @GetMapping
    @Operation(
            summary = "Get all restaurants",
            description = "Get the list of all restaurants."
    )
    public ResponseEntity<List<RestaurantDTO>> getRestaurants(){

        List<RestaurantDTO> restaurantList = service.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(restaurantList);
    }

    @GetMapping("/name")
    @Operation(
            summary = "Get restaurant by name",
            description = "Get the restaurant details by restaurant name."
    )
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByName(@RequestParam
                                                                        @Parameter(name = "name",
                                                                        description = "Restaurant name") String name){

        List<RestaurantDTO> restaurantList = service.getAllRestaurantsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantList);
    }

    @GetMapping("/cuisine")
    @Operation(
            summary = "Get restaurant by serving cuisine",
            description = "Get the list of restaurant that serve a particular cuisine."
    )
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByCuisine(@RequestParam("name")
                                                                           @Parameter(name = "name",
                                                                           description = "Cuisine name",
                                                                           example = "Chinese") String name){

        List<RestaurantDTO> restaurantList = service.getAllRestaurantsByCuisine(name);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantList);
    }

    @PutMapping("/confirmOrder/{orderId}")
    @Operation(
            summary = "Confirm order",
            description = "Confirm pending orders at the restaurant."
    )
    public ResponseEntity<OrderDTO> confirmOrder(@PathVariable("orderId") @Parameter(name = "id", description = "Order I'd") long orderId,
                                                 @RequestBody OrderDTO orderDTO){
        OrderDTO dto = orderService.confirmOrder(orderDTO, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{restaurantId}/pendingOrders")
    @Operation(
            summary = "Get pending orders",
            description = "Get the list of pending orders at the restaurant."
    )
    public ResponseEntity<List<OrderDTO>> getPendingOrders(@PathVariable @Parameter(name = "restaurantId", description = "Restaurant I'd") long restaurantId){
        List<OrderDTO> dtoList = orderService.getPendingOrders(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "View all dishes",
            description = "View all dishes from a restaurant."
    )
    public ResponseEntity<List<DishDTO>> getDishByRestaurant(@PathVariable @Parameter(name = "id", description = "Restaurant I'd") Long id){
        List<DishDTO> dishes = dishService.getDishByRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body(dishes);
    }

}
