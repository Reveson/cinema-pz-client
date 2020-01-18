package com.example.cinema.cinemapz.rest;

import com.example.cinema.cinemapz.PropertyService;
import com.example.cinema.cinemapz.dto.MovieCategoryDto;
import com.example.cinema.cinemapz.dto.MovieDto;
import com.example.cinema.cinemapz.dto.ProjectionIdWithEpoch;
import com.example.cinema.cinemapz.dto.SimpleMovie;

import java.util.Properties;
import javax.ws.rs.core.GenericType;
import java.time.Instant;
import java.util.List;

public class MovieClient extends RestClient {

    public MovieDto getMovie(String id) {
        return getJson(getTarget().path("movies").path(id)
                .queryParam("lang", PropertyService.getLanguageTagFromLocale()), MovieDto.class);
    }

    public List<SimpleMovie> getMoviesList() {
        return getJson(getTarget().path("movies")
                        .queryParam("lang", PropertyService.getLanguageTagFromLocale())
                , new GenericType<List<SimpleMovie>>() {
                });
    }

    public List<MovieCategoryDto> getCategories() {
        return getJson(
                getTarget().path("movies").path("categories")
                        .queryParam("lang", PropertyService.getLanguageTagFromLocale()),
                new GenericType<List<MovieCategoryDto>>() {
                });
    }

    public List<SimpleMovie> getMoviesList(int categoryId) {
        return getJson(
                getTarget().path("movies").path("categories").path(String.valueOf(categoryId))
                        .queryParam("lang", PropertyService.getLanguageTagFromLocale()),
                new GenericType<List<SimpleMovie>>() {
                });
    }

    public List<ProjectionIdWithEpoch> getProjectionTimes(String movieId) {
        return getJson(
                getTarget().path("movies").path(movieId).path("projections"),
                new GenericType<List<ProjectionIdWithEpoch>>() {
                });
    }

    public String getMovieImageUrl(String imageName) {
        return REST_URI + "/images/" + imageName;
    }
}
