package controller.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.text.html.ListView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import model.PublicLibrarian;
import model.PublicViewController;
import model.RobotAI;
import model.enums.RobotType;



public class RobotStatsController implements Initializable {

    @FXML
    ListView robotListView;

    public void uploadStats(/*MouseEvent mouseEvent*/) {

    }

    public void resetStats(/*MouseEvent mouseEvent*/) {

    }

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }

    public void initialize(URL location, ResourceBundle resources) {

        //robotListView.setItems(PublicLibrarian.getInstance().getRobots());
    }
}
