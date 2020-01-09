package com.example.cinema.cinemapz;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.cinema.cinemapz.error.RestApiErrorAttributes;

public abstract class RestClient {

	static final String REST_URI;
	static final Client client = ClientBuilder.newClient();

	private static final Logger logger = Logger.getLogger(RestClient.class);

	static {
		String host = PropertyService.getProperty("cinema.server.host");
		String port = PropertyService.getProperty("cinema.server.port");
		REST_URI = "http://" + host + ":" + port;
	}

	<T> T getJson(WebTarget webTarget, Class<T> clazz) throws RestRequestException {
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);

		if (response.getStatusInfo().getStatusCode() != Response.Status.OK.getStatusCode()) {
			RestApiErrorAttributes errorObject = response.readEntity(RestApiErrorAttributes.class);

			logger.warn("Invalid rest request: " + errorObject);
			throw new RestRequestException(errorObject);
		}

		return response.readEntity(clazz);
	}

	WebTarget getTarget() {
		return client.target(REST_URI);
	}

}
