package controller.view;

import javafx.scene.input.MouseEvent;
import model.PublicViewController;

public class RobotStatsController {

    public void uploadStats(/*MouseEvent mouseEvent*/){

    }

    public void resetStats(/*MouseEvent mouseEvent*/){

    }

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }
}
