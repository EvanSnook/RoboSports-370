package model;

import java.util.Iterator;

public class HexNodeIterator implements Iterator{
    private HexNode root;
    private HexNode currentNode;
    private int currentLayer;
    private int currentIndex;

    public HexNodeIterator(HexNode root) {
        this.root = root;
        this.currentLayer = 0;
        this.currentIndex = 0;
    }

    public static void main(String[] args) {
        HexNodeIterator iterator = new HexNodeIterator(new HexNode());

        while(iterator.hasNext()){
            HexNode current = iterator.next();
            System.out.println(current.toDebugString());
        }
    }

    @Override
    public HexNode next() {
        // TODO HexNodeIterator.next()
        return null;
    }

    @Override
    public boolean hasNext() {
        if(currentIndex-1 == nodesOnLayer(currentLayer))
            if(currentNode.getR() != null)
                return true;
        return false;
    }

    public HexNode getCurrentNode(){
        return this.currentNode;
    }

    private int nodesOnLayer(int layer){
        return layer * 6;
    }
}
