package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PublicGameMaster;
import model.PublicViewController;
import model.enums.BoardSize;


import java.io.IOException;
import java.net.URL;

public class ViewController {
    private static Stage view;
    private Stage rulesStage;
    private Scene currentScene;
    private Scene previousScene;

    private Scene mainMenuScene;
    private Scene createGameScene;
    private Scene statsScene;
    private Scene robotManagerScene;
    private Scene endGameScene;
    private Scene editRobotsScene;

    public ViewController() {
        PublicViewController.setInstance(this);

        createGameScene = new Scene(loadFxml("/view/CreateGameView.fxml"));
        statsScene = new Scene(loadFxml("/view/RobotStats.fxml"));
        robotManagerScene = new Scene(loadFxml("/view/RobotManagerView.fxml"));
        endGameScene = new Scene(loadFxml("/view/EndGameView.fxml"));
        editRobotsScene = new Scene(loadFxml("/view/EditRobotView.fxml"));
    }

    private static Parent loadFxml(String fxmlURL) {
        try {
            return FXMLLoader.load(ViewController.class.getResource(fxmlURL));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setStage(Stage stage) {
        view = stage;
        mainMenuScene = stage.getScene();
        currentScene = mainMenuScene;
    }

    public void setScene(Scene scene) {
        previousScene = currentScene;
        currentScene = scene;
        view.setScene(scene);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Set the scene of the ViewController
     * <p>
     * Available scenes include the following<br>
     * {@code MAIN_MENU}<br>
     * {@code CREATE_GAME}<br>
     * {@code ROBOT_MANAGER}<br>
     * {@code ROBOT_STATS}<br>
     * {@code SMALL_BOARD}<br>
     * {@code MEDIUM_BOARD}<br>
     * {@code END_GAME}
     *
     * @param sceneName the name of the scene from above
     */
    public void setScene(String sceneName) {
        switch (sceneName) {
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
            case "END_GAME":
                setScene(endGameScene);
                break;
            case "EDIT_ROBOTS":
                setScene(editRobotsScene);
                break;
            default:
                System.err.println("Unknown scene: " + sceneName);
        }
    }

    public void createGame(BoardSize size) {
        String sizeString = size.equals(BoardSize.SMALL) ? "Small" : "Medium";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/GameView" + sizeString + "Board.fxml"));
            Parent parent = fxmlLoader.load();
            GameMaster gm = fxmlLoader.getController();

            PublicGameMaster.setInstance(gm);

            setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the game
     */
    public void quit(/*MouseEvent mouseEvent*/) {
        System.exit(0);
    }

    public void showCreateGame(/*MouseEvent mouseEvent*/) {
        setScene("CREATE_GAME");
    }

    public void showStats(/*MouseEvent mouseEvent*/) {
        setScene("ROBOT_STATS");
    }

    public void showRoboManager(/*MouseEvent mouseEvent*/) {
        setScene("ROBOT_MANAGER");
    }

    public void showRules(MouseEvent mouseEvent) {
        if (rulesStage != null) {
            rulesStage.show();
            return;
        }
        Stage rulesStage = new Stage();
        Parent rulesMain = loadFxml("/view/RulesView.fxml");
        rulesStage.setScene(new Scene(rulesMain));
        rulesStage.initModality(Modality.NONE);

        rulesStage.initOwner(((Node) mouseEvent.getSource()).getScene().getWindow());
        rulesStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/redScout.PNG")));
        rulesStage.show();

        this.rulesStage = rulesStage;
    }
}