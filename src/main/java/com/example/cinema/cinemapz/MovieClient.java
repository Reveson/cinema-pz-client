package com.example.cinema.cinemapz;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.cinema.cinemapz.dto.MovieDto;

public class MovieClient extends RestClient {

	public MovieDto getMovie(int id) {
		return getJson(getTarget().path("movies").path(String.valueOf(id)), MovieDto.class);
	}

}
