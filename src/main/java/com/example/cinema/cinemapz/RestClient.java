package com.example.cinema.cinemapz;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.cinema.cinemapz.dto.SimpleMovie;
import com.sun.xml.internal.bind.v2.model.core.Ref;
import org.apache.log4j.Logger;

import com.example.cinema.cinemapz.error.RestApiErrorAttributes;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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
        return getJson(webTarget, (response) -> response.readEntity(clazz));
    }

    <T> List<T> getJson(WebTarget webTarget, GenericType<List<T>> genericType)
            throws RestRequestException {
        return getJson(webTarget, (response) -> response.readEntity(genericType));
    }

    private <R> R getJson(WebTarget webTarget, Function<Response, R> serializer) {
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);

        throwExceptionIfStatuNotOK(response);

        return serializer.apply(response);
    }

    void post(WebTarget webTarget) {
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(null, Response.class);

        throwExceptionIfStatuNotOK(response);
    }

    private void throwExceptionIfStatuNotOK(Response response) throws RestRequestException {
        if (response.getStatusInfo().getStatusCode() != Response.Status.OK.getStatusCode()) {
            RestApiErrorAttributes errorObject = response.readEntity(RestApiErrorAttributes.class);

            logger.warn("Invalid rest request: " + errorObject);
            throw new RestRequestException(errorObject);
        }
    }

    WebTarget getTarget() {
        return client.target(REST_URI);
    }

    <T> String stringifyList(List<T> list) {
        StringBuilder builder = new StringBuilder();
        int lastElementIndex = list.size() - 1;

        for (int i = 0; ; i++) {
            builder.append(list.get(i));
            if (i == lastElementIndex)
                return builder.toString();
            builder.append(",");
        }
    }

}
