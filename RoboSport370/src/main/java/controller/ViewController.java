package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PublicViewController;

public class ViewController{
    private static Stage view;
    private Stage rulesStage;
    private Scene currentScene;
    private Scene previousScene;

    private Scene mainMenuScene;
    private Scene createGameScene;
    private Scene statsScene;
    private Scene robotManagerScene;

    public ViewController() {
        PublicViewController.setInstance(this);
        try {
            createGameScene = new Scene(loadFxml("/view/CreateGameView.fxml"));
            statsScene = new Scene(loadFxml("/view/RobotStats.fxml"));
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
        previousScene = currentScene;
        currentScene = scene;
        view.setScene(scene);
    }

    /**
     * Set the scene of the ViewController
     * <p>
     *     Available scenes include the following<br>
     *     {@code MAIN_MENU}<br>
     *     {@code CREATE_GAME}<br>
     *     {@code ROBOT_MANAGER}<br>
     *     {@code ROBOT_STATS}
     * @param sceneName
     *              the name of the scene from above
     */
    public void setScene(String sceneName){
        switch(sceneName){
            case "MAIN_MENU":
                setScene(mainMenuScene);
                break;
            case "CREATE_GAME":
                setScene(createGameScene);
                break;
            case "ROBOT_MANAGER":
                setScene(robotManagerScene);
                break;
            case "ROBOT_STATS":
                setScene(statsScene);
                break;
            default:
                System.err.println("Unknown scene: " + sceneName);
        }
    }

    /**
     * Close the game
     */
    public void quit(/*MouseEvent mouseEvent*/){
        System.exit(0);
    }

    public void showCreateGame(/*MouseEvent mouseEvent*/){
        setScene("CREATE_GAME");
    }

    public void showStats(/*MouseEvent mouseEvent*/){
        setScene("ROBOT_STATS");
    }

    public void showRoboManager(/*MouseEvent mouseEvent*/){
        setScene("ROBOT_MANAGER");
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

    private static Parent loadFxml(String fxmlURL) throws IOException{
        return FXMLLoader.load(ViewController.class.getResource(fxmlURL));
    }
}