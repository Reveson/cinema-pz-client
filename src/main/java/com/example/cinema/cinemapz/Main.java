package com.example.cinema.cinemapz;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

import com.example.cinema.cinemapz.panel.MovieDetailsPanel;
import com.example.cinema.cinemapz.panel.ProjectionsPanel;
import com.example.cinema.cinemapz.panel.TicketPanel;

public class Main extends JFrame {

//    Logger logger = new Logger.(Main.class);

    public static void main(String[] args) throws IOException {
    	PropertyService.initialize();
        EventQueue.invokeLater(Main::new);
    }

    public Main() {
        this.setVisible(true);
        initGui();
    }

    private void initGui() {
//        add(MainPanel.getInstance());
//        add(MovieDetailsPanel.getInstance());
//        add(ProjectionsPanel.getInstance());
        add(TicketPanel.getInstance());
        int width = Integer.parseInt(PropertyService.getProperty("app.default.width", "800"));
        int height = Integer.parseInt(PropertyService.getProperty("app.default.height", "600"));
        setPreferredSize(new Dimension(width, height));
        setResizable(true);
        setFocusable(true);
        setTitle("Cinema PZ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
}
