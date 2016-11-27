package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.PublicViewController;

public class RobotManagerController {
    @FXML
    public ListView localRobotsListView;
    @FXML
    public ListView onlineRobotsListView;

    public void editRobot(/*MouseEvent mouseEvent*/){

    }

    public void importRobot(/*MouseEvent mouseEvent*/){

    }

    public void exportRobot(/*MouseEvent mouseEvent*/){

    }

    public void deleteRobot(/*MouseEvent mouseEvent*/){

    }

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }
}
