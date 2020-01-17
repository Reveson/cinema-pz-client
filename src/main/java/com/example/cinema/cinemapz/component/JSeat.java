package com.example.cinema.cinemapz.component;

import java.awt.*;

import javax.swing.*;

import com.example.cinema.cinemapz.dto.SeatDto;

public class JSeat extends JPanel {

	private Status status;
	private int seatId;
	private String name;
	private static final Color occupiedColor = Color.RED;
	private static final Color freeColor = Color.GREEN;
	private static final Color selectedColor = Color.YELLOW;
	private static final int EXPECTED_WIDTH = 30;
	private static final int EXPECTED_HEIGHT = EXPECTED_WIDTH;

	public JSeat(SeatDto seatDto) {
		Status status;
		if (seatDto != null) {
			status = seatDto.isOccupied() ? Status.OCCUPIED : Status.FREE;
			this.name = seatDto.getCodeLetter() + seatDto.getCodeNumber();
			this.seatId = seatDto.getId();
			JLabel nameLabel = new JLabel(name);
			add(nameLabel);
		} else {
			status = Status.NOT_EXISTS;
		}

		setStatus(status);

		setPreferredSize(new Dimension(EXPECTED_WIDTH, EXPECTED_HEIGHT));
	}

	public void setStatus(Status status) {
		switch (status) {
			case OCCUPIED:
				setBackground(occupiedColor);
				break;
			case SELECTED:
				setBackground(selectedColor);
				break;
			case FREE:
				setBackground(freeColor);
				break;
			case NOT_EXISTS:
				setVisible(false);
				break;
		}
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public enum Status {
		FREE, OCCUPIED, NOT_EXISTS, SELECTED
	}

}
