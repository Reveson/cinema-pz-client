package com.example.cinema.cinemapz.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.example.cinema.cinemapz.Main;

public class MovieDetailsPanel extends JPanel {

    public MovieDetailsPanel() {
        initWindow();
        initElements();
    }

    private void initWindow() {
        this.setLayout(new GridBagLayout());
    }

    private void initElements() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        JLabel categoryLabel = new JLabel("Fantasy"); //TODO
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(categoryLabel, gbc);

        JButton backButton = new JButton("Powrót"); //TODO
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(backButton, gbc);

        backButton.addActionListener((event) -> {
            Main.setPanel(Main.Frame.MAIN);
        });

        JLabel imageLabel = getImage();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(imageLabel, gbc);

        JLabel titleLabel = new JLabel("Nazwa:"); //TODO
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(titleLabel, gbc);

        JLabel titleActualLabel = new JLabel("The hobbit: lorem ipsum"); //TODO
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(titleActualLabel, gbc);

        JLabel ageLabel = new JLabel("Wiek:"); //TODO
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(ageLabel, gbc);

        JLabel ageActualLabel = new JLabel("+16"); //TODO
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(ageActualLabel, gbc);

        JLabel timeLabel = new JLabel("czas:"); //TODO
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(timeLabel, gbc);

        JLabel timeActualLabel = new JLabel("1:20"); //TODO
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(timeActualLabel, gbc);

        JLabel descriptionLabel = new JLabel("opis:"); //TODO
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(descriptionLabel, gbc);

        String longDescr =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut malesuada ullamcorper sem, vel vulputate ligula egestas ut."
                        + " Etiam fermentum ligula neque, a viverra sem ultrices vel. Nam aliquam interdum nibh."
                        + " Pellentesque fringilla suscipit leo, a commodo sem rutrum vitae."
                        + " Aenean eu justo sit amet eros consequat blandit cursus nec dolor."
                        + " Nam ultricies et orci ac iaculis. Vivamus luctus purus vitae mauris laoreet pellentesque.";
//        JLabel descriptionActualLabel = new JLabel("Lorem ipsuuuum"); //TODO
        JTextArea descriptionActualLabel = new JTextArea(longDescr); //TODO
        descriptionActualLabel.setWrapStyleWord(true);
        descriptionActualLabel.setLineWrap(true);
        descriptionActualLabel.setOpaque(false);
        descriptionActualLabel.setEditable(false);
        descriptionActualLabel.setMinimumSize(new Dimension(400, 100));
//        descriptionActualLabel.setHighlighter(null);
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(descriptionActualLabel, gbc);

        JButton nextButton = new JButton("Zobacz godziny seansów"); //TODO
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        add(nextButton, gbc);

        nextButton.addActionListener((event) -> {
            Main.setPanel(Main.Frame.PROJECTIONS);
        });

    }

    private JLabel getImage() {
        String url = "https://images-na.ssl-images-amazon.com/images/I/51D8AEqiZ-L.jpg";
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

}
