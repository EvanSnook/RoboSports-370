package model;

import java.util.Iterator;

import model.enums.BoardSize;

public class HexNodeIterator implements Iterator{
    private HexNode root;
    private final int outDirection;

    private HexNode currentNode;
    private int currentFacing;
    private int currentLayer;
    private int currentIndex;

    public static void main(String[] args) {
        Board b = new Board(BoardSize.SMALL);

        HexNodeIterator iterator = new HexNodeIterator(b.getRoot());

        while(iterator.hasNext()){
            HexNode current = iterator.next();
            System.out.println(current.toString());
        }
    }

    public HexNodeIterator(HexNode root, int startFacing){
        this.root = root;
        this.currentNode = root;
        this.currentLayer = 0;
        this.currentIndex = 0;
        this.outDirection = startFacing;
        this.currentFacing = startFacing + 2;
    }

    public HexNodeIterator(HexNode root) {
        this(root, 0);
    }

    @Override
    public HexNode next() {
        // return the root if we are on 0
        if (currentLayer == 0 && currentIndex == 0) {
            currentIndex++;
            return root;
        }
        // If we are on the last node of the layer
        if(currentIndex >= nodesOnLayer() - 1){
            if(currentLayer != 0)
                currentNode = currentNode.get(currentFacing);
            // Then move out to the next layer
            out();
        } else {
            currentNode = currentNode.get(currentFacing);
            incrementIndex();

            // If the cursor is on a corner turn
            if(currentIndex % currentLayer == 0){
                if(currentLayer == 1 || currentIndex != 0)
                    turnFacing();
            }

        }

        return currentNode;
    }

    @Override
    public boolean hasNext() {
        // If we're on the last node of the layer
        if(currentIndex == nodesOnLayer() - 1)
            // And there is no node outwards from this node
            if(currentNode.get(outDirection) == null)
                // There is no next node
                return false;

        return true;
    }

    /**
     * get the amount of nodes on this layer
     * @return the amount of nodes on this layer
     */
    private int nodesOnLayer(){
        if(currentLayer == 0)
            return 1;

        return currentLayer * 6;
    }

    /**
     * Move the cursor one unit away from the starting location.
     */
    private void out(){
        currentLayer++;
        currentIndex = 0;
        currentFacing = outDirection + 2;
        currentNode = currentNode.get(outDirection);
    }

    /**
     * Turn to the next direction, returns back to 0 after 5
     */
    private void turnFacing(){
        currentFacing = (currentFacing + 1) % 6;
    }

    /**
     * Get the next index, returns back to 0 after the last node on the current layer
     */
    private void incrementIndex(){
        currentIndex = (currentIndex + 1) % nodesOnLayer();
    }

    public int getCurrentLayer() {
        return currentLayer;
    }

    public HexNode getCurrentNode(){
        return this.currentNode;
    }
}
