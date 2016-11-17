package model;

import org.json.simple.JSONObject;

public class Stats {
    private int wins;
    private int matches;
    private int distanceTraveled;
    private int damageTaken;
    private int damageGiven;
    private int plays;
    private int kills;
    private int deaths;

    public Stats(){

    }

    public Stats(JSONObject json){
        // TODO Stats(JSONOBject)
        // Don't know what is JSON looks like
    }

    public int getWins() {
        return wins;
    }

    public int getMatches() {
        return matches;
    }

    public int getDistanceTraveled() {
        return distanceTraveled;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public int getDamageGiven() {
        return damageGiven;
    }

    public int getPlays() {
        return plays;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getLosses(){
        return matches - wins;
    }

    public void addMatch(boolean wonMatch){
        if(wonMatch)
            wins++;
        matches++;
    }

    public void addDistanceTraveled(){
        distanceTraveled++;
    }

    public void addDamageTaken(int damage){
        damageTaken += damage;
    }

    public void addDamageGiven(int damage){
        damageGiven += damage;
    }

    public void addPlay(){
        plays++;
    }

    public void addKill(){
        kills++;
    }

    public void addDeath(){
        deaths++;
    }

    public void mergeStats(Stats other){
        this.wins += other.getWins();
        this.matches += other.getMatches();
        this.distanceTraveled += other.getDistanceTraveled();
        this.damageTaken += other.getDamageTaken();
        this.damageGiven += other.getDamageGiven();
        this.plays += other.getPlays();
        this.kills += other.getKills();
        this.deaths += other.getDeaths();
    }
}
