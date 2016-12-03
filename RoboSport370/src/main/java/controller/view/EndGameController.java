package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.PublicViewController;

public class EndGameController {
    @FXML
    public ListView gameOverviewListView;

    public void quit() {
        System.exit(0);
    }

    public void showMainMenu() {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }
}
