package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.LinkedList;

public class RobotAI extends Robot implements Cloneable {
    private String name;
    private String team;
    private String code;
    private HashMap<String, String> userDefinedWords;
    private HashMap<String, String> userDefinedVariables;
    private LinkedList<String> mail;

    public RobotAI() {
        this.userDefinedWords = new HashMap<>();
        this.userDefinedVariables = new HashMap<>();
        this.mail = new LinkedList<>();
    }

    public void load(JSONObject json) {
        this.code = "";
        for (Object item : (JSONArray) json.get("code")) {
            this.code += item;
        }
    }

    public String getPlayCommand() {
        return userDefinedWords.get("play");
    }

    public String getCode() {
        return this.code;
    }

    public JSONObject toJSON() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return name + "(" + team + ")";
    }

    @Override
    public Object clone() {
        RobotAI clone = null;
        try {
            clone = (RobotAI) super.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return clone;
    }
}
