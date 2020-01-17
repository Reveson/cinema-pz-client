package com.example.cinema.cinemapz;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import com.example.cinema.cinemapz.panel.MainPanel;
import com.example.cinema.cinemapz.panel.MovieDetailsPanel;
import com.example.cinema.cinemapz.panel.ProjectionsPanel;
import com.example.cinema.cinemapz.panel.TicketPanel;

public class Main extends JFrame {

//    Logger logger = new Logger.(Main.class);
    private Component currentPanel;
    private static Main thisObject;

    public static void main(String[] args) throws IOException {
    	PropertyService.initialize();
        EventQueue.invokeLater(Main::new);
    }

    private Main() {
        this.setVisible(true);
        thisObject = this;
        initGui();
    }

    @Override
    public Component add(Component component) {
        getContentPane().removeAll();
        return super.add(component);
    }

    public static void setPanel(Frame frame) {
        switch (frame) {
            case MAIN:
                thisObject.add(new MainPanel());
                break;
            case DETAILS:
                thisObject.add(new MovieDetailsPanel());
                break;
            case PROJECTIONS:
                thisObject.add(new ProjectionsPanel());
                break;
            case SEATS:
                thisObject.add(new TicketPanel());
                break;
        }
        thisObject.pack();
    }

    private void initGui() {

//        setPanel(Frame.MAIN); //TODO
        setPanel(Frame.SEATS);

        int width = Integer.parseInt(PropertyService.getProperty("app.default.width", "800"));
        int height = Integer.parseInt(PropertyService.getProperty("app.default.height", "600"));
        setPreferredSize(new Dimension(width, height));
        setResizable(true);
        setFocusable(true);
        setTitle("Cinema PZ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public enum Frame {
        MAIN, DETAILS, PROJECTIONS, SEATS
    }
}
