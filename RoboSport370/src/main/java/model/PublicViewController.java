package model;

import javax.swing.text.View;

import controller.ViewController;

public class PublicViewController {
    private static ViewController instance;

    public static ViewController getInstance() {
        if(instance == null)
            System.err.println("No ViewController Set!");

        return instance;
    }

    public static void setInstance(ViewController instance){
        PublicViewController.instance = instance;
    }

    private PublicViewController() {}
}
