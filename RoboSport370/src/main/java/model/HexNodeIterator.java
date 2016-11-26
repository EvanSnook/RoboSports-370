package model;

import java.util.Iterator;

public class HexNodeIterator implements Iterator{
    private HexNode root;
    private final int outDirection;

    private HexNode currentNode;
    private int currentFacing;
    private int currentLayer;
    private int currentIndex;

    public static void main(String[] args) {
        HexNodeIterator iterator = new HexNodeIterator(new HexNode());

        while(iterator.hasNext()){
            HexNode current = iterator.next();
            System.out.println(current.toString());
        }
    }

    public HexNodeIterator(HexNode root) {
        this(root, 0);
    }

    public HexNodeIterator(HexNode root, int startFacing){
        this.root = root;
        this.currentNode = root;
        this.currentLayer = 0;
        this.currentIndex = 0;
        this.outDirection = startFacing;
        this.currentFacing = startFacing + 2;
    }

    @Override
    public HexNode next() {
        if(currentIndex > nodesOnLayer() - 1){
            out();
        } else {
            // TODO get the next ndoe on this layer
        }

        return currentNode;
    }

    @Override
    public boolean hasNext() {
        if(currentIndex <= nodesOnLayer() - 1)
            return true;

        if(currentIndex > nodesOnLayer() - 1)
            if(currentNode.getR() != null)
                return true;

        return false;
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
        currentNode = currentNode.get(outDirection);
    }

    public HexNode getCurrentNode(){
        return this.currentNode;
    }
}
