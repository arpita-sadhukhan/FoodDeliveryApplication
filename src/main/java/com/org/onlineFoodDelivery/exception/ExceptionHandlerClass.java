package com.org.onlineFoodDelivery.exception;

import com.org.onlineFoodDelivery.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler({ObjectCreationException.class, InvalidRequestException.class, ObjectNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleException(BaseException ex, WebRequest webRequest){

        ErrorDTO dto = new ErrorDTO();
        dto.setErrorCode(ex.getErrorCode());
        dto.setErrorMessage(ex.getMessage());
        dto.setTimestamp(LocalDate.now());
        dto.setDetails(webRequest.getDescription(false));
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
