package controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewController extends Application{

// this class doe not need constructor because it is application and uses start
//    ViewController(){
//        mainMenu = new MainMenu();
//    }

    public void start(Stage view){
        try {
            StackPane page = FXMLLoader.load(ViewController.class.getResource("/view/MainMenuView.fxml"));
            Scene scene = new Scene(page);
            view.setScene(scene);
            view.setTitle("Main Menu");
            view.show();
        } catch (Exception ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GoToRules(){

    }


}