package com.example.cinema.cinemapz.rest;

import com.example.cinema.cinemapz.dto.MovieCategoryDto;
import com.example.cinema.cinemapz.dto.MovieDto;
import com.example.cinema.cinemapz.dto.SimpleMovie;

import javax.ws.rs.core.GenericType;
import java.time.Instant;
import java.util.List;

public class MovieClient extends RestClient {

    public MovieDto getMovie(int id) {
        return getJson(getTarget().path("movies").path(String.valueOf(id)), MovieDto.class);
    }

    public List<SimpleMovie> getMoviesList() {
        return getJson(getTarget().path("movies"), new GenericType<List<SimpleMovie>>() {
        });
    }

    public List<MovieCategoryDto> getCategories() {
        return getJson(
                getTarget().path("movies").path("categories"),
                new GenericType<List<MovieCategoryDto>>() {
                });
    }

    public List<SimpleMovie> getMoviesList(int categoryId) {
        return getJson(
                getTarget().path("movies").path("categories").path(String.valueOf(categoryId)),
                new GenericType<List<SimpleMovie>>() {
                });
    }

    public List<Instant> getProjectionTimes(int movieId) {
        return getJson(
                getTarget().path("movies").path(String.valueOf(movieId)).path("projections"),
                new GenericType<List<Instant>>() {
                });
    }
}
