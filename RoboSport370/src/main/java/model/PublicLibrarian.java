package model;

public class PublicLibrarian {
    private static final Librarian instance;

    static {
        instance = new Librarian();
    }

    public static Librarian getInstance() {
        return instance;
    }
}
