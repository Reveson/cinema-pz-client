package com.example.cinema.cinemapz;

import java.util.ResourceBundle;

import com.example.cinema.cinemapz.error.RestApiErrorAttributes;

public class RestRequestException extends RuntimeException {

	private static final ResourceBundle bundle = PropertyService.getResourceBundle();

	RestRequestException(RestApiErrorAttributes errorAttributes) {
		super(bundle.getString(PropertyService.mapErrorToProperty(errorAttributes.getMessage())));
	}

}
