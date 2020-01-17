package com.example.cinema.cinemapz.panel;

import java.awt.*;

import javax.swing.*;

import com.example.cinema.cinemapz.component.MovieTile;

public class MainPanel extends JPanel {

    private JComponent categorySelectionComponent = new JComponent() {};
    private JComponent movieListComponent = new JComponent() {};

    public MainPanel() {
        initWindow();
        initTopPanel();
        initMoviePanel();
    }

    private void initWindow() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void initTopPanel() {
//        categorySelectionComponent.setLayout(new BoxLayout(categorySelectionComponent, BoxLayout.X_AXIS));
        categorySelectionComponent.setLayout(new FlowLayout());

        JLabel categoryLabel = new JLabel("Kategoria:"); //TODO
        categorySelectionComponent.add(categoryLabel);

        JComboBox<String> categoryCombo = new JComboBox<>();
        categoryCombo.addItem("Wszystkie"); //TODO
        categoryCombo.addItem("Horror");
        categoryCombo.addItem("Komedia");
        categorySelectionComponent.add(categoryCombo);

//        categorySelectionComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        add(categorySelectionComponent);
    }

    private void initMoviePanel() {
        int numberOfMovies = 10;
        GridLayout layout = new GridLayout();
        movieListComponent.setLayout(layout);

        layout.setRows((int) Math.ceil(numberOfMovies/3.0));
        layout.setHgap(50);

        for(int i =  0; i < numberOfMovies; i++) {
            movieListComponent.add(getMovieTitle());
        }

        add(movieListComponent);
    }

    private MovieTile getMovieTitle() {
        String title = "The hobbit: lorem ipsum";
        String url = "https://images-na.ssl-images-amazon.com/images/I/51D8AEqiZ-L.jpg";
        MovieTile tile = new MovieTile(title, url);
        tile.addActionListener((event) -> {
            System.out.println("works");
        });
        return tile;
    }
}
