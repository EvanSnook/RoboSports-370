package model;

import controller.GameMaster;

public class PublicGameMaster {
    private static GameMaster instance;

    public static GameMaster getInstance() {
        if(instance == null)
            System.err.println("No GameMaster Set!");

        return instance;
    }

    public static void setInstance(GameMaster instance){
        PublicGameMaster.instance = instance;
    }

    private PublicGameMaster() {}
}
