package model;

import org.json.simple.JSONObject;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import model.enums.RobotType;
import model.enums.TeamColour;

public class RobotBuilder {
    private Robot r;

    public RobotBuilder(boolean isAI){
        r = (isAI) ? new RobotAI() : new Robot();
    }

    public RobotBuilder getTank(){
        // TODO set Tank Defaults
        return this;
    }

    public RobotBuilder getScout(){
        // TODO set Scout Defaults
        return this;
    }

    public RobotBuilder getSniper(){
        // TODO set Sniper Defaults
        return this;
    }

    public RobotBuilder setHealth(int health){
        r.setHealth(health);
        return this;
    }

    public RobotBuilder setDamage(int damage){
        r.setDamage(damage);
        return this;
    }

    public RobotBuilder setRange(int range){
        r.setRange(range);
        return this;
    }

    public RobotBuilder setMoves(int moves){
        r.setMaxMove(moves);
        return this;
    }

    public RobotBuilder setType(RobotType type){
        r.setType(type);
        return this;
    }

    public RobotBuilder setPosition(HexNode position){
        r.setPosition(position);
        return this;
    }

    public RobotBuilder setName(String name){
        r.setName(name);
        return this;
    }

    public RobotBuilder setFacing(int facing){
        r.setFacing(facing);
        return this;
    }

    public RobotBuilder setTeamColour(TeamColour colour){
        r.setColour(colour);
        return this;
    }

    public Robot build(){
        return r;
    }

    public RobotAI build(JSONObject json){
        ((RobotAI) r).load(json);
        return ((RobotAI) r);
    }
}
