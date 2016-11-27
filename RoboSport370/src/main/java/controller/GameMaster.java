package controller;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.HexNode;
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
        // TODO link the Polygon to the HexNode
        // set selectedNode
        System.out.println(((Node) mouseEvent.getSource()).getId());
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
