package com.org.onlineFoodDelivery.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantDTO {

    private long id;
    @NotNull
    private String name;
    @NotNull
    private UserDTO restaurantOwner;
    @NotNull
    private AddressDTO location;
    @NotNull
    private String deliveringStatus;

}
