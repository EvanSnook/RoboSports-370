package model;

import model.enums.BoardSize;
import model.enums.TeamColour;

public class Board {
    private HexNode root;

    /**
     * Construct a new board model of a given {@link BoardSize}
     *
     * @param boardSize
     *              the size of the board to be created
     */
    public Board(BoardSize boardSize){
        int size = boardSize.getValue();

        HexNode root = new HexNode();
        HexNode currentNode = root;

        // Loop for Layer
        for(int curSize=1; curSize <= size; curSize++){
            // Create the 0th node for this layer to the right of current node
            HexNode newLayerNode = new HexNode();
            newLayerNode.setLabel("L" + curSize + "N0");

            linkNode(currentNode, 0, newLayerNode);
            currentNode = currentNode.getR();

            int facing = 2;
            // Creating nodes for this layer
            for(int i=1; i<=(curSize*6)-1; i++) {
                HexNode newNode = new HexNode();
                newNode.setLabel("L" + curSize + "N" + i);

                // Create next node and link
                linkNode(currentNode, facing, newNode);
                currentNode = currentNode.get(facing);

                // If we reached a corner, turn right
                if(i%curSize == 0)
                    facing = (facing + 1) % 6;
            }
            currentNode = currentNode.getDR();
        }

        this.root = root;
    }

    /**
     * Recursively links two {@link HexNode}s so that all of the current {@link HexNode}'s
     * sides are properly linked with the sides of the other {@link HexNode}
     *
     * @param cur
     *              the current {@link HexNode}
     * @param side
     *              the side you would like to link to the other {@link HexNode}
     * @param other
     *              the other {@link HexNode}
     */
    private void linkNode(HexNode cur, int side, HexNode other){
        cur.set(side, other);
        other.set((side+3)%6, cur);

        switch (side){
            case 0:
                if(cur.getDR() != null)
                    linkNode(cur.getDR(), 5, cur.get(side));
                break;
            case 1:
                if(cur.getDL() != null)
                    linkNode(cur.getDL(), 0, cur.get(side));
                break;
            case 2:
                if(cur.getL() != null)
                    linkNode(cur.getL(), 1, cur.get(side));
                break;
            case 3:
                if(cur.getUL() != null)
                    linkNode(cur.getUL(), 2, cur.get(side));
                break;
            case 4:
                if(cur.getUR() != null)
                    linkNode(cur.getUR(), 3, cur.get(side));
                break;
            case 5:
                if(cur.getR() != null)
                    linkNode(cur.getR(), 4, cur.get(side));
                break;
        }
    }

    /**
     * Get the outmost {@link HexNode} from the root on the specified {@code side}
     *
     * @param side
     *              index of the side
     * @return the corner {@link HexNode}
     */
    private HexNode getCorner(int side){
        HexNode current = getRoot();

        while(current.get(side) != null){
            current = current.get(side);
        }
        return current;
    }

    /**
     * Get the corner for the specified {@link Team}
     *
     * @param colour
     *              the colour of the team
     * @return the {@link HexNode} for the {@link Team}
     */
    public HexNode getCorner(TeamColour colour){
        switch(colour){
            case RED:
                return getCorner(0);
            case ORANGE:
                return getCorner(1);
            case BLUE:
                return getCorner(2);
            case GREEN:
                return getCorner(3);
            case YELLOW:
                return getCorner(4);
            case PURPLE:
                return getCorner(5);
            default:
                return getCorner(0);
        }
    }

    public HexNode getRoot() {
        return root;
    }
}
