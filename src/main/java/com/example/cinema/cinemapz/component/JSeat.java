package com.example.cinema.cinemapz.component;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.example.cinema.cinemapz.dto.SeatDto;

public class JSeat extends JCheckBox {

	private int seatId;
	private String name;
	private static final int EXPECTED_WIDTH = 30;
	private static final int EXPECTED_HEIGHT = EXPECTED_WIDTH;

	private static final ImageIcon freeImage;
	private static final ImageIcon occupiedImage;
	private static final ImageIcon selectedImage;

	static {
		freeImage = createImage(Color.GREEN);
		occupiedImage = createImage(Color.RED);
		selectedImage = createImage(Color.YELLOW);
	}

	private static ImageIcon createImage(Color color) {
		BufferedImage returnImage = new BufferedImage(EXPECTED_WIDTH, EXPECTED_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = returnImage.createGraphics();
		graphics.setPaint(color);
		graphics.fillRect(0, 0, returnImage.getWidth(), returnImage.getHeight());
		return new ImageIcon(returnImage);
	}

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

		initializeWithStatus(status);

		setPreferredSize(new Dimension(EXPECTED_WIDTH, EXPECTED_HEIGHT));
	}

	private void initializeWithStatus(Status status) {

		switch (status) {
			case SELECTED: //intentionally empty
			case FREE:
				setIcon(freeImage);
				setSelectedIcon(selectedImage);
				break;
			case OCCUPIED:
				setSelected(true);
				setEnabled(false);
				setDisabledSelectedIcon(occupiedImage);
				setIcon(occupiedImage);
				break;
			case NOT_EXISTS:
				setEnabled(false);
				setVisible(false);
				break;
		}
	}

	public Status getStatus() {
		if(isEnabled() && isSelected())
			return Status.SELECTED;
		else if(isEnabled() && !isSelected())
			return Status.FREE;
		else if(!isEnabled() && isSelected())
			return Status.OCCUPIED;
		else
			return Status.NOT_EXISTS;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public enum Status {
		FREE, OCCUPIED, NOT_EXISTS, SELECTED
	}

}
