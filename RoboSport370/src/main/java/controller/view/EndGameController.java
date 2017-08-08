package controller.view;

import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javax.annotation.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import model.PublicGameMaster;
import model.PublicViewController;
import model.Team;

public class EndGameController implements Initializable{
    @FXML
    public ListView gameOverviewListView;
    @FXML
    private Label resultText;

    public void quit() {
        System.exit(0);
    }

    public void showMainMenu() {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }

    //get id of label and set the text to
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle){
        long remainingTeams = PublicGameMaster.getInstance().getGame().getTeams().stream()
                .sequential()
                .filter(Team::isEnabled)
                .filter(t -> t.remainingRobots() > 0)
                .count();

        if(remainingTeams == 1){
            resultText.setText(PublicGameMaster.getInstance().getGame().getWinningTeam().getColour() + " TEAM WINS!");
        }else{
            resultText.setText("DRAW!");
        }
    }
}
