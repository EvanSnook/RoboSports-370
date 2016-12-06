package controller.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.PublicLibrarian;
import model.PublicViewController;
import model.RobotAI;
import model.Stats;


public class RobotStatsController implements Initializable {

    @FXML
    private ListView<RobotAI> robotListView;

    @FXML
    private Label teamName;
    @FXML
    private Label robotName;
    @FXML
    private Label matches;
    @FXML
    private Label wins;
    @FXML
    private Label losses;
    @FXML
    private Label lived;
    @FXML
    private Label died;
    @FXML
    private Label executions;
    @FXML
    private Label killed;
    @FXML
    private Label moved;
    @FXML
    private Label absorbed;

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }

    public void initialize(URL location, ResourceBundle resources) {
        robotListView.getItems().addAll(PublicLibrarian.getInstance().getRobots());

        robotListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateStats());

        robotListView.getSelectionModel().select(0);
    }

    private void updateStats(){
        RobotAI r = getSelectedRobot();
        Stats s = r.getStats();

        teamName.setText(r.getTeam());
        robotName.setText(r.getName());
        matches.setText(String.valueOf(s.getMatches()));
        wins.setText(String.valueOf(s.getWins()));
        losses.setText(String.valueOf(s.getLosses()));
        lived.setText(String.valueOf(s.getLived()));
        died.setText(String.valueOf(s.getDied()));
        executions.setText(String.valueOf(s.getExecutions()));
        killed.setText(String.valueOf(s.getKilled()));
        moved.setText(String.valueOf(s.getMoved()));
        absorbed.setText(String.valueOf(s.getAbsorbed()));
    }

    private RobotAI getSelectedRobot(){
        return robotListView.getSelectionModel().getSelectedItem();
    }
}
