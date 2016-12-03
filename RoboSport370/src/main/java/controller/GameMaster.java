package controller;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Game;
import model.HexNode;
import model.HexNodeIterator;
import model.PublicViewController;
import model.Robot;
import model.Team;
import model.enums.TeamColour;

// TODO GameMaster Class
public class GameMaster {
    //solid colours range from 0x000000ff to 0xffffffff (Black to White)
    public static final String FOG_COLOUR = "0xddddddff";//grey
    public static final String DEFAULT_COLOUR = "0xffffffff";//white
    public static final String SELECTED_COLOUR = "0xaaaaaaff";//dark grey

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
        outputTile();
        setRobotFacing();
    }

    public void setRobotFacing(){
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

    public void outputTile(){
        if(selectedNode != null){
            if (!selectedNode.isFoggy()){
                String output = "";
                for (Robot r: selectedNode.getRobots()) {
                    output += r.toOutput() + "\n";
                }
                OutputBox.setText(output + selectedNode.toString());
            }
            else {
                OutputBox.setText(selectedNode.toString());
            }
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
            if(t.isEnabled()) {
                if (mouseEvent.getSource() == t.getScout().getRobotImage()) {
                    selectTile(t.getScout().getPosition());
                } else if (mouseEvent.getSource() == t.getSniper().getRobotImage()) {
                    selectTile(t.getSniper().getPosition());
                } else if (mouseEvent.getSource() == t.getTank().getRobotImage()) {
                    selectTile(t.getTank().getPosition());
                }

                outputTile();
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

            String colour = t.getColour().toString().toLowerCase();

            ImageView startView =
                    (ImageView) gameContainer.lookup("#" + colour + "Start");

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

    public void showRules(MouseEvent mouseEvent){
        PublicViewController.getInstance().showRules(mouseEvent);
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
        draw();
        return;
    }

    public void robotShoot(){
        if(getSelectedNode() != null){
            getSelectedNode().getRobots().forEach(r -> r.takeDamage(getCurrentRobot().getDamage()));

            robotShoot.setDisable(true);
        } else {
            robotShoot.setDisable(true);
        }
        if(game.getRemainingTeams() == 1){
            PublicViewController.getInstance().setScene("END_GAME");
        }
    }

    public void endTurn(){
        selectTile(game.getBoard().getCorner(getNextTeam().getColour()));
        outputTile();
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
        currentRobot.setRemainingMoves(currentRobot.getMaxMove());
        draw();
    }

    public void draw(){
        makeFoggyOut();

        if(game.getTeam(currentRobot.getColour()).getScout().isAlive()){
            clearAreaFog(game.getTeam(currentRobot.getColour()).getScout().getPosition(), game.getTeam(currentRobot.getColour()).getScout().getRange());
            game.getTeam(currentRobot.getColour()).getScout().getRobotImage().setVisible(true);
        }
        if(game.getTeam(currentRobot.getColour()).getSniper().isAlive()) {
            clearAreaFog(game.getTeam(currentRobot.getColour()).getSniper().getPosition(), game.getTeam(currentRobot.getColour()).getSniper().getRange());
            game.getTeam(currentRobot.getColour()).getSniper().getRobotImage().setVisible(true);
        }
        if(game.getTeam(currentRobot.getColour()).getTank().isAlive()) {
            clearAreaFog(game.getTeam(currentRobot.getColour()).getTank().getPosition(), game.getTeam(currentRobot.getColour()).getTank().getRange());
            game.getTeam(currentRobot.getColour()).getTank().getRobotImage().setVisible(true);
        }
    }

    public Team getNextTeam(){
        Team returnTeam = null;
        //who's turn is it currently.
        TeamColour[] colour = TeamColour.values();
        int index = 0;
        switch(currentRobot.getColour()){
            case RED:
                index = 0;
                break;
            case ORANGE:
                index = 1;
                break;
            case YELLOW:
                index = 2;
                break;
            case GREEN:
                index = 3;
                break;
            case BLUE:
                index = 4;
                break;
            case PURPLE:
                index = 5;
                break;
        }
        while(returnTeam == null) {
            Team t = game.getTeam(colour[++index % 6]);
            if (!t.isEnabled() || t.getNextRobot() == null) continue;
            returnTeam = t;
        }
        return returnTeam;
    }

    public void endMatch(){
        PublicViewController.getInstance().setScene("END_GAME");
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
