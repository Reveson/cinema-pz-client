package com.example.cinema.cinemapz.panel;

import com.example.cinema.cinemapz.PropertyService;
import com.example.cinema.cinemapz.rest.MovieClient;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;

import com.example.cinema.cinemapz.Main;
import com.example.cinema.cinemapz.dto.ProjectionIdWithEpoch;
import com.example.cinema.cinemapz.utils.Constants;

public class ProjectionsPanel extends AbstractPanel {

	MovieClient movieClient = new MovieClient();

	public ProjectionsPanel(Map<String, String> cache) {
		setCache(cache);
		initWindow();
		initContent();
	}

	private void initWindow() {
		this.setLayout(new GridBagLayout());
	}

	private void initContent() {
		List<ProjectionIdWithEpoch> projections = getProjections(getCachedItem(Constants.MOVIE_ID_CACHE));
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTHWEST;

		JLabel categoryLabel = new JLabel(getCachedItem(Constants.MOVIE_NAME_CACHE));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		add(categoryLabel, gbc);

		JButton backButton = new JButton(PropertyService.getMessage("global.panel.back_button"));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		add(backButton, gbc);

		backButton.addActionListener((event) -> {
			Main.setPanel(Main.Frame.MAIN);
		});

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		initProjectionDays(projections, gbc);
	}


	private void initProjectionDays(List<ProjectionIdWithEpoch> projectionList, GridBagConstraints gbc) {
		Map<DayOfWeek, List<ProjectionIdWithEpoch>> dayOfWeekToProjection = filterEpochBeforeNowAndAfterSevenDays(projectionList).stream()
				.collect(Collectors.groupingBy(projection -> getDayOfWeekFromEpoch(projection.getStartTimeEpoch())));

		int ordinalNumberOfDayOfWeek = getTodayDayOfWeek().getValue();
		for (int i = 0; i < 7; i++) {
			DayOfWeek currentlyIteratedDOW = DayOfWeek.of(ordinalNumberOfDayOfWeek);
			if (dayOfWeekToProjection.containsKey(currentlyIteratedDOW)) {
				addDayName(currentlyIteratedDOW.name(), gbc);
				for (ProjectionIdWithEpoch projection : dayOfWeekToProjection.get(currentlyIteratedDOW)) { //TODO sorted?
					String projectionTimeStringified = Instant.ofEpochMilli(projection.getStartTimeEpoch())
							.atZone(Constants.CINEMA_TIME_ZONE)
							.toLocalTime()
							.toString();
					addButton(projectionTimeStringified, projection.getProjectionId(), gbc);
				}
			}
			ordinalNumberOfDayOfWeek = ordinalNumberOfDayOfWeek % 7 + 1;
		}
	}

	private DayOfWeek getTodayDayOfWeek() {
		return Instant.now().atZone(Constants.CINEMA_TIME_ZONE).getDayOfWeek();
	}

	private List<ProjectionIdWithEpoch> filterEpochBeforeNowAndAfterSevenDays(List<ProjectionIdWithEpoch> projectionList) {
		long epochNow = Instant.now().toEpochMilli();
		long epochMax = getSevenDaysLaterEpoch(getTodayMidnightEpoch());
		return projectionList.stream()
				.filter(projection -> projection.getStartTimeEpoch() > epochNow)
				.filter(projection -> projection.getStartTimeEpoch() < epochMax)
				.collect(Collectors.toList());
	}

	private DayOfWeek getDayOfWeekFromEpoch(long epoch) {
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epoch), Constants.CINEMA_TIME_ZONE).getDayOfWeek();
	}

	private long getTodayMidnightEpoch() {
		//		return LocalDateTime.of(LocalDate.now(Constants.CINEMA_TIME_ZONE), LocalTime.MIDNIGHT).toEpochSecond(Constants.CINEMA_TIME_ZONE);
		return LocalDate.now(Constants.CINEMA_TIME_ZONE).atStartOfDay(Constants.CINEMA_TIME_ZONE).toEpochSecond() * 1000;
	}

	private long getSevenDaysLaterEpoch(long epoch) {
		final int SEVEN_DAYS_IN_MILLIS = 1000 * 60 * 60 * 24 * 7;
		return epoch + SEVEN_DAYS_IN_MILLIS;
	}

	private void addDayName(String dayOfWeekName, GridBagConstraints gbc) {
		final JLabel dayOfWeek = new JLabel(getTranslatedDayOfWeek(dayOfWeekName));

		gbc.gridy += 1;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(dayOfWeek, gbc);
	}

	private String getTranslatedDayOfWeek(String dayOfWeekName) {
		return PropertyService.getProperty("global.panel.day."+dayOfWeekName.toLowerCase());
	}

	private void addButton(String timeStringified, int projectionId, GridBagConstraints gbc) {
		JButton buttonToAdd = new JButton(timeStringified);
		buttonToAdd.addActionListener((event) -> {
			addCachedItem(Constants.TIME_CACHE, timeStringified);
			addCachedItem(Constants.PROJECTION_ID_CACHE, String.valueOf(projectionId));
			Main.setPanel(Main.Frame.SEATS, getCache());
		});
		gbc.gridx += 1;
		add(buttonToAdd, gbc);
	}

	private List<ProjectionIdWithEpoch> getProjections(String movieId) {
		return  movieClient.getProjectionTimes(movieId);
	}

}
