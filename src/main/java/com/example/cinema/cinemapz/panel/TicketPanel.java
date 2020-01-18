package com.example.cinema.cinemapz.panel;


import com.example.cinema.cinemapz.component.JSeat;
import com.example.cinema.cinemapz.exception.RestRequestException;
import com.example.cinema.cinemapz.rest.TicketClient;
import com.example.cinema.cinemapz.utils.Constants;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.*;

import com.example.cinema.cinemapz.Main;
import com.example.cinema.cinemapz.component.SeatsTile;
import com.example.cinema.cinemapz.dto.SeatDto;

public class TicketPanel extends AbstractPanel {

	TicketClient ticketClient = new TicketClient();

	public TicketPanel(Map<String, String> cache) {
		setCache(cache);
		initWindow();
		initSeatsTile();
	}

	private void initWindow() {
		this.setLayout(new GridBagLayout());
	}

	private void initSeatsTile() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		List<Integer[]> seatsPlacement = getSeatsPlacement(getCachedItem(Constants.PROJECTION_ID_CACHE));
		List<SeatDto> seats = getSeats(getCachedItem(Constants.PROJECTION_ID_CACHE));


		JButton backButton = new JButton("Powrót"); //TODO
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		add(backButton, gbc);

		backButton.addActionListener((event) -> {
			Main.setPanel(Main.Frame.PROJECTIONS, getCache());
		});

		SeatsTile seatsTile = new SeatsTile(seatsPlacement, seats);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(seatsTile, gbc);

		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		JLabel titleLabel = new JLabel("Nazwa:"); //TODO
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(titleLabel, gbc);

		JLabel titleActualLabel = new JLabel(getCachedItem(Constants.MOVIE_NAME_CACHE));
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(titleActualLabel, gbc);

		JLabel timeLabel = new JLabel("godzina:"); //TODO
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(timeLabel, gbc);

		JLabel timeActualLabel = new JLabel(getCachedItem(Constants.TIME_CACHE));
		gbc.gridx = 1;
		gbc.gridy = 5;
		add(timeActualLabel, gbc);

		JLabel nameLabel = new JLabel("Twoje imię:"); //TODO
		gbc.gridx = 4;
		gbc.gridy = 1;
		add(nameLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;

		JTextField nameTextField = new JTextField(null, 12);
//		nameTextField.setMinimumSize(new Dimension(80, 0));
//		nameTextField.setPreferredSize(new Dimension(100, 30));
		gbc.gridx = 5;
		gbc.gridy = 1;
//		gbc.gridwidth = 2;
		add(nameTextField, gbc);

		JButton acceptButton = new JButton("Rezerwuj"); //TODO
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		add(acceptButton, gbc);
		acceptButton.addActionListener(event -> {
			List<JSeat> chosenSeats = seatsTile.getChosenSeats();
			String clientName = nameTextField.getText();
			try {
				if(chosenSeats.size() == 0)
					showErrorMonit();
				reserveTickets(chosenSeats, clientName);
				showSuccessMonit(chosenSeats, clientName);
			} catch (RestRequestException e) {
				showErrorMonit();
			}
		});

	}

	List<Integer[]> getSeatsPlacement(String projectionId) {
		return ticketClient.getSeatsPlacement(projectionId);
	}

	List<SeatDto> getSeats(String projectionId) {
		return ticketClient.getSeats(projectionId);
	}


	private void showSuccessMonit(List<JSeat> seats, String clientName) {
		String reservedSeatsSymbols = seats.stream().map(JSeat::getName).collect(Collectors.joining(", "));
		String reservedLabel = "Zarezerwowano: " + reservedSeatsSymbols; //TODO
		String clientNameLabel = "Twoje imię: " + clientName;
		String movieTitleLabel = "Film: " + getCachedItem(Constants.MOVIE_NAME_CACHE);
		String hourLabel = "Godzina: " + getCachedItem(Constants.TIME_CACHE);
		Object[] fields = {
				reservedLabel,
				clientNameLabel,
				movieTitleLabel,
				hourLabel
		};

		JOptionPane
				.showMessageDialog(this, fields, "Zarezerwowano", JOptionPane.INFORMATION_MESSAGE);
		Main.setPanel(Main.Frame.MAIN);
	}

	private void showErrorMonit() {
		String message = "error occurred"; //TODO
		Object[] fields = {message};
		JOptionPane.showMessageDialog(this, fields, "Błąd", JOptionPane.ERROR_MESSAGE);
		Main.setPanel(Main.Frame.SEATS, getCache());
	}

	private void reserveTickets(List<JSeat> seats, String clientName) {
		List<Integer> seatsIds = seats.stream().map(JSeat::getSeatId).collect(Collectors.toList());
		ticketClient.bookTickets(getCachedItem(Constants.PROJECTION_ID_CACHE), seatsIds, clientName);
	}

}
