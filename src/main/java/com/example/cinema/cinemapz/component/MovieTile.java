package com.example.cinema.cinemapz.component;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.example.cinema.cinemapz.Main;

//public class MovieTile extends JComponent implements ActionListener {
public class MovieTile extends JButton {

    private JLabel title;
    private JLabel image;

    private static final int IMAGE_WIDTH = 66;
    private static final int IMAGE_HEIGHT = 100;

    public MovieTile(String title, String imageUrl) {
        this.title = new JLabel(title);
        try {
            Image image = ImageIO.read(new URL(imageUrl)).getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
            this.image = new JLabel(new ImageIcon(image));
            this.image.setSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        } catch (IOException e) {
            //TODO default image;
            e.printStackTrace();
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(this.image);
        add(this.title);

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        addActionListener((event) -> { //TODO
            Main.setPanel(Main.Frame.DETAILS);
        });
    }

}
