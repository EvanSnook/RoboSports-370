package model;

import javafx.scene.image.ImageView;
import model.enums.RobotType;
import model.enums.TeamColour;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Robot {
    private int health;
    private int maxHealth;
    private int damage;
    private int range;
    private int maxMove;
    private RobotType type;
    private HexNode position;
    private int remainingMoves;
    private Stats stats;
    private int facing;
    private TeamColour colour;
    private ImageView robotImage;

    public Robot() {
    }

    public static RobotBuilder getBuilder(boolean isAI) {
        return new RobotBuilder(isAI);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getMaxMove() {
        return maxMove;
    }

    public void setMaxMove(int maxMove) {
        this.maxMove = maxMove;
    }

    public RobotType getType() {
        return type;
    }

    public void setType(RobotType type) {
        this.type = type;
    }

    public HexNode getPosition() {
        return position;
    }

    public void setPosition(HexNode position) {
        this.position = position;
    }

    public int getRemainingMoves() {
        return remainingMoves;
    }

    public void setRemainingMoves(int remainingMoves) {
        this.remainingMoves = remainingMoves;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public TeamColour getColour() {
        return colour;
    }

    public void setColour(TeamColour colour) {
        this.colour = colour;
    }

    public void setRobotImage(ImageView robotImage) {
        this.robotImage = robotImage;
    }

    public ImageView getRobotImage(){
        return robotImage;
    }

    public String getRobotString() {
        String imageString = "";

        imageString += colour.toString().toLowerCase();
        imageString += type.toString().charAt(0) + type.toString().toLowerCase().substring(1);

        return imageString;
    }

    public void consumeMove() {
        remainingMoves--;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
    }

    public void startPlay() {
        // TODO Robot.startPlay
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return "Robot{" +
                "health=" + health +
                ", maxHealth=" + maxHealth +
                ", damage=" + damage +
                ", range=" + range +
                ", maxMove=" + maxMove +
                ", type=" + type +
                ", position=" + position +
                ", remainingMoves=" + remainingMoves +
                ", stats=" + stats +
                ", facing=" + facing +
                ", colour=" + colour +
                '}';
    }
}