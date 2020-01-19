package com.example.cinema.cinemapz.exception;

import java.util.ResourceBundle;

import com.example.cinema.cinemapz.PropertyService;
import com.example.cinema.cinemapz.error.RestApiErrorAttributes;

public class RestRequestException extends RuntimeException {

    public RestRequestException(RestApiErrorAttributes errorAttributes) {
        super(PropertyService
                .getMessage(PropertyService.mapErrorToProperty(errorAttributes.getMessage())));
    }

}
