package com.example.cinema.cinemapz;

import java.awt.EventQueue;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JFrame;
import org.apache.log4j.Logger;

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
        add(MainPanel.getInstance());
        setResizable(true);
        setFocusable(true);
        setTitle("Cinema PZ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
}
