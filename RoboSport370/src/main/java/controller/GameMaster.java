package controller;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import model.Game;
import model.HexNode;
import model.HexNodeIterator;
import model.Robot;
import model.Team;
import model.enums.BoardSize;
import model.enums.TeamColour;

// TODO GameMaster Class
public class GameMaster {
    //solid colours range from 0x000000ff to 0xffffffff (Black to White)
    private final String FOG_COLOUR = "0xddddddff";//grey
    private final String DEFAULT_COLOUR = "0xffffffff";//white
    private final String SELECTED_COLOUR = "0xaaaaaaff";//dark grey

    private static Game game;

    private HexNode selectedNode;
    private Robot currentRobot;
    private ForthInterpreter interpreter;

    @FXML
    public Pane gameContainer;

    @FXML
    public Button robotShoot;

    @FXML
    public Button robotMove;

    @FXML
    public TextArea OutputBox;

    public GameMaster() {}

    /**
     * The real constructor..
     * @param game - a game
     */
    public void setGame(Game game) {
        GameMaster.game = game;

        // TODO set GameTime action listener
        game.getGameTime().getPlayTimer().addActionListener(e -> {
        });

        linkPolygonsToHexNodes();
        initRobots();
        initStartTiles();
        makeFoggyOut();
        startPlay();
    }

    /**
     * Iterators through the board until it finds the selected Polygon that matches the event ID
     * Outputs to stderr if the HexNode could not be found
     *
     * @param mouseEvent a mouse event
     */
    public void hexNodeClicked(MouseEvent mouseEvent) {
        HexNodeIterator iterator = new HexNodeIterator(game.getBoard().getRoot());
        while ((iterator.getCurrentNode().getLabel().compareTo(((Node) mouseEvent.getSource()).getId())) != 0) {
            if (iterator.hasNext()) {
                iterator.next();
            } else {
                System.err.println("Can't find selected HexNode.");
            }
        }

        selectTile(iterator.getCurrentNode());
        OutputBox.setText(iterator.getCurrentNode().toString());



        //set robot to face selected node
        if(getCurrentRobot().getPosition().getR() == getSelectedNode()){
                getCurrentRobot().setFacing(0);
        }
        else if(getCurrentRobot().getPosition().getDR() == getSelectedNode()){
            getCurrentRobot().setFacing(1);
        }
        else if(getCurrentRobot().getPosition().getDL() == getSelectedNode()){
            getCurrentRobot().setFacing(2);
        }
        else if(getCurrentRobot().getPosition().getL() == getSelectedNode()){
            getCurrentRobot().setFacing(3);
        }
        else if(getCurrentRobot().getPosition().getUL() == getSelectedNode()){
            getCurrentRobot().setFacing(4);
        }
        else if(getCurrentRobot().getPosition().getUR() == getSelectedNode()){
            getCurrentRobot().setFacing(5);
        }
    }

    public void selectTile(HexNode node) {
        if(!node.getHexagon().getFill().toString().equals(FOG_COLOUR)) {
            //set the previously selected node to white
            if (selectedNode != null)
                selectedNode.getHexagon().setFill(Paint.valueOf(DEFAULT_COLOUR));
            selectedNode = node;
            selectedNode.getHexagon().setFill(Paint.valueOf(SELECTED_COLOUR));
        }
    }

    public void robotClicked(MouseEvent mouseEvent){
        for(Team t : game.getTeams()) {
            if (mouseEvent.getSource() == t.getScout().getRobotImage()) {
                OutputBox.setText(t.getScout().toString());
            } else if (mouseEvent.getSource() == t.getSniper().getRobotImage()) {
                OutputBox.setText(t.getSniper().toString());
            } else if (mouseEvent.getSource() == t.getTank().getRobotImage()) {
                OutputBox.setText(t.getTank().toString());
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public HexNode getSelectedNode() {
        return selectedNode;
    }

    public Robot getCurrentRobot() {
        return currentRobot;
    }

    public ForthInterpreter getInterpreter() {
        return interpreter;
    }

    private void initRobots(){
        for(Team t : game.getTeams()){

            String colour = t.getColour().toString().toLowerCase();

            ImageView scoutView =
                    (ImageView) gameContainer.lookup("#" + colour + "Scout");
            ImageView sniperView =
                    (ImageView) gameContainer.lookup("#" + colour + "Sniper");
            ImageView tankView =
                    (ImageView) gameContainer.lookup("#" + colour + "Tank");

            // If the team is disabled, disable the ImageView
            if(!t.isEnabled()){
                scoutView.setVisible(false);
                sniperView.setVisible(false);
                tankView.setVisible(false);
                continue;
            }

            t.getScout().setRobotImage(scoutView);
            t.getSniper().setRobotImage(sniperView);
            t.getTank().setRobotImage(tankView);
        }
    }

    private void initStartTiles(){

        for(Team t : game.getTeams()){

            String lowerColour = t.getColour().toString().toLowerCase();
            String upperColour = t.getColour().toString().toUpperCase();

            ImageView startView =
                    (ImageView) gameContainer.lookup("#" + lowerColour + "Start");

            // If the team is disabled, disable the ImageView
            if(!t.isEnabled()){
                startView.setVisible(false);
                game.getBoard().getCorner(t.getColour()).getHexagon().setOpacity(1.00);
                continue;
            }
            
            game.getBoard().getCorner(t.getColour()).addRobot(t.getScout());
            t.getScout().setPosition( game.getBoard().getCorner(t.getColour()));
            game.getBoard().getCorner(t.getColour()).addRobot(t.getSniper());
            t.getSniper().setPosition( game.getBoard().getCorner(t.getColour()));
            game.getBoard().getCorner(t.getColour()).addRobot(t.getTank());
            t.getTank().setPosition( game.getBoard().getCorner(t.getColour()));
        }
    }

    private void linkPolygonsToHexNodes(){
        HexNodeIterator iterator = new HexNodeIterator(game.getBoard().getRoot());
        while (iterator.hasNext()) {
            HexNode current = iterator.next();

            if (!current.canContainRobots()) break;

            current.setHexagon((Polygon) gameContainer.lookup("#" + current.getLabel()));
            current.getHexagon().setFill(Paint.valueOf(DEFAULT_COLOUR));
        }
    }

    public void showRules(/*MouseEvent mouseEvent*/) {

    }

    public void robotMove(){
        int xBuffer = 24;
        int yBuffer = 21;
        if (getCurrentRobot().getFacing() == 0 && getCurrentRobot().getPosition().getR().canContainRobots() != false) {
            getCurrentRobot().consumeMove();
            getCurrentRobot().getPosition().removeRobot(getCurrentRobot());
            getCurrentRobot().setPosition(getCurrentRobot().getPosition().getR());
            getCurrentRobot().getPosition().addRobot(getCurrentRobot());
            getCurrentRobot().getRobotImage().setLayoutX(getCurrentRobot().getPosition().getHexagon().getLayoutX() - xBuffer);
        }
        else if (getCurrentRobot().getFacing() == 1 && getCurrentRobot().getPosition().getDR().canContainRobots() != false) {
            getCurrentRobot().consumeMove();
            getCurrentRobot().getPosition().removeRobot(getCurrentRobot());
            getCurrentRobot().setPosition(getCurrentRobot().getPosition().getDR());
            getCurrentRobot().getPosition().addRobot(getCurrentRobot());
            getCurrentRobot().getRobotImage().setLayoutX(getCurrentRobot().getPosition().getHexagon().getLayoutX() - xBuffer);
            getCurrentRobot().getRobotImage().setLayoutY(getCurrentRobot().getPosition().getHexagon().getLayoutY() - yBuffer);

        }
        else if (getCurrentRobot().getFacing() == 2 && getCurrentRobot().getPosition().getDL().canContainRobots() != false) {
            getCurrentRobot().consumeMove();
            getCurrentRobot().getPosition().removeRobot(getCurrentRobot());
            getCurrentRobot().setPosition(getCurrentRobot().getPosition().getDL());
            getCurrentRobot().getPosition().addRobot(getCurrentRobot());
            getCurrentRobot().getRobotImage().setLayoutX(getCurrentRobot().getPosition().getHexagon().getLayoutX() - xBuffer);
            getCurrentRobot().getRobotImage().setLayoutY(getCurrentRobot().getPosition().getHexagon().getLayoutY() - yBuffer);

        }
        else if (getCurrentRobot().getFacing() == 3 && getCurrentRobot().getPosition().getL().canContainRobots() != false) {
            getCurrentRobot().consumeMove();
            getCurrentRobot().getPosition().removeRobot(getCurrentRobot());
            getCurrentRobot().setPosition(getCurrentRobot().getPosition().getL());
            getCurrentRobot().getPosition().addRobot(getCurrentRobot());
            getCurrentRobot().getRobotImage().setLayoutX(getCurrentRobot().getPosition().getHexagon().getLayoutX() - xBuffer);
            getCurrentRobot().getRobotImage().setLayoutY(getCurrentRobot().getPosition().getHexagon().getLayoutY() - yBuffer);
        }
        else if (getCurrentRobot().getFacing() == 4 && getCurrentRobot().getPosition().getUL().canContainRobots() != false) {
            getCurrentRobot().consumeMove();
            getCurrentRobot().getPosition().removeRobot(getCurrentRobot());
            getCurrentRobot().setPosition(getCurrentRobot().getPosition().getUL());
            getCurrentRobot().getPosition().addRobot(getCurrentRobot());
            getCurrentRobot().getRobotImage().setLayoutX(getCurrentRobot().getPosition().getHexagon().getLayoutX() - xBuffer);
            getCurrentRobot().getRobotImage().setLayoutY(getCurrentRobot().getPosition().getHexagon().getLayoutY() - yBuffer);
        }
        else if (getCurrentRobot().getFacing() == 5 && getCurrentRobot().getPosition().getUR().canContainRobots() != false) {
            getCurrentRobot().consumeMove();
            getCurrentRobot().getPosition().removeRobot(getCurrentRobot());
            getCurrentRobot().setPosition(getCurrentRobot().getPosition().getUR());
            getCurrentRobot().getPosition().addRobot(getCurrentRobot());
            getCurrentRobot().getRobotImage().setLayoutX(getCurrentRobot().getPosition().getHexagon().getLayoutX() - xBuffer);
            getCurrentRobot().getRobotImage().setLayoutY(getCurrentRobot().getPosition().getHexagon().getLayoutY() - yBuffer);

        }
        if(getCurrentRobot().getRemainingMoves() < 1){
            robotMove.setDisable(true);
        }
        return;

    }

    public void robotShoot(){
        if(getSelectedNode() != null){
            Iterator iterator = getSelectedNode().getRobots().iterator();
            while(iterator.hasNext()){
                getSelectedNode().getRobots().element().takeDamage(getCurrentRobot().getDamage());
                iterator.next();
            }
            robotShoot.setDisable(true);
            return;
        }
        else {
            robotShoot.setDisable(true);
            return;
        }


    }

    public void endTurn(){
        makeFoggyOut();
        //TODO hide all robots
        startPlay();
        robotShoot.setDisable(false);
    }

    public void startPlay(){
        if(currentRobot == null){
            currentRobot = game.getTeam(TeamColour.RED).getScout();
        } else {
            Team nextTeam = getNextTeam();
            currentRobot = nextTeam.getNextRobot();
        }
        // clear the fog
        clearAreaFog(game.getTeam(currentRobot.getColour()).getScout().getPosition(), game.getTeam(currentRobot.getColour()).getScout().getRange());
        clearAreaFog(game.getTeam(currentRobot.getColour()).getSniper().getPosition(), game.getTeam(currentRobot.getColour()).getSniper().getRange());
        clearAreaFog(game.getTeam(currentRobot.getColour()).getTank().getPosition(), game.getTeam(currentRobot.getColour()).getTank().getRange());
    }

    public Team getNextTeam(){
        Team returnTeam = null;
        //who's turn is it currently.

        switch(currentRobot.getColour()){
            case RED:
                //Who's turn is it going to be.
                if(game.getTeam(TeamColour.ORANGE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.ORANGE);
                } else if(game.getTeam(TeamColour.YELLOW).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.YELLOW);
                } else if(game.getTeam(TeamColour.GREEN).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.GREEN);
                } else if(game.getTeam(TeamColour.BLUE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.BLUE);
                } else if(game.getTeam(TeamColour.PURPLE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.PURPLE);
                } else {/*Red is only alivr*/}
                break;
            case ORANGE:
                if(game.getTeam(TeamColour.YELLOW).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.YELLOW);
                } else if(game.getTeam(TeamColour.GREEN).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.GREEN);
                } else if(game.getTeam(TeamColour.BLUE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.BLUE);
                } else if(game.getTeam(TeamColour.PURPLE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.PURPLE);
                } else if(game.getTeam(TeamColour.RED).getNextRobot() != null) {
                    returnTeam = game.getTeam(TeamColour.RED);
                } else {/*Orange is only alive*/}
                break;
            case BLUE:
                if(game.getTeam(TeamColour.PURPLE).getNextRobot() != null){
                returnTeam = game.getTeam(TeamColour.PURPLE);
                } else if(game.getTeam(TeamColour.RED).getNextRobot() != null) {
                returnTeam = game.getTeam(TeamColour.RED);
                } else if(game.getTeam(TeamColour.ORANGE).getNextRobot() != null){
                returnTeam = game.getTeam(TeamColour.ORANGE);
                } else if(game.getTeam(TeamColour.YELLOW).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.YELLOW);
                } else if(game.getTeam(TeamColour.GREEN).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.GREEN);
                } else {/*Blue is only alive*/}
                break;
            case GREEN:
                if(game.getTeam(TeamColour.BLUE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.BLUE);
                } else if(game.getTeam(TeamColour.PURPLE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.PURPLE);
                } else if(game.getTeam(TeamColour.RED).getNextRobot() != null) {
                    returnTeam = game.getTeam(TeamColour.RED);
                } else if(game.getTeam(TeamColour.ORANGE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.ORANGE);
                } else if(game.getTeam(TeamColour.YELLOW).getNextRobot() != null){
                        returnTeam = game.getTeam(TeamColour.YELLOW);
                } else {/*Green is only alive*/}
                break;
            case YELLOW:
                if(game.getTeam(TeamColour.GREEN).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.GREEN);
                } else if(game.getTeam(TeamColour.BLUE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.BLUE);
                } else if(game.getTeam(TeamColour.PURPLE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.PURPLE);
                } else if(game.getTeam(TeamColour.RED).getNextRobot() != null) {
                    returnTeam = game.getTeam(TeamColour.RED);
                } else if(game.getTeam(TeamColour.ORANGE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.ORANGE);

                } else {/*Yellow is only alive*/                }
                break;
            case PURPLE:
                if(game.getTeam(TeamColour.RED).getNextRobot() != null) {
                    returnTeam = game.getTeam(TeamColour.RED);
                } else if(game.getTeam(TeamColour.ORANGE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.ORANGE);
                } else if(game.getTeam(TeamColour.YELLOW).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.YELLOW);
                } else if(game.getTeam(TeamColour.GREEN).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.GREEN);
                } else if(game.getTeam(TeamColour.BLUE).getNextRobot() != null){
                    returnTeam = game.getTeam(TeamColour.BLUE);
                } else {/*Purple is only alive*/}
                break;
            default:
                break;
        }
        return returnTeam;
    }

    public void endMatch(){

    }

    /**
     * Clears an area of some radius
     * @param start  the not to start at.
     * @param radius number of nodes out not counting the center.
     */
    private void clearAreaFog(HexNode start, int radius){
        if(radius>5){
            System.out.println("radius too big must be less than 5 was: " + radius);
        }else{
            HexNodeIterator iterator = new HexNodeIterator(start, 0);
            while(iterator.getCurrentLayer() <= radius) {
                if (iterator.getCurrentNode().canContainRobots()) {
                    iterator.getCurrentNode().getHexagon().setFill(Paint.valueOf(DEFAULT_COLOUR));
                    for(Robot robot : iterator.getCurrentNode().getRobots()){
                        robot.getRobotImage().setVisible(true);
                    }
                }
                iterator.next();
            }
        }
    }

    /**
     * Makes the entire board foggy
     */
    private void makeFoggyOut(){
        selectedNode = null;
        HexNodeIterator iterator = new HexNodeIterator(game.getBoard().getRoot(), 0);
        int radius = game.getBoard().getSize().getValue();
        while(iterator.getCurrentLayer() <= radius){
            if(iterator.getCurrentNode().canContainRobots()){
                iterator.getCurrentNode().getHexagon().setFill(Paint.valueOf(FOG_COLOUR));
                for(Robot robot : iterator.getCurrentNode().getRobots()){
                    robot.getRobotImage().setVisible(false);
                }
            }
            iterator.next();
        }
    }
}
