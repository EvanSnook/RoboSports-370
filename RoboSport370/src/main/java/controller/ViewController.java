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
}