package com.example.cinema.cinemapz.panel;

import com.example.cinema.cinemapz.Main;
import com.example.cinema.cinemapz.PropertyService;
import com.example.cinema.cinemapz.dto.MovieDto;
import com.example.cinema.cinemapz.rest.MovieClient;
import com.example.cinema.cinemapz.utils.Constants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MovieDetailsPanel extends AbstractPanel {

    private MovieClient movieClient = new MovieClient();

    public MovieDetailsPanel(Map<String, String> cache) {
        setCache(cache);
        initWindow();
        initElements();
    }

    private void initWindow() {
        this.setLayout(new GridBagLayout());
    }

    private void initElements() {
        MovieDto movie = getMovie(getCachedItem(Constants.MOVIE_ID_CACHE));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel categoryLabel = new JLabel(getCachedItem(movie.getCategoryName()));
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

        backButton.addActionListener((event) -> Main.setPanel(Main.Frame.MAIN));

        JLabel imageLabel = getImage(movieClient.getMovieImageUrl(movie.getImageUrl()));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(imageLabel, gbc);

        JLabel titleLabel = new JLabel(PropertyService.getMessage("details.panel.movie_title"));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(titleLabel, gbc);

        JLabel titleActualLabel = new JLabel(movie.getName());
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(titleActualLabel, gbc);

        JLabel ageLabel = new JLabel(PropertyService.getMessage("details.panel.age"));
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(ageLabel, gbc);

        JLabel ageActualLabel = new JLabel(movie.getAge());
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(ageActualLabel, gbc);

        JLabel timeLabel = new JLabel(PropertyService.getMessage("details.panel.time"));
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(timeLabel, gbc);

        JLabel timeActualLabel = new JLabel(movie.getDuration());
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(timeActualLabel, gbc);

        JLabel descriptionLabel = new JLabel(PropertyService.getMessage("details.panel.description"));
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(descriptionLabel, gbc);

        JTextArea descriptionActualLabel = new JTextArea(movie.getDescription());
        descriptionActualLabel.setWrapStyleWord(true);
        descriptionActualLabel.setLineWrap(true);
        descriptionActualLabel.setOpaque(false);
        descriptionActualLabel.setEditable(false);
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(descriptionActualLabel, gbc);

        JButton nextButton = new JButton(PropertyService.getMessage("details.panel.submit"));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        add(nextButton, gbc);

        nextButton.addActionListener((event) -> {
            addCachedItem(Constants.MOVIE_NAME_CACHE, movie.getName());
            Main.setPanel(Main.Frame.PROJECTIONS, getCache());
        });

    }

    private JLabel getImage(String url) {
        try {
            Image image = ImageIO
                    .read(new URL(url)).getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            //TODO default image;
            e.printStackTrace();
        }

        throw new RuntimeException();
    }//TODO to utils

    private MovieDto getMovie(String movieId) {
        return movieClient.getMovie(movieId);
    }
}
