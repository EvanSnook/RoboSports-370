package model;

public class HexNode<E>{
    private HexNode<E> r;
    private HexNode<E> dr;
    private HexNode<E> dl;
    private HexNode<E> l;
    private HexNode<E> ul;
    private HexNode<E> ur;
    private E value;

    public HexNode(E value){
        this.value = value;
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

    private E getValue(){
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
                (ul == null) ? "xxxx" : ul.getValue(),
                (ur == null) ? "xxxx" : ur.getValue(),
                (l == null) ? "xxxx" : l.getValue(),
                (r == null) ? "xxxx" : r.getValue(),
                (dl == null) ? "xxxx" : dl.getValue(),
                (dr == null) ? "xxxx" : dr.getValue()
        );
    }
}
