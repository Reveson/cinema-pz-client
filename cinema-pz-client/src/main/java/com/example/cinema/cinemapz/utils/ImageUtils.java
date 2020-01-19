package com.example.cinema.cinemapz.utils;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageUtils {

    public static JLabel getImage(String url, int width, int height) {
        Image image;
        try {
            image = ImageIO
                    .read(new URL(url)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            try {
                image = ImageIO.read(ImageUtils.class.getResource("/no_image.jpg"))
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH);
            } catch (IOException ex) {
                return new JLabel();
            }
        }
        return new JLabel(new ImageIcon(image));
    }


}
