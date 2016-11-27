package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import model.PublicViewController;

public class CreateGameController {
    @FXML
    private Slider robotThinkTimeSlider;
    @FXML
    private Label robotThinkTimeValueLabel;

    public void updateRobotThinkTimeValueLabel(/*MouseEvent mouseEvent*/){
        System.out.println("Im called!");
        robotThinkTimeValueLabel.setText(String.valueOf(robotThinkTimeSlider.getValue()));
    }

    public void createGame(/*MouseEvent mouseEvent*/){

    }

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }
}
