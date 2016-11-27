package controller.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.PublicGameMaster;
import model.PublicViewController;
import model.enums.BoardSize;

public class CreateGameController {
    @FXML
    public RadioButton teams2RadioButton;
    @FXML
    public RadioButton teams3RadioButton;
    @FXML
    public RadioButton teams6RadioButton;

    @FXML
    public RadioButton hexagons5RadioButton;
    @FXML
    public RadioButton hexagons7RadioButton;

    @FXML
    private Slider robotThinkTimeSlider;
    @FXML
    private Label robotThinkTimeValueLabel;

    public void updateRobotThinkTimeValueLabel(/*MouseEvent mouseEvent*/){
        System.out.println("Im called!");
        robotThinkTimeValueLabel.setText(String.valueOf(robotThinkTimeSlider.getValue()));
    }

    public void createGame(MouseEvent mouseEvent) {
        if(getBoardSize().equals(BoardSize.SMALL)){
            PublicViewController.getInstance().setScene("SMALL_BOARD");
        } else {
            PublicViewController.getInstance().setScene("MEDIUM_BOARD");
        }

        PublicGameMaster.getInstance().setGame(new Game(getBoardSize(), getNumTeams()));
    }

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }

    private int getNumTeams(){
        return teams2RadioButton.selectedProperty().get() ? 2 :
                (teams3RadioButton.selectedProperty().get() ? 3 : 6);
    }

    private BoardSize getBoardSize(){
        return hexagons5RadioButton.selectedProperty().get() ? BoardSize.SMALL : BoardSize.MEDIUM;
    }

    public Game getGame(){
        return new Game(getBoardSize(), getNumTeams());
    }
}
