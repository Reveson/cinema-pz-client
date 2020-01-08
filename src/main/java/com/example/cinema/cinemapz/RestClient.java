package com.example.cinema.cinemapz;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class RestClient { //TODO thats only sample

	private static final String REST_URI = "http://localhost:9090/movies";

	private Client client = ClientBuilder.newClient();

	public Movie getJsonTest(int id) {
		return client.target(REST_URI).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).get(Movie.class);
	}

}
