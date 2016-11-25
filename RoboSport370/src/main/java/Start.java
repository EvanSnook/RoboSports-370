
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Start extends Application{

    public static void main(String[] args) {
        Application.launch(Start.class);
    }

    public void start(Stage view){
        try {
            URL mainMenuURL = ViewController.class.getResource("/view/MainMenuView.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(mainMenuURL);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent mainMenu = fxmlLoader.load(mainMenuURL.openStream());

            Scene scene = new Scene(mainMenu);
            view.setScene(scene);
            view.setTitle("RoboSport370");
            view.show();

            fxmlLoader.<ViewController>getController().setStage(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
