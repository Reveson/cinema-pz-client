package com.example.cinema.cinemapz.component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.*;

import com.example.cinema.cinemapz.dto.SeatDto;

public class SeatsTile extends JComponent {

	private List<JSeat> seatIconsList = new ArrayList<>();

	public SeatsTile(int[][] seatsPlacement, List<SeatDto> seats) {
		setLayout(initializeLayout(seatsPlacement.length));
		Map<Integer, SeatDto> idToSeatMap = seats.stream().collect(Collectors.toMap(SeatDto::getId, Function.identity()));

		for(int[] rowList : seatsPlacement) {
			for(int seatId : rowList) {
				SeatDto seatDto = idToSeatMap.get(seatId);
				if(seatDto != null) {
					JSeat seatIcon = new JSeat(seatDto);
					seatIconsList.add(seatIcon);
					add(seatIcon);
				}
				else {
					add(new JSeat(null));
				}
			}
		}
	}

	private LayoutManager initializeLayout(int numberOfColumns) {
		GridLayout layout = new GridLayout();
		layout.setRows(numberOfColumns);
		layout.setHgap(10);
		layout.setVgap(10);
		return layout;
	}

}
