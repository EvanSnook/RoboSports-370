package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import model.enums.TeamColour;

public class Team {
    private Robot scout;
    private Robot sniper;
    private Robot tank;
    private Robot lastRobot;
    private TeamColour colour;
    private boolean enabled;

    public Team(TeamColour colour, boolean enabled){
        this.colour = colour;
        this.enabled = enabled;
    }

    public Robot getScout() {
        return scout;
    }

    public Robot getSniper() {
        return sniper;
    }

    public Robot getTank() {
        return tank;
    }

    public Robot getNextRobot() {
        // TODO Team.getNextRobot()
        throw new NotImplementedException();
    }

    public int remainingRobots(){
        int robotCount = 0;

        if(!scout.isAlive()) robotCount++;
        if(!sniper.isAlive()) robotCount++;
        if(!tank.isAlive()) robotCount++;

        return robotCount;
    }

    public TeamColour getColour() {
        return colour;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
