package com.example.cinema.cinemapz.panel;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.example.cinema.cinemapz.Main;
import com.example.cinema.cinemapz.component.SeatsTile;
import com.example.cinema.cinemapz.dto.SeatDto;

public class TicketPanel extends JPanel {

	public TicketPanel() {
		initWindow();
		initSeatsTile();
	}

	private void initWindow() {
		this.setLayout(new GridBagLayout());
	}

	private void initSeatsTile() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		int[][] tmp = new int[][] {
				{0 ,11,12,13,0 },
				{14,15,16,32,33},
				{17,18,19,34,35}
		}; //TODO


		JButton backButton = new JButton("Powrót"); //TODO
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		add(backButton, gbc);

		backButton.addActionListener((event) -> { //TODO cache
			Main.setPanel(Main.Frame.PROJECTIONS);
		});

		SeatsTile seatsTile = new SeatsTile(tmp, mock());
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

		JLabel titleActualLabel = new JLabel("The hobbit: lorem ipsum"); //TODO
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(titleActualLabel, gbc);

		JLabel timeLabel = new JLabel("godzina:"); //TODO
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(timeLabel, gbc);

		JLabel timeActualLabel = new JLabel("12:30"); //TODO
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

	}

	private List<SeatDto> mock() {
		List<SeatDto> list = new ArrayList<>();
		list.add(new SeatDto());
		list.get(0).setId(11);
		list.get(0).setCodeLetter("A");
		list.get(0).setCodeNumber("1");
		list.get(0).setOccupied(false);

		list.add(new SeatDto());
		list.get(1).setId(12);
		list.get(1).setCodeLetter("A");
		list.get(1).setCodeNumber("2");
		list.get(1).setOccupied(false);

		list.add(new SeatDto());
		list.get(2).setId(14);
		list.get(2).setCodeLetter("B");
		list.get(2).setCodeNumber("1");
		list.get(2).setOccupied(false);

		list.add(new SeatDto());
		list.get(3).setId(18);
		list.get(3).setCodeLetter("C");
		list.get(3).setCodeNumber("2");
		list.get(3).setOccupied(true);

		list.add(new SeatDto());
		list.get(4).setId(13);
		list.get(4).setCodeLetter("A");
		list.get(4).setCodeNumber("3");
		list.get(4).setOccupied(true);

		return list;
	}

}
