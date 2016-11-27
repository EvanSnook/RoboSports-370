package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PublicViewController;

public class ViewController{
    private static Stage view;
    private Stage rulesStage;
    private Scene currentScene;

    private Scene mainMenuScene;
    private Scene createGameScene;
    private Scene statsScene;
    private Scene robotManagerScene;

    public ViewController() {
        PublicViewController.setInstance(this);
        try {
            if(createGameScene == null)
                createGameScene = new Scene(loadFxml("/view/CreateGameView.fxml"));
            if(statsScene == null)
                statsScene = new Scene(loadFxml("/view/RobotStats.fxml"));
            if(robotManagerScene == null)
                robotManagerScene = new Scene(loadFxml("/view/RobotManagerView.fxml"));
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        view = stage;
        mainMenuScene = stage.getScene();
        currentScene = mainMenuScene;
    }

    public void setScene(Scene scene){
        currentScene = scene;
        view.setScene(scene);
    }

    public void setScene(String sceneName){
        switch(sceneName){
            case "MAIN_MENU":
                setScene(mainMenuScene);
                break;
            default:
                System.err.println("Unknown scene: " + sceneName);
        }
    }

    //universal actions
    public void showMainMenu(/*MouseEvent mouseEvent*/){
        setScene(mainMenuScene);
    }

    public void quit(/*MouseEvent mouseEvent*/){
        System.exit(0);
    }

    //EditRobot Actions
    public void createNewRobot(/*MouseEvent mouseEvent*/){

    }

    public void saveRobot(/*MouseEvent mouseEvent*/){

    }

    public void cancelRobot(/*MouseEvent mouseEvent*/){

    }

    public void showCreateGame(/*MouseEvent mouseEvent*/){
        setScene(createGameScene);
    }

    public void showRules(MouseEvent mouseEvent) {
        if(rulesStage != null) {
            rulesStage.show();
            return;
        }
        try {
            Stage rulesStage = new Stage();
            Parent rulesMain = loadFxml("/view/RulesView.fxml");
            rulesStage.setScene(new Scene(rulesMain));
            rulesStage.initModality(Modality.NONE);

            rulesStage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
            rulesStage.show();

            rulesStage = rulesStage;
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void showStats(/*MouseEvent mouseEvent*/){
        setScene(statsScene);
    }

    public void showRoboManager(/*MouseEvent mouseEvent*/){
        setScene(robotManagerScene);
    }

    private static Parent loadFxml(String fxmlURL) throws IOException{
        return FXMLLoader.load(ViewController.class.getResource(fxmlURL));
    }
}