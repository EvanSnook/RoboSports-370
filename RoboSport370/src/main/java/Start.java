import controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class Start extends Application {

    public static void main(String[] args) {
        Application.launch(Start.class);
    }

    public void start(Stage view) {
        try {
            URL mainMenuURL = ViewController.class.getResource("/view/MainMenuView.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(mainMenuURL);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent mainMenu = fxmlLoader.load(mainMenuURL.openStream());

            Scene scene = new Scene(mainMenu);
            view.setScene(scene);
            view.setTitle("RoboSport370");
            view.getIcons().add(new Image(getClass().getResourceAsStream("/images/redScout.PNG")));
            view.show();

            fxmlLoader.<ViewController>getController().setStage(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
