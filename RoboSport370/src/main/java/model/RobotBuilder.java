package model;

import model.enums.RobotType;
import model.enums.TeamColour;
import org.json.simple.JSONObject;

public class RobotBuilder {
    private Robot r;

    public RobotBuilder(boolean isAI) {
        r = (isAI) ? new RobotAI() : new Robot();
    }

    public RobotBuilder getTank() {
        return this.setDamage(3)
                .setHealth(3)
                .setMoves(1)
                .setRange(1);
    }

    public RobotBuilder getScout() {
        return this.setDamage(1)
                .setHealth(1)
                .setMoves(3)
                .setRange(2);
    }

    public RobotBuilder getSniper() {
        return this.setDamage(2)
                .setHealth(2)
                .setMoves(2)
                .setRange(3);
    }

    private RobotBuilder setHealth(int health) {
        r.setHealth(health);
        return this;
    }

    private RobotBuilder setDamage(int damage) {
        r.setDamage(damage);
        return this;
    }

    private RobotBuilder setRange(int range) {
        r.setRange(range);
        return this;
    }

    private RobotBuilder setMoves(int moves) {
        r.setMaxMove(moves);
        return this;
    }

    private RobotBuilder setType(RobotType type) {
        r.setType(type);
        return this;
    }

    public RobotBuilder setPosition(HexNode position) {
        r.setPosition(position);
        return this;
    }

    public RobotBuilder setName(String name) {
        r.setName(name);
        return this;
    }

    public RobotBuilder setFacing(int facing) {
        r.setFacing(facing);
        return this;
    }

    public RobotBuilder setTeamColour(TeamColour colour) {
        r.setColour(colour);
        return this;
    }

    public Robot build() {
        return r;
    }

    public RobotAI build(JSONObject json) {
        ((RobotAI) r).load(json);
        return ((RobotAI) r);
    }
}
