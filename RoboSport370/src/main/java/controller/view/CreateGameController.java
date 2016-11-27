package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import model.PublicViewController;

public class CreateGameController {
    @FXML
    private Slider RobotThinkTimeSlider;

    @FXML
    private Label RobotThinkTimeValueLabel;


    public void updateRobotThinkTimeValueLabel(/*MouseEvent mouseEvent*/){
        System.out.println("Im called!");
        RobotThinkTimeValueLabel.setText(String.valueOf(RobotThinkTimeSlider.getValue()));
    }

    public void createGame(/*MouseEvent mouseEvent*/){

    }

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }
}
