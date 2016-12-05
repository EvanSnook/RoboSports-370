package controller.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Game;
import model.PublicGameMaster;
import model.PublicLibrarian;
import model.PublicViewController;
import model.Robot;
import model.RobotAI;
import model.Team;
import model.enums.BoardSize;
import model.enums.RobotType;
import model.enums.TeamColour;

public class CreateGameController implements Initializable {
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

    @FXML
    private ChoiceBox<RobotAI> team1Scout;
    @FXML
    private ChoiceBox<RobotAI> team2Scout;
    @FXML
    private ChoiceBox<RobotAI> team3Scout;
    @FXML
    private ChoiceBox<RobotAI> team4Scout;
    @FXML
    private ChoiceBox<RobotAI> team5Scout;
    @FXML
    private ChoiceBox<RobotAI> team6Scout;

    @FXML
    private ChoiceBox<RobotAI> team1Sniper;
    @FXML
    private ChoiceBox<RobotAI> team2Sniper;
    @FXML
    private ChoiceBox<RobotAI> team3Sniper;
    @FXML
    private ChoiceBox<RobotAI> team4Sniper;
    @FXML
    private ChoiceBox<RobotAI> team5Sniper;
    @FXML
    private ChoiceBox<RobotAI> team6Sniper;

    @FXML
    private ChoiceBox<RobotAI> team1Tank;
    @FXML
    private ChoiceBox<RobotAI> team2Tank;
    @FXML
    private ChoiceBox<RobotAI> team3Tank;
    @FXML
    private ChoiceBox<RobotAI> team4Tank;
    @FXML
    private ChoiceBox<RobotAI> team5Tank;
    @FXML
    private ChoiceBox<RobotAI> team6Tank;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<RobotAI> tanks = (ArrayList<RobotAI>) PublicLibrarian.getInstance().getRobots()
                .stream()
                .filter(r -> r.getType().equals(RobotType.TANK))
                .collect(Collectors.toList());

        ArrayList<RobotAI> snipers = (ArrayList<RobotAI>) PublicLibrarian.getInstance().getRobots()
                .stream()
                .filter(r -> r.getType().equals(RobotType.SNIPER))
                .collect(Collectors.toList());

        ArrayList<RobotAI> scouts = (ArrayList<RobotAI>) PublicLibrarian.getInstance().getRobots()
                .stream()
                .filter(r -> r.getType().equals(RobotType.SCOUT))
                .collect(Collectors.toList());

        team1Tank.getItems().addAll(tanks);
        team2Tank.getItems().addAll(tanks);
        team3Tank.getItems().addAll(tanks);
        team4Tank.getItems().addAll(tanks);
        team5Tank.getItems().addAll(tanks);
        team6Tank.getItems().addAll(tanks);

        team1Sniper.getItems().addAll(snipers);
        team2Sniper.getItems().addAll(snipers);
        team3Sniper.getItems().addAll(snipers);
        team4Sniper.getItems().addAll(snipers);
        team5Sniper.getItems().addAll(snipers);
        team6Sniper.getItems().addAll(snipers);

        team1Scout.getItems().addAll(scouts);
        team2Scout.getItems().addAll(scouts);
        team3Scout.getItems().addAll(scouts);
        team4Scout.getItems().addAll(scouts);
        team5Scout.getItems().addAll(scouts);
        team6Scout.getItems().addAll(scouts);
    }

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
            createRobotsForTeam(redTeam);
            createRobotsForTeam(greenTeam);
        } else if(getNumTeams() == 3){
            createRobotsForTeam(redTeam);
            createRobotsForTeam(yellowTeam);
            createRobotsForTeam(blueTeam);
        } else if(getNumTeams() == 6){
            createRobotsForTeam(redTeam);
            createRobotsForTeam(greenTeam);
            createRobotsForTeam(yellowTeam);
            createRobotsForTeam(blueTeam);
            createRobotsForTeam(orangeTeam);
            createRobotsForTeam(purpleTeam);
        }
        PublicGameMaster.getInstance().setGame(game);
    }

    private void createRobotsForTeam(Team team){
        ArrayList<ToggleButton> buttons = new ArrayList<>();
        ArrayList<ChoiceBox<RobotAI>> scouts = new ArrayList<>();
        ArrayList<ChoiceBox<RobotAI>> snipers = new ArrayList<>();
        ArrayList<ChoiceBox<RobotAI>> tanks = new ArrayList<>();

        buttons.add(toggleButton1);
        buttons.add(toggleButton2);
        buttons.add(toggleButton3);
        buttons.add(toggleButton4);
        buttons.add(toggleButton5);
        buttons.add(toggleButton6);

        scouts.add(team1Scout);
        scouts.add(team2Scout);
        scouts.add(team3Scout);
        scouts.add(team4Scout);
        scouts.add(team5Scout);
        scouts.add(team6Scout);

        snipers.add(team1Sniper);
        snipers.add(team2Sniper);
        snipers.add(team3Sniper);
        snipers.add(team4Sniper);
        snipers.add(team5Sniper);
        snipers.add(team6Sniper);

        tanks.add(team1Tank);
        tanks.add(team2Tank);
        tanks.add(team3Tank);
        tanks.add(team4Tank);
        tanks.add(team5Tank);
        tanks.add(team6Tank);

        int index = Arrays.binarySearch(getActiveTeamColours(), team.getColour());

        if(!buttons.get(index).isDisabled() && buttons.get(index).isSelected()){
            createRobotAIsForTeam(team,
                    (RobotAI) scouts.get(index).getValue().clone(),
                    (RobotAI) snipers.get(index).getValue().clone(),
                    (RobotAI) tanks.get(index).getValue().clone()
                    );
        } else {
            team.setScout(Robot.getBuilder(false).getScout().setTeamColour(team.getColour()).build());
            team.setSniper(Robot.getBuilder(false).getSniper().setTeamColour(team.getColour()).build());
            team.setTank(Robot.getBuilder(false).getTank().setTeamColour(team.getColour()).build());
        }

    }

    private void createRobotAIsForTeam(Team team, RobotAI scout, RobotAI sniper, RobotAI tank){
        scout.setColour(team.getColour());
        sniper.setColour(team.getColour());
        tank.setColour(team.getColour());

        team.setScout(scout);
        team.setSniper(sniper);
        team.setTank(tank);
    }

    private TeamColour[] getActiveTeamColours(){
        TeamColour[] result = new TeamColour[getNumTeams()];

        switch(getNumTeams()){
            case 2:
                result[0] = TeamColour.RED;
                result[1] = TeamColour.GREEN;
                break;
            case 3:
                result[0] = TeamColour.RED;
                result[1] = TeamColour.YELLOW;
                result[2] = TeamColour.BLUE;
                break;
            case 6:
                result[0] = TeamColour.RED;
                result[1] = TeamColour.ORANGE;
                result[2] = TeamColour.YELLOW;
                result[3] = TeamColour.GREEN;
                result[4] = TeamColour.BLUE;
                result[5] = TeamColour.PURPLE;
                break;
        }

        return result;
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
            toggleDropBoxes(tb, false);
        }
        else{
            tb.setText("Human");
            toggleDropBoxes(tb, true);
        }
    }

    public void toggleDropBoxes(ToggleButton tb, Boolean  state){
        switch(tb.getId()) {
            case "toggleButton1":
                team1Scout.setDisable(state);
                team1Sniper.setDisable(state);
                team1Tank.setDisable(state);
                break;
            case "toggleButton2":
                team2Scout.setDisable(state);
                team2Sniper.setDisable(state);
                team2Tank.setDisable(state);
                break;
            case "toggleButton3":
                team3Scout.setDisable(state);
                team3Sniper.setDisable(state);
                team3Tank.setDisable(state);
                break;
            case "toggleButton4":
                team4Scout.setDisable(state);
                team4Sniper.setDisable(state);
                team4Tank.setDisable(state);
                break;
            case "toggleButton5":
                team5Scout.setDisable(state);
                team5Sniper.setDisable(state);
                team5Tank.setDisable(state);
                break;
            case "toggleButton6":
                team6Scout.setDisable(state);
                team6Sniper.setDisable(state);
                team6Tank.setDisable(state);
        }
    }
}
