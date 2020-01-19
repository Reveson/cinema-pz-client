package com.example.cinema.cinemapz.panel;


import com.example.cinema.cinemapz.Main;
import com.example.cinema.cinemapz.PropertyService;
import com.example.cinema.cinemapz.component.JSeat;
import com.example.cinema.cinemapz.component.SeatsTile;
import com.example.cinema.cinemapz.dto.SeatDto;
import com.example.cinema.cinemapz.exception.RestRequestException;
import com.example.cinema.cinemapz.rest.TicketClient;
import com.example.cinema.cinemapz.utils.Constants;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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

		JLabel screenLabel = new JLabel(PropertyService.getMessage("seats.panel.screen"));
		screenLabel.setForeground(Color.BLUE);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		add(screenLabel, gbc);

		JButton backButton = new JButton(PropertyService.getMessage("global.panel.back_button"));
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		add(backButton, gbc);

		backButton.addActionListener((event) -> Main.setPanel(Main.Frame.PROJECTIONS, getCache()));

		SeatsTile seatsTile = new SeatsTile(seatsPlacement, seats);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(seatsTile, gbc);

		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		JLabel titleLabel = new JLabel(PropertyService.getMessage("seats.panel.title"));
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(titleLabel, gbc);

		JLabel titleActualLabel = new JLabel(getCachedItem(Constants.MOVIE_NAME_CACHE));
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(titleActualLabel, gbc);

		JLabel timeLabel = new JLabel(PropertyService.getMessage("seats.panel.time"));
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(timeLabel, gbc);

		JLabel timeActualLabel = new JLabel(getCachedItem(Constants.TIME_CACHE));
		gbc.gridx = 1;
		gbc.gridy = 5;
		add(timeActualLabel, gbc);

		JLabel nameLabel = new JLabel(PropertyService.getMessage("seats.panel.name"));
		gbc.gridx = 4;
		gbc.gridy = 1;
		add(nameLabel, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;

		JTextField nameTextField = new JTextField(null, 12);
		gbc.gridx = 5;
		gbc.gridy = 1;
		add(nameTextField, gbc);

		JButton acceptButton = new JButton(PropertyService.getMessage("seats.panel.submit"));
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		add(acceptButton, gbc);
		acceptButton.addActionListener(event -> {
			List<JSeat> chosenSeats = seatsTile.getChosenSeats();
			String clientName = nameTextField.getText();
			try {
				if(chosenSeats.size() == 0) {
					showErrorDialog("seats.panel.error.no_seats");
					return;
				}
				reserveTickets(chosenSeats, clientName);
				showSuccessDialog(chosenSeats, clientName);
			} catch (RuntimeException e) {
				showErrorDialog("global.panel.error.default");
			}
		});

	}

	List<Integer[]> getSeatsPlacement(String projectionId) {
		return ticketClient.getSeatsPlacement(projectionId);
	}

	List<SeatDto> getSeats(String projectionId) {
		return ticketClient.getSeats(projectionId);
	}


	private void showSuccessDialog(List<JSeat> seats, String clientName) {
		String reservedSeatsSymbols = seats.stream().map(JSeat::getName)
				.collect(Collectors.joining(", "));
		String reservedLabel = PropertyService.getMessage("seats.panel.success.reserved") + " "
				+ reservedSeatsSymbols;
		String clientNameLabel =
				PropertyService.getMessage("seats.panel.success.name") + " " + clientName;
		String movieTitleLabel =
				PropertyService.getMessage("seats.panel.success.movie") + " " + getCachedItem(
						Constants.MOVIE_NAME_CACHE);
		String hourLabel =
				PropertyService.getMessage("seats.panel.success.time") + " " + getCachedItem(
						Constants.TIME_CACHE);
		Object[] fields = {
				reservedLabel,
				clientNameLabel,
				movieTitleLabel,
				hourLabel
		};

		JOptionPane
				.showMessageDialog(this, fields,
						PropertyService.getMessage("seats.panel.success.header"),
						JOptionPane.INFORMATION_MESSAGE);
		Main.setPanel(Main.Frame.MAIN);
	}

	private void showErrorDialog(String propertyMessage) {
		String message = PropertyService.getMessage(propertyMessage);
		Object[] fields = {message};
		JOptionPane.showMessageDialog(this, fields,
				PropertyService.getMessage("seats.panel.error.header"), JOptionPane.ERROR_MESSAGE);
		Main.setPanel(Main.Frame.SEATS, getCache());
	}

	private void reserveTickets(List<JSeat> seats, String clientName) {
		List<Integer> seatsIds = seats.stream().map(JSeat::getSeatId).collect(Collectors.toList());
		ticketClient.bookTickets(getCachedItem(Constants.PROJECTION_ID_CACHE), seatsIds, clientName);
	}

}
