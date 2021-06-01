package pl.sdacademy.pogodynka.ui.fx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.sdacademy.pogodynka.controller.WeatherController;
import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.model.dto.WeatherData;

import java.io.IOException;
import java.util.TreeSet;

public class JavaFxMain extends Application {

    WeatherData lastWeatherData;

    @FXML
    Label weatherHeader;

    @FXML
    Label weatherTemperature;

    @FXML
    Pane weatherInfoPane;

    @FXML
    ComboBox<String> citiesList;

    WeatherController controller = new WeatherController();
    @Override
    public void start(Stage primaryStage) throws WeatherNotFoundException, IOException {

        lastWeatherData = controller.getWeatherDataForCity("Jasło");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("template.fxml"));
        primaryStage.setTitle("Registration Form FXML Application");
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        weatherInfoPane.setVisible(false);
        TreeSet<String> cityNames = new TreeSet<>(controller.getCityNames());
        citiesList.setItems(FXCollections.observableArrayList(cityNames));

    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    public void citySelected(ActionEvent event) throws WeatherNotFoundException {
        String value = citiesList.getValue();
        WeatherData weatherDataForCity = controller.getWeatherDataForCity(value);
        weatherHeader.setText(String.format("Pogoda dla %s, %s", weatherDataForCity.getCity(), "PL"));
        weatherTemperature.setText(String.format("%s \u2103", weatherDataForCity.getTemperature()));
        System.out.println(weatherDataForCity);
        weatherInfoPane.setVisible(true);
    }
}
