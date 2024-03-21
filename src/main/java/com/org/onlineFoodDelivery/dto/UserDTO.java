package com.org.onlineFoodDelivery.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    private long id;
    @NotNull
    private String firstName;
    private String lastName;
    @NotNull
    private String email;
    private String password;
    private AddressDTO address;
    private String phone;

    @NotNull
    private String username;

    private Date createdDate;

}
