package com.org.onlineFoodDelivery.controller;

import com.org.onlineFoodDelivery.dto.LoginDTO;
import com.org.onlineFoodDelivery.dto.UserDTO;
import com.org.onlineFoodDelivery.service.UserRegistrationService;
import com.org.onlineFoodDelivery.util.FoodDeliveryConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Authorisation"
)
public class UserRegistration {

    @Autowired
    private UserRegistrationService service;

    public UserRegistration(UserRegistrationService service) {
        this.service = service;
    }

    @PostMapping("/admin/register")
    @Operation(
            summary = "Admin registration",
            description = "Register as admin"
    )
    public ResponseEntity<UserDTO> adminRegistration(@RequestBody UserDTO dto){

        if(null != dto){

            UserDTO registeredUser = service.register(dto, FoodDeliveryConstants.USER_ADMIN);
            if(null != registeredUser){
                return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDTO());
    }

    @PostMapping("/restaurantOwner/register")
    @Operation(
            summary = "Restaurant owner registration",
            description = "Register as Restaurant Owner"
    )
    public ResponseEntity<UserDTO> restaurantOwnerRegistration(@RequestBody UserDTO dto){

        if(null != dto){
            UserDTO registeredUser = service.register(dto, FoodDeliveryConstants.USER_OWNER);
            if(null != registeredUser){
                return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDTO());
    }

    @PostMapping("/customer/register")
    @Operation(
            summary = "Customer Registration",
            description = "Register as Customer"
    )
    public ResponseEntity<UserDTO> customerRegistration(@RequestBody UserDTO dto){

        if(null != dto){
            UserDTO registeredUser = service.register(dto, FoodDeliveryConstants.USER_CUSTOMER);
            if(null != registeredUser){
                return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDTO());
    }

    @GetMapping("/users")
    @Operation(
            summary = "Get all users",
            description = "Get the list of all users."
    )
    public ResponseEntity<List<UserDTO>> getUserList(){
        List<UserDTO> userList = service.getUserList();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "User login"
    )
    public ResponseEntity<String> login(@RequestBody LoginDTO dto){
        String response = service.login(dto);
        return ResponseEntity.ok(response);
    }
}
