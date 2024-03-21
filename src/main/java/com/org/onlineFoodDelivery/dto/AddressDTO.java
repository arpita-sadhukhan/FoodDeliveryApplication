package com.org.onlineFoodDelivery.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String houseNo;
    private String street;
    private String city;
    private String pincode;
    private String state;
    private String country;


}
