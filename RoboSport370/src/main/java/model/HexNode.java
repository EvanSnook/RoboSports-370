package model;

import javafx.scene.shape.Polygon;
import java.util.LinkedList;

public class HexNode {
    /**
     * The node to the right of this {@link HexNode}
     */
    private HexNode r;

    /**
     * The node to the bottom-right of this {@link HexNode}
     */
    private HexNode dr;

    /**
     * The node to the bottom-left of this {@link HexNode}
     */
    private HexNode dl;

    /**
     * The node to the left of this {@link HexNode}
     */
    private HexNode l;

    /**
     * The node to the top-left of this {@link HexNode}
     */
    private HexNode ul;

    /**
     * The node to the top-right of this {@link HexNode}
     */
    private HexNode ur;

    /**
     * The list of robots residing on this {@link HexNode}
     */
    private LinkedList<Robot> robots;

    /**
     * A reference to the {@link Polygon} on the game board
     */
    private Polygon hexagon;

    /**
     * A label to identify this {@link HexNode}
     */
    private String label;

    /**
     * Construct a new {@link HexNode} with the ability to hold {@link Robot}s
     */
    public HexNode() {
        this(true);
    }

    /**
     * Construct a new {@link HexNode} without the ability to hold {@link Robot}s
     * <p>
     * This constructor is used for {@link HexNode}s that reside outside of the visible game board.
     *
     * @param willHoldRobots if true, the {@link LinkedList} that contains the {@link Robot}s will be
     *                       instantiated to an empty {@link LinkedList}
     */
    public HexNode(boolean willHoldRobots) {
        if (willHoldRobots)
            this.robots = new LinkedList<>();
    }

    public boolean isFoggy(){
        return (hexagon.getFill().toString().equals("0xddddddff"));
    }

    public void addRobot(Robot r){
        robots.add(r);
        r.setPosition(this);
    }

    public void removeRobot(Robot r){
        robots.remove(r);
    }

    public String getLabel() {
        return this.label;
    }

    /**
     * Set a label for this {@link HexNode}
     *
     * @param label the desired label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get the {@link HexNode} on the specified {@code side}
     *
     * @param side an {@link Integer} denoting the side desired
     *             <p>{@code 0} - right
     *             <p>{@code 1} - down-right
     *             <p>{@code 2} - down-left
     *             <p>{@code 3} - left
     *             <p>{@code 4} - top-left
     *             <p>{@code 5} - top-right
     * @return the {@link HexNode} on the desired {@code side}, or {@code null}
     */
    public HexNode get(int side) {
        switch (side) {
            case 0:
                return getR();
            case 1:
                return getDR();
            case 2:
                return getDL();
            case 3:
                return getL();
            case 4:
                return getUL();
            case 5:
                return getUR();
            default:
                return null;
        }
    }

    /**
     * Set the {@link HexNode} on the specified {@code side}
     *
     * @param side an {@link Integer} denoting the side desired
     *             <p>{@code 0} - right
     *             <p>{@code 1} - down-right
     *             <p>{@code 2} - down-left
     *             <p>{@code 3} - left
     *             <p>{@code 4} - top-left
     *             <p>{@code 5} - top-right
     * @param node the {@link HexNode} to be added to the {@code side} of this {@link HexNode}
     */
    public void set(int side, HexNode node) {
        switch (side) {
            case 0:
                setR(node);
                break;
            case 1:
                setDR(node);
                break;
            case 2:
                setDL(node);
                break;
            case 3:
                setL(node);
                break;
            case 4:
                setUL(node);
                break;
            case 5:
                setUR(node);
                break;
            default:
                break;
        }
    }

    public HexNode getR() {
        return r;
    }

    public void setR(HexNode r) {
        this.r = r;
    }

    public HexNode getDR() {
        return dr;
    }

    public void setDR(HexNode dr) {
        this.dr = dr;
    }

    public HexNode getDL() {
        return dl;
    }

    public void setDL(HexNode dl) {
        this.dl = dl;
    }

    public HexNode getL() {
        return l;
    }

    public void setL(HexNode l) {
        this.l = l;
    }

    public HexNode getUL() {
        return ul;
    }

    public void setUL(HexNode ul) {
        this.ul = ul;
    }

    public HexNode getUR() {
        return ur;
    }

    public void setUR(HexNode ur) {
        this.ur = ur;
    }

    public Polygon getHexagon() {
        return hexagon;
    }

    public void setHexagon(Polygon hexagon) {
        this.hexagon = hexagon;
    }

    public LinkedList<Robot> getRobots() {
        return robots;
    }

    public boolean isEmpty() {
        if(!canContainRobots())
            return true;
        return robots.isEmpty();
    }

    /**
     * Whether or not this {@link HexNode} was created to contain {@link Robot}s
     *
     * @return true if it can contain robots
     */
    public boolean canContainRobots() {
        return (robots != null);
    }

    @Override
    public String toString() {
        return String.format(
                "\n" +
                        " Node %s (%s)\n" +
                        "-------------------------\n" +
                        "     %s      %s\n" +
                        "\n" +
                        "  %s            %s\n" +
                        "\n" +
                        "     %s     %s\n",
                getLabel(), String.valueOf(canContainRobots()),
                (ul == null) ? "xxxx" : ul.getLabel(),
                (ur == null) ? "xxxx" : ur.getLabel(),
                (l == null) ? "xxxx" : l.getLabel(),
                (r == null) ? "xxxx" : r.getLabel(),
                (dl == null) ? "xxxx" : dl.getLabel(),
                (dr == null) ? "xxxx" : dr.getLabel()
        );
    }
}
