package com.example.cinema.cinemapz.component;

import com.example.cinema.cinemapz.utils.ImageUtils;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MovieTile extends JButton {

    private int movieId;
    private JLabel title;
    private JLabel image;

    private static final int IMAGE_WIDTH = 66;
    private static final int IMAGE_HEIGHT = 100;

    public MovieTile(String title, String imageUrl) {
        this.title = new JLabel(title);

        this.image = ImageUtils.getImage(imageUrl, IMAGE_WIDTH, IMAGE_HEIGHT);
        this.image.setSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(this.image);
        add(this.title);

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

    }



    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
