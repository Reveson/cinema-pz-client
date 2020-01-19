package com.example.cinema.cinemapz.utils;

import java.time.ZoneId;
import java.util.Locale;

public class Constants {

	public static final String DEFAULT_PROPERTIES_NAME = "settings.properties";
	public static final Locale DEFAULT_LOCALE = Locale.forLanguageTag("pl-PL");
	public static final ZoneId CINEMA_TIME_ZONE = ZoneId.of("Europe/Warsaw");

	public static final String MOVIE_ID_CACHE = "movie_id";
	public static final String MOVIE_NAME_CACHE = "movie_name";
	public static final String PROJECTION_ID_CACHE = "projection_id";
	public static final String TIME_CACHE = "time";

}
