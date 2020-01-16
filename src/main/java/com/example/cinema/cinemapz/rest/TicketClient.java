package com.example.cinema.cinemapz.rest;

import com.example.cinema.cinemapz.dto.SeatDto;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;

public class TicketClient extends RestClient {

    public List<SeatDto> getSeats(int projectionId) {
        WebTarget target = getTarget()
                .path("tickets")
                .path("projection")
                .path(String.valueOf(projectionId));

        return getJson(target, new GenericType<List<SeatDto>>() {
        });
    }

    public void bookTickets(int projectionId, List<Integer> seatsIds, String clientName) {

        WebTarget target = getTarget()
                .path("tickets")
                .path("projection")
                .path(String.valueOf(projectionId))
                .path("book")
                .queryParam("seats", seatsIds.toArray())
                .queryParam("clientName", clientName);

        post(target);
    }
}
