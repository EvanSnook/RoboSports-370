package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.PublicGameMaster;
import model.PublicViewController;
import model.Robot;
import model.RobotBuilder;
import model.Team;
import model.enums.BoardSize;
import model.enums.TeamColour;

public class CreateGameController {
    @FXML
    public RadioButton teams2RadioButton;
    @FXML
    public RadioButton teams3RadioButton;
    @FXML
    public RadioButton teams6RadioButton;

    @FXML
    public RadioButton hexagons5RadioButton;
    @FXML
    public RadioButton hexagons7RadioButton;

    public ToggleButton toggleButton1;
    public ToggleButton toggleButton2;
    public ToggleButton toggleButton3;
    public ToggleButton toggleButton4;
    public ToggleButton toggleButton5;
    public ToggleButton toggleButton6;

    @FXML
    private Slider robotThinkTimeSlider;
    @FXML
    private Label robotThinkTimeValueLabel;

    public void updateRobotThinkTimeValueLabel(/*MouseEvent mouseEvent*/) {
        System.out.println("Im called!");
        robotThinkTimeValueLabel.setText(String.valueOf(robotThinkTimeSlider.getValue()));
    }

    public void createGame(MouseEvent mouseEvent) {
        PublicViewController.getInstance().createGame(getBoardSize());

        Game game = new Game(getBoardSize(), getNumTeams());

        // TODO instantiate all of the robots in the teams
        if(getNumTeams() == 2){
            Team redTeam = game.getTeam(TeamColour.RED);
            Team greenTeam = game.getTeam(TeamColour.GREEN);

            redTeam.setScout(Robot.getBuilder(false).getScout().build());
            redTeam.setSniper(Robot.getBuilder(false).getSniper().build());
            redTeam.setTank(Robot.getBuilder(false).getTank().build());

            greenTeam.setScout(Robot.getBuilder(false).getScout().build());
            greenTeam.setSniper(Robot.getBuilder(false).getSniper().build());
            greenTeam.setTank(Robot.getBuilder(false).getTank().build());
        } else if(getNumTeams() == 3){

        } else if(getNumTeams() == 6){

        }

        PublicGameMaster.getInstance().setGame(game);
    }

    public void showMainMenu(MouseEvent mouseEvent) {
        PublicViewController.getInstance().setScene("MAIN_MENU");
    }

    private int getNumTeams() {
        return teams2RadioButton.selectedProperty().get() ? 2 :
                (teams3RadioButton.selectedProperty().get() ? 3 : 6);
    }

    private BoardSize getBoardSize() {
        return hexagons5RadioButton.selectedProperty().get() ? BoardSize.SMALL : BoardSize.MEDIUM;
    }

    public Game getGame() {
        return new Game(getBoardSize(), getNumTeams());
    }
}
