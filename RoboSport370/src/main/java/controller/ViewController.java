package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewController{
    private static Stage view;
    private static Stage rulesStage;
    private static Scene currentScene;

    private static Scene mainMenuScene;
    private static Scene createGameScene;
    private static Scene statsScene;
    private static Scene robotManagerScene;

    static{
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

    public ViewController() {
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

    //universal actions
    public void showMainMenu(/*MouseEvent mouseEvent*/){
        setScene(mainMenuScene);
    }

    public void quit(/*MouseEvent mouseEvent*/){
        System.exit(0);
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

    private static Parent loadFxml(String fxmlURL) throws IOException{
        return FXMLLoader.load(ViewController.class.getResource(fxmlURL));
    }

    // TODO What is this?
    public void updateRobotThinkTimeLabel(ScrollEvent scrollEvent) {
    }
}