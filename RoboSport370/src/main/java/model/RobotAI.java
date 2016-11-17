package model;

import org.json.simple.JSONObject;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.LinkedList;

public class RobotAI extends Robot{
    private Stats previousStats;
    private JSONObject script;
    private HashMap<String, String> userDefinedWords;
    private HashMap<String, String> userDefinedVariables;
    private LinkedList<String> mail;

    public RobotAI(){
        // TODO RobotAI Constructor
        this.userDefinedVariables = new HashMap<>();
        this.userDefinedVariables = new HashMap<>();
        this.mail = new LinkedList<>();
    }

    public void load(JSONObject script){
        // TODO RobotAI.load(JSONObject)
        // we don't know what his json looks like so we can't do this
        throw new NotImplementedException();
    }

    public String getLibTeam(){
        // TODO RobotAI.getLibTeam()
        // requires load
        throw new NotImplementedException();
    }

    public String getPlayCommand(){
        // TODO RobotAI.getPlayCommand()
        // requires load
        throw new NotImplementedException();
    }

    public String getInitCommand(){
        // TODO RobotAI.getInitCommand()
        // requires load
        throw new NotImplementedException();
    }

    public JSONObject toJSON(){
        // TODO RobotAI.toJSON()
        throw new NotImplementedException();
    }

    public HashMap<String, String> getUserDefinedWords() {
        return userDefinedWords;
    }

    public HashMap<String, String> getUserDefinedVariables() {
        return userDefinedVariables;
    }

    public LinkedList<String> getMail() {
        return mail;
    }

    public Stats getPreviousStats() {
        return previousStats;
    }
}
