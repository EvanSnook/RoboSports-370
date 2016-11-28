package model;

import model.enums.TeamColour;

public class Team {
    /**
     * the Scout {@link Robot} for this {@link Team}
     */
    private Robot scout;

    /**
     * the Sniper {@link Robot} for this {@link Team}
     */
    private Robot sniper;

    /**
     * the Tank {@link Robot} for this {@link Team}
     */
    private Robot tank;

    /**
     * the {@link Robot} that played on the last turn
     */
    private Robot lastRobot;

    /**
     * the {@link TeamColour} for this {@link Team}
     */
    private TeamColour colour;

    /**
     * if this {@link Team} is participating in the current game
     */
    private boolean enabled;

    /**
     * Creates a new {@link Team}
     *
     * @param colour  the {@link TeamColour} for this team
     * @param enabled if this {@link Team} will be participating in the current game
     */
    public Team(TeamColour colour, boolean enabled) {
        this.colour = colour;
        this.enabled = enabled;
    }

    /**
     * Return the {@link Robot} who is to play next
     *
     * @return the next {@link Robot} to play, or null if they are all dead
     */
    public Robot getNextRobot() {
        if (lastRobot == scout && sniper.isAlive()) {
            return sniper;
        } else if (lastRobot == scout && !sniper.isAlive() && tank.isAlive()) {
            return tank;
        } else if (lastRobot == scout && !sniper.isAlive() && !tank.isAlive() && scout.isAlive()) {
            return scout;
        } else if (lastRobot == sniper && tank.isAlive()) {
            return tank;
        } else if (lastRobot == sniper && !tank.isAlive() && scout.isAlive()) {
            return scout;
        } else if (lastRobot == sniper && !tank.isAlive() && !scout.isAlive() && sniper.isAlive()) {
            return sniper;
        } else if (lastRobot == tank && scout.isAlive()) {
            return scout;
        } else if (lastRobot == tank && !scout.isAlive() && sniper.isAlive()) {
            return sniper;
        } else if (lastRobot == tank && !scout.isAlive() && sniper.isAlive() && tank.isAlive()) {
            return tank;
        } else {
            return null;
        }
    }

    /**
     * Get the amount of {@link Robot}s that are still alive on this {@link Team}
     *
     * @return the count of alive robots
     */
    public int remainingRobots() {
        int robotCount = 0;

        if (scout.isAlive()) robotCount++;
        if (sniper.isAlive()) robotCount++;
        if (tank.isAlive()) robotCount++;

        return robotCount;
    }

    public Robot getScout() {
        return scout;
    }

    public void setScout(Robot scout) {
        this.scout = scout;
    }

    public Robot getSniper() {
        return sniper;
    }

    public void setSniper(Robot sniper) {
        this.sniper = sniper;
    }

    public Robot getTank() {
        return tank;
    }

    public void setTank(Robot tank) {
        this.tank = tank;
    }

    public void setLastRobot(Robot lastRobot) {
        this.lastRobot = lastRobot;
    }

    public TeamColour getColour() {
        return colour;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
