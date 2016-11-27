package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import model.Game;
import model.HexNode;
import model.HexNodeIterator;
import model.Robot;

// TODO GameMaster Class
public class GameMaster {
    @FXML
    public Pane gameContainer;

    private static Game game;
    private HexNode selectedNode;
    private Robot currentRobot;
    private ForthInterpreter interpreter;

    private final String FOG_COLOUR = "#DDD";
    private final String DEFAULT_COLOUR = "WHITE";
    private final String SELECTED_COLOUR = "#AAA";

    public GameMaster(){}

    public void setGame(Game game){
        GameMaster.game = game;
        game.getGameTime().getPlayTimer().addActionListener( e-> {});

        HexNodeIterator iterator = new HexNodeIterator(game.getBoard().getRoot());
        while(iterator.hasNext()){
            HexNode current = iterator.next();

            if(!current.canContainRobots()) break;

            current.setHexagon((Polygon) gameContainer.lookup("#" + current.getLabel()));
            current.getHexagon().setFill(Paint.valueOf(DEFAULT_COLOUR));
        }
    }

    public void hexNodeClicked(MouseEvent mouseEvent) {
        /*
            Iterators through the board until it finds the selected Polygon that matches the event ID
            Outputs to stderr if the HexNode could not be found
         */
        HexNodeIterator iterator = new HexNodeIterator(game.getBoard().getRoot());
        while( (iterator.getCurrentNode().getLabel().compareTo(((Node) mouseEvent.getSource()).getId())) != 0){
            if(iterator.hasNext()) {
                iterator.next();
            }
            else{
                System.err.println("Can't find selected HexNode.");
            }
        }

        selectTile(iterator.getCurrentNode());
    }

    public void selectTile(HexNode node){
        if(selectedNode != null)
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
}
