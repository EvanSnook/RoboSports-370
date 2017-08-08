package model;

import controller.ViewController;

public class PublicViewController {
    private static ViewController instance;

    private PublicViewController() {
    }

    public static ViewController getInstance() {
        if (instance == null)
            System.err.println("No ViewController Set!");

        return instance;
    }

    public static void setInstance(ViewController instance) {
        PublicViewController.instance = instance;
    }
}
