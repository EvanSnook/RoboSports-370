package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewController{
    private Stage view;
    private Stage rulesStage;
    private Scene currentScene;
    private Scene mainMenuScene;

    public ViewController() {}

    public void setStage(Stage stage) {
        this.view = stage;
        this.mainMenuScene = stage.getScene();
        this.currentScene = mainMenuScene;
    }

    //universal actions
    public void showMainMenu(/*MouseEvent mouseEvent*/){

    }
    public void quit(/*MouseEvent mouseEvent*/){

    }

    //CreateGame Actions
    public void updateRobotThinkTimeValueLabel(/*MouseEvent mouseEvent*/){

    }
    public void createGame(/*MouseEvent mouseEvent*/){

    }

    //EditRobot Actions
    public void createNewRobot(/*MouseEvent mouseEvent*/){

    }
    public void saveRobot(/*MouseEvent mouseEvent*/){

    }
    public void cancelRobot(/*MouseEvent mouseEvent*/){

    }

    //MainMenu Actions
    public void showCreateGame(/*MouseEvent mouseEvent*/){

    }
    public void showRules(MouseEvent mouseEvent) {
        if(rulesStage != null) {
            rulesStage.show();
            return;
        }
        try {
            Stage rulesStage = new Stage();
            Parent rulesMain = FXMLLoader.load(ViewController.class.getResource("/view/RulesView.fxml"));
            rulesStage.setScene(new Scene(rulesMain));
            rulesStage.initModality(Modality.NONE);

            rulesStage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
            rulesStage.show();

            this.rulesStage = rulesStage;
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void showStats(/*MouseEvent mouseEvent*/){

    }
    public void showRoboManager(/*MouseEvent mouseEvent*/){

    }

    //RoboManager Actions
    public void editRobot(/*MouseEvent mouseEvent*/){

    }
    public void importRobot(/*MouseEvent mouseEvent*/){

    }
    public void exportRobot(/*MouseEvent mouseEvent*/){

    }
    public void deleteRobot(/*MouseEvent mouseEvent*/){

    }

    //Stats Actions
    public void uploadStats(/*MouseEvent mouseEvent*/){

    }
    public void resetStats(/*MouseEvent mouseEvent*/){

    }
}