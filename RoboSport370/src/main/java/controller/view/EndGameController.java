package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class EndGameController {
    @FXML
    public ListView gameOverviewListView;

    public void quit(/*MouseEvent mouseEvent*/) {
        System.exit(0);
    }

    public void showMainMenu(MouseEvent mouseEvent) {

    }
}
