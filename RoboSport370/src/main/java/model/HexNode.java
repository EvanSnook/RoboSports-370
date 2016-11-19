package model;

import java.util.ArrayList;

public class HexNode{
    private HexNode r;
    private HexNode dr;
    private HexNode dl;
    private HexNode l;
    private HexNode ul;
    private HexNode ur;
    private ArrayList<Robot> value;
    private String label;

    public HexNode(){
        this.value = new ArrayList<>();
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

    public HexNode get(int side){
        switch(side){
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

    public void set(int side, HexNode node){
        switch(side){
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

    public int getOccupiedSides(){
        int count = 0;
        for(int i=0; i<=5; i++)
            if(get(i) != null)
                count++;

        return count;
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

    private ArrayList<Robot> getValue(){
        return value;
    }

    private boolean isEmpty(){
        return value == null;
    }

    public String toDebugString() {
        return String.format(
                "\n" +
                        " Node %s\n" +
                        "-------------------------\n" +
                        "     %s      %s\n" +
                        "\n" +
                        "  %s            %s\n" +
                        "\n" +
                        "     %s     %s\n",
                value,
                (ul == null) ? "xxxx" : ul.getLabel(),
                (ur == null) ? "xxxx" : ur.getLabel(),
                (l == null) ? "xxxx" : l.getLabel(),
                (r == null) ? "xxxx" : r.getLabel(),
                (dl == null) ? "xxxx" : dl.getLabel(),
                (dr == null) ? "xxxx" : dr.getLabel()
        );
    }
}
