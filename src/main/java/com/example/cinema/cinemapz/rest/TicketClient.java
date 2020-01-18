package com.example.cinema.cinemapz.rest;

import com.example.cinema.cinemapz.dto.SeatDto;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;

public class TicketClient extends RestClient {

    public List<SeatDto> getSeats(String projectionId) {
        WebTarget target = getTarget()
                .path("tickets")
                .path("projection")
                .path(projectionId)
                .path("seats");

        return getJson(target, new GenericType<List<SeatDto>>() {
        });
    }

    public List<Integer[]> getSeatsPlacement(String projectionId) {
        WebTarget target = getTarget()
                .path("tickets")
                .path("projection")
                .path(projectionId)
                .path("seats")
                .path("placement");

        return getJson(target, new GenericType<List<Integer[]>>() {
        });
    }


    public void bookTickets(String projectionId, List<Integer> seatsIds, String clientName) {

        WebTarget target = getTarget()
                .path("tickets")
                .path("projection")
                .path(projectionId)
                .path("book")
                .queryParam("seats", seatsIds.toArray())
                .queryParam("clientName", clientName);

        post(target);
    }
}
