package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
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
import model.enums.RobotType;
import model.enums.TeamColour;

// TODO GameMaster Class
public class GameMaster {
    private final int SMALL_ROBOT_WIDTH = 60;
    private final int SMALL_ROBOT_HEIGHT = 50;
    private final int MEDIUM_ROBOT_WIDTH = 45;
    private final int MEDIUM_ROBOT_HEIGHT = 35;

    private final String FOG_COLOUR = "#DDD";
    private final String DEFAULT_COLOUR = "WHITE";
    private final String SELECTED_COLOUR = "#AAA";

    private static Game game;

    private HexNode selectedNode;
    private Robot currentRobot;
    private ForthInterpreter interpreter;

    @FXML
    public Pane gameContainer;

    public GameMaster() {}

    /**
     * The real constructor..
     * @param game
     */
    public void setGame(Game game) {
        GameMaster.game = game;

        // TODO set GameTime action listener
        game.getGameTime().getPlayTimer().addActionListener(e -> {
        });

        linkPolygonsToHexNodes();
        initRobots();
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
    }

    public void selectTile(HexNode node) {
        if (selectedNode != null)
            selectedNode.getHexagon().setFill(Paint.valueOf(DEFAULT_COLOUR));
        selectedNode = node;
        selectedNode.getHexagon().setFill(Paint.valueOf(SELECTED_COLOUR));
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
            ImageView scoutView =
                    (ImageView) gameContainer.lookup("#" + t.getScout().getRobotString());
            ImageView sniperView =
                    (ImageView) gameContainer.lookup("#" + t.getSniper().getRobotString());
            ImageView tankView =
                    (ImageView) gameContainer.lookup("#" + t.getTank().getRobotString());

            t.getScout().setRobotImage(scoutView);
            t.getSniper().setRobotImage(sniperView);
            t.getTank().setRobotImage(tankView);
        }
    }

    private void linkPolygonsToHexNodes(){
        HexNodeIterator iterator = new HexNodeIterator(game.getBoard().getRoot());
        while (iterator.hasNext()) {
            HexNode current = iterator.next();

            if (!current.canContainRobots()) break;

            current.setHexagon((Polygon) gameContainer.lookup("#" + current.getLabel()));
            current.getHexagon().setFill(Paint.valueOf(DEFAULT_COLOUR));
        }}
}
