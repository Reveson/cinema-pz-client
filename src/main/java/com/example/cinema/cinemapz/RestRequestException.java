package com.example.cinema.cinemapz;

import java.util.ResourceBundle;

import com.example.cinema.cinemapz.error.RestApiErrorAttributes;

public class RestRequestException extends RuntimeException {

    RestRequestException(RestApiErrorAttributes errorAttributes) {
        super(PropertyService
                .getMessage(PropertyService.mapErrorToProperty(errorAttributes.getMessage())));
    }

}
