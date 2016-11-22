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
        return this.setDamage(3)
                .setHealth(3)
                .setMoves(1)
                .setRange(1);
    }

    public RobotBuilder getScout(){
        return this.setDamage(1)
                .setHealth(1)
                .setMoves(3)
                .setRange(2);
    }

    public RobotBuilder getSniper(){
        return this.setDamage(2)
                .setHealth(2)
                .setMoves(2)
                .setRange(3);
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
