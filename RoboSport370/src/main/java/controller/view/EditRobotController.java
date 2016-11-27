package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.PublicViewController;

public class EditRobotController {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField teamTextField;
    @FXML
    public TextArea forthCodeTextArea;
    @FXML
    public ListView robotsListView;

    public void createNewRobot(/*MouseEvent mouseEvent*/){

    }

    public void saveRobot(/*MouseEvent mouseEvent*/){

    }

    public void cancelRobot(/*MouseEvent mouseEvent*/){

    }

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }
}
