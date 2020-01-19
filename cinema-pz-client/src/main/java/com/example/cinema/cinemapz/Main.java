package com.example.cinema.cinemapz;

import com.example.cinema.cinemapz.panel.MainPanel;
import com.example.cinema.cinemapz.panel.MovieDetailsPanel;
import com.example.cinema.cinemapz.panel.ProjectionsPanel;
import com.example.cinema.cinemapz.panel.TicketPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;

public class Main extends JFrame {

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
        setPanel(frame, null);
    }

    public static void setPanel(Frame frame, Map<String, String> cache) {
        switch (frame) {
            case MAIN:
                thisObject.add(new MainPanel());
                break;
            case DETAILS:
                thisObject.add(new MovieDetailsPanel(cache));
                break;
            case PROJECTIONS:
                thisObject.add(new ProjectionsPanel(cache));
                break;
            case SEATS:
                thisObject.add(new TicketPanel(cache));
                break;
        }
        thisObject.pack();
    }

    private void initGui() {

        int width = Integer.parseInt(PropertyService.getProperty("app.default.width", "800"));
        int height = Integer.parseInt(PropertyService.getProperty("app.default.height", "600"));
        setPreferredSize(new Dimension(width, height));
        setResizable(true);
        setFocusable(true);
        setTitle("Cinema PZ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        setPanel(Frame.MAIN);
    }

    public enum Frame {
        MAIN, DETAILS, PROJECTIONS, SEATS
    }
}
