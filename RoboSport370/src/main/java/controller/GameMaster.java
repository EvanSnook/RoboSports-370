package controller;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.HexNode;
import model.HexNodeIterator;
import model.Robot;

// TODO GameMaster Class
public class GameMaster {
    private Game game;
    private HexNode selectedNode;
    private Robot currentRobot;
    private ForthInterpreter interpreter;

    public GameMaster(Game newGame){
        game = newGame;
        game.getGameTime().getPlayTimer().addActionListener( e-> {});
    }

    public void hexNodeClicked(MouseEvent mouseEvent) {
        /*
            Iterators through the board until it finds the selected Polygon that matches the event ID
            Outputs to stderr if the HexNode could not be found
         */
        HexNodeIterator iterator = new HexNodeIterator(game.getBoard().getRoot(),0);
        while( (iterator.getCurrentNode().getLabel().compareTo(((Node) mouseEvent.getSource()).getId())) != 0){
            if(iterator.hasNext()) {
                iterator.next();
            }
            else{
                System.err.println("Can't find selected HexNode.");
            }
        }
        selectedNode = iterator.getCurrentNode();
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
