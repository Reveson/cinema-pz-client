package com.example.cinema.cinemapz.rest;

import com.example.cinema.cinemapz.Main;
import com.example.cinema.cinemapz.Main.Frame;
import com.example.cinema.cinemapz.PropertyService;
import com.example.cinema.cinemapz.error.RestApiErrorAttributes;
import com.example.cinema.cinemapz.exception.RestRequestException;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.List;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

public abstract class RestClient {

    static final String REST_URI;
    static final Client client;// = ClientBuilder.newClient();

    static {
        ClientConfig config = new ClientConfig();
        config.register(JacksonJsonProvider.class);
        client = ClientBuilder.newClient(config);
    }

    private static final Logger logger = Logger.getLogger(RestClient.class);

    static {
        String host = PropertyService.getProperty("cinema.server.host");
        String port = PropertyService.getProperty("cinema.server.port");
        REST_URI = "http://" + host + ":" + port;
    }

    <T> T getJson(WebTarget webTarget, Class<T> clazz) {
        return getJson(webTarget, (response) -> response.readEntity(clazz));
    }

    <T> List<T> getJson(WebTarget webTarget, GenericType<List<T>> genericType) {
        return getJson(webTarget, (response) -> response.readEntity(genericType));
    }

    private <R> R getJson(WebTarget webTarget, Function<Response, R> serializer) {
        Response response;
        try {
            response = webTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
            return serializer.apply(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            showErrorDialog();
            return null;
        }
    }

    void post(WebTarget webTarget) {
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(null, Response.class);

        throwExceptionIfStatusNotOK(response);
    }

    private void throwExceptionIfStatusNotOK(Response response) throws RestRequestException {
        if (response.getStatusInfo().getStatusCode() != Response.Status.OK.getStatusCode()) {
            RestApiErrorAttributes errorObject = response.readEntity(RestApiErrorAttributes.class);

            logger.warn("Invalid rest request: " + errorObject);
            throw new RestRequestException(errorObject);
        }
    }

    private void showErrorDialog() {
        String message = PropertyService.getMessage("global.panel.error.default");
        Object[] fields = {message};
        JOptionPane.showMessageDialog(null, fields,
                PropertyService.getMessage("seats.panel.error.header"), JOptionPane.ERROR_MESSAGE);
        Main.setPanel(Frame.MAIN);
    }

    WebTarget getTarget() {
        return client.target(REST_URI);
    }

}
