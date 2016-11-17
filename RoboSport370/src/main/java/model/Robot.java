package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import model.enums.RobotType;
import model.enums.TeamColour;

public class Robot {
    private int health;
    private int maxHealth;
    private int damage;
    private int range;
    private int maxMove;
    private RobotType type;
    private HexNode position;
    private int remainingMoves;
    private String name;
    private Stats stats;
    private int facing;
    private TeamColour colour;

    public Robot(){}

    public RobotBuilder getBuilder(boolean isAI){
        return new RobotBuilder(isAI);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage(){
        return this.damage;
    }

    public int getRange() {
        return range;
    }

    public int getMaxMove() {
        return maxMove;
    }

    public RobotType getType() {
        return type;
    }

    public HexNode getPosition() {
        return position;
    }

    public int getRemainingMoves() {
        return remainingMoves;
    }

    public String getName() {
        return name;
    }

    public Stats getStats() {
        return stats;
    }

    public int getFacing() {
        return facing;
    }

    public TeamColour getColour() {
        return colour;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setMaxMove(int maxMove) {
        this.maxMove = maxMove;
    }

    public void setType(RobotType type) {
        this.type = type;
    }

    public void setPosition(HexNode position) {
        this.position = position;
    }

    public void setRemainingMoves(int remainingMoves) {
        this.remainingMoves = remainingMoves;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public void setColour(TeamColour colour) {
        this.colour = colour;
    }

    public void consumeMove(){
        remainingMoves--;
    }

    public boolean isAlive(){
        return health > 0;
    }

    public void takeDamage(int amount){
        this.health = Math.max(0, this.health - amount);
    }

    public void startPlay(){
        // TODO Robot.startPlay
        throw new NotImplementedException();
    }
}