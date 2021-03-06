package model;

import java.awt.event.ActionListener;

import javax.swing.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameTime {

    private int robotThinkTime;
    private Timer playTimer;
    private long startGameinMilliS;


    private int playerTimeLimitMilliS = 1_000;

    public GameTime(int think) {
        robotThinkTime = think;
        playTimer = new Timer(playerTimeLimitMilliS, null);
        startGameinMilliS = System.currentTimeMillis();
    }

    public int getRobotThinkTime() {
        return robotThinkTime;
    }

    public long getStartGameinMilliS() {
        return startGameinMilliS;
    }

    public void setStartGameinMilliS(int i) {
        startGameinMilliS = i;
    }

    public void setThinkTime(int i) {
        robotThinkTime = i;
    }

    public void resetPlayTimer() {
        playTimer.restart();
    }

    public void toggleTimer() {
        if (isPaused()) {
            //resume the timer
            playTimer.start();
        } else {
            //pause the timer
            playTimer.stop();
        }
    }

    public Boolean isPaused() {
        return !playTimer.isRunning();
    }

    private void pauseTime() {
        playTimer.stop();
    }

    private void resumeTime() {
        playTimer.start();
    }

    public Timer getPlayTimer() {
        return playTimer;
    }
}
