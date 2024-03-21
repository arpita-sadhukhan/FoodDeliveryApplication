package com.org.onlineFoodDelivery.controller;

import com.org.onlineFoodDelivery.dto.CartDTO;
import com.org.onlineFoodDelivery.dto.OrderDTO;
import com.org.onlineFoodDelivery.service.ICartService;
import com.org.onlineFoodDelivery.service.IOrderHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(
        name = "Cart API list"
)
public class CartController {

    @Autowired
    ICartService service;

    @Autowired
    IOrderHistoryService orderService;

    @Operation(
            summary = "Add to cart",
            description = "Add items to Cart. Inputs are - Cart items, user id and restaurant id."
    )
    @PostMapping("/{id}/restaurant/{restaurantId}")
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartDTO dto,
                                             @PathVariable("id") @Parameter(name = "userId", description = "User I'd") long userId,
                                             @PathVariable("restaurantId") @Parameter(name = "restaurantId", description = "Restaurant I'd")long restaurantId){

        CartDTO cart = service.addToCart(dto, userId, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @Operation(
            summary = "Place order",
            description = "Place order with items from cart. Pass user id and restaurant id."
    )
    @GetMapping("/{id}/restaurant/{restaurantId}/placeOrder")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable("id") @Parameter(name = "userId", description = "User I'd")long userId,
                                               @PathVariable("restaurantId") @Parameter(name = "restaurantId", description = "Restaurant I'd")long restaurantId){

        OrderDTO dto = orderService.placeOrder(userId, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
            summary = "View cart",
            description = "View items added to cart."
    )
    @GetMapping("/{id}/restaurant/{restaurantId}")
    public ResponseEntity<CartDTO> viewCart(@PathVariable("id") @Parameter(name = "userId", description = "User I'd") long userId,
                                            @PathVariable("restaurantId") @Parameter(name = "restaurantId", description = "Restaurant I'd") long restaurantId){
        CartDTO dto = service.viewCart(userId, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
