package com.example.cinema.cinemapz.panel;

import com.example.cinema.cinemapz.Main;
import com.example.cinema.cinemapz.dto.MovieCategoryDto;
import com.example.cinema.cinemapz.dto.SimpleMovie;
import com.example.cinema.cinemapz.rest.MovieClient;

import com.example.cinema.cinemapz.utils.Constants;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import com.example.cinema.cinemapz.component.MovieTile;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class MainPanel extends AbstractPanel {

    private JComponent categorySelectionComponent = new JComponent() {};
    private JComponent movieListComponent = new JComponent() {};
    private MovieClient movieClient = new MovieClient();

    public MainPanel() {
        initWindow();
        initTopPanel();
        initMoviePanel(getMovies());
        add(movieListComponent);
    }

    private void initWindow() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void initTopPanel() {
//        categorySelectionComponent.setLayout(new BoxLayout(categorySelectionComponent, BoxLayout.X_AXIS));
        categorySelectionComponent.setLayout(new FlowLayout());

        JLabel categoryLabel = new JLabel("Kategoria:"); //TODO
        categorySelectionComponent.add(categoryLabel);

        JComboBox<MovieCategoryDto> categoryCombo = getCategoryDropdownList();
        categorySelectionComponent.add(categoryCombo);

//        categorySelectionComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        add(categorySelectionComponent);
    }

    private void initMoviePanel(List<SimpleMovie> movies) {
        movieListComponent.removeAll();
        int numberOfMovies = movies.size();
        GridLayout layout = new GridLayout();
        movieListComponent.setLayout(layout);

        layout.setRows(numberOfMovies > 0 ? (int) Math.ceil(numberOfMovies/3.0) : 1);
        layout.setHgap(50);

        for(SimpleMovie simpleMovie : movies) {
            movieListComponent.add(getMovieTitle(simpleMovie));
        }

    }

    private JComboBox<MovieCategoryDto> getCategoryDropdownList() {
        JComboBox<MovieCategoryDto> categoryCombo = new JComboBox<>();
        List<MovieCategoryDto> categories = getCategories();
        MovieCategoryDto mockCategory = new MovieCategoryDto();
        mockCategory.setName("All"); //TODO
        categoryCombo.addItem(mockCategory);
        for(MovieCategoryDto category : categories) {
            categoryCombo.addItem(category);
        }
        categoryCombo.addActionListener(this::categoryDropdownListener);
        return categoryCombo;
    }

    @SuppressWarnings("unchecked")
    private void categoryDropdownListener(ActionEvent actionEvent) {
        JComboBox<MovieCategoryDto> comboBox = (JComboBox<MovieCategoryDto>) actionEvent.getSource();
        if(comboBox.getSelectedIndex() == 0)
            initMoviePanel(getMovies());
        else
            initMoviePanel(getMovies(((MovieCategoryDto) comboBox.getSelectedItem()).getId()));
        movieListComponent.revalidate();
        movieListComponent.repaint();
    }


    private  List<SimpleMovie> getMovies(int categoryId) {
        return movieClient.getMoviesList(categoryId);
    }

    private List<SimpleMovie> getMovies() {
        return movieClient.getMoviesList();
    }

    private List<MovieCategoryDto> getCategories() {
        return movieClient.getCategories();
    }

    private MovieTile getMovieTitle(SimpleMovie simpleMovie) {
        String title = simpleMovie.getName();
        String url = "https://images-na.ssl-images-amazon.com/images/I/51D8AEqiZ-L.jpg"; //TODO
        MovieTile tile = new MovieTile(title, url);
        tile.addActionListener((event) -> { //TODO
            addCachedItem(Constants.MOVIE_ID_CACHE, String.valueOf(tile.getMovieId()));
            Main.setPanel(Main.Frame.DETAILS, getCache());
        });
        tile.setMovieId(simpleMovie.getId());
        return tile;
    }
}
