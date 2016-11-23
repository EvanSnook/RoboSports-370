package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenu extends Application{

    public void start(Stage view){
        try {
            StackPane page = FXMLLoader.load(MainMenu.class.getResource("/MainMenuView.fxml"));
            Scene scene = new Scene(page);
            view.setScene(scene);
            view.setTitle("Main Menu");
            view.show();
        } catch (Exception ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Application.launch(MainMenu.class, (java.lang.String[])null);
    }

    public void GoToRules(){

    }
}
