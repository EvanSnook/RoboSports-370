package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

    @FXML
    public ToggleButton toggleButton1;
    @FXML
    public ToggleButton toggleButton2;
    @FXML
    public ToggleButton toggleButton3;
    @FXML
    public ToggleButton toggleButton4;
    @FXML
    public ToggleButton toggleButton5;
    @FXML
    public ToggleButton toggleButton6;

    @FXML
    public HBox Team1Box;
    @FXML
    public HBox Team2Box;
    @FXML
    public HBox Team3Box;
    @FXML
    public HBox Team4Box;
    @FXML
    public HBox Team5Box;
    @FXML
    public HBox Team6Box;

    @FXML
    private Slider robotThinkTimeSlider;
    @FXML
    private Label robotThinkTimeValueLabel;

    public void createGame(MouseEvent mouseEvent) {
        PublicViewController.getInstance().createGame(getBoardSize());

        Game game = new Game(getBoardSize(), getNumTeams());

        Team redTeam = game.getTeam(TeamColour.RED);
        Team yellowTeam = game.getTeam(TeamColour.YELLOW);
        Team blueTeam = game.getTeam(TeamColour.BLUE);
        Team greenTeam = game.getTeam(TeamColour.GREEN);
        Team purpleTeam = game.getTeam(TeamColour.PURPLE);
        Team orangeTeam = game.getTeam(TeamColour.ORANGE);

        // TODO instantiate all of the robots in the teams
        // TODO Move this to separate functions
        if(getNumTeams() == 2){
            redTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.RED).build());
            redTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.RED).build());
            redTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.RED).build());

            greenTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.GREEN).build());
            greenTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.GREEN).build());
            greenTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.GREEN).build());

        } else if(getNumTeams() == 3){
            redTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.RED).build());
            redTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.RED).build());
            redTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.RED).build());

            yellowTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.YELLOW).build());
            yellowTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.YELLOW).build());
            yellowTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.YELLOW).build());

            blueTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.BLUE).build());
            blueTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.BLUE).build());
            blueTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.BLUE).build());

        } else if(getNumTeams() == 6){
            redTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.RED).build());
            redTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.RED).build());
            redTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.RED).build());

            greenTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.GREEN).build());
            greenTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.GREEN).build());
            greenTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.GREEN).build());

            yellowTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.YELLOW).build());
            yellowTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.YELLOW).build());
            yellowTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.YELLOW).build());

            blueTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.BLUE).build());
            blueTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.BLUE).build());
            blueTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.BLUE).build());

            orangeTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.ORANGE).build());
            orangeTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.ORANGE).build());
            orangeTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.ORANGE).build());

            purpleTeam.setScout(Robot.getBuilder(false).getScout().setTeamColour(TeamColour.PURPLE).build());
            purpleTeam.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(TeamColour.PURPLE).build());
            purpleTeam.setTank(Robot.getBuilder(false).getTank().setTeamColour(TeamColour.PURPLE).build());
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

    public void updateThinkTimeLabel(){
        int value = (int)robotThinkTimeSlider.getValue();
        String s = value + "s";
        robotThinkTimeValueLabel.setText(s);
    }

    public void changeVisibleTeams(){
        if(getNumTeams() == 2) {
            Team1Box.setVisible(true);
            Team2Box.setVisible(true);
            Team3Box.setVisible(false);
            Team4Box.setVisible(false);
            Team5Box.setVisible(false);
            Team6Box.setVisible(false);
            hexagons5RadioButton.setVisible(true);
            hexagons7RadioButton.setVisible(false);
            hexagons5RadioButton.setSelected(true);
        }
        if(getNumTeams() == 3) {
            Team1Box.setVisible(true);
            Team2Box.setVisible(true);
            Team3Box.setVisible(true);
            Team4Box.setVisible(false);
            Team5Box.setVisible(false);
            Team6Box.setVisible(false);
            hexagons5RadioButton.setVisible(true);
            hexagons7RadioButton.setVisible(true);
        }
        if(getNumTeams() == 6) {
            Team1Box.setVisible(true);
            Team2Box.setVisible(true);
            Team3Box.setVisible(true);
            Team4Box.setVisible(true);
            Team5Box.setVisible(true);
            Team6Box.setVisible(true);
            hexagons5RadioButton.setVisible(false);
            hexagons7RadioButton.setVisible(true);
            hexagons7RadioButton.setSelected(true);
        }
    }

    public void togglePlayer(MouseEvent mouseEvent ){
        ToggleButton tb = (ToggleButton)mouseEvent.getSource();
        if(tb.isSelected()){
            tb.setText("AI");
        }
        else{
            tb.setText("Human");
        }
    }
}
