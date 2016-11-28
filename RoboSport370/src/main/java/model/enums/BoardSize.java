package model.enums;

public enum BoardSize {
    SMALL(5),
    MEDIUM(7);

    private int size;

    BoardSize(int size) {
        this.size = size;
    }

    public int getValue() {
        return this.size;
    }
}
