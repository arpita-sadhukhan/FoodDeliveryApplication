package com.org.onlineFoodDelivery.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ErrorDTO {

    private String errorMessage;
    private int errorCode;
    private LocalDate timestamp;
    private String details;
}
