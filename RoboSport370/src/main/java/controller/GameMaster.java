package controller;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

// TODO GameMaster Class
public class GameMaster {

    public void hexNodeClicked(MouseEvent mouseEvent) {
        System.out.println(((Node) mouseEvent.getSource()).getId());
    }
}
