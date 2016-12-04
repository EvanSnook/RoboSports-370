package model;

import org.json.simple.JSONObject;

public class Stats {
    private long wins;
    private long losses;
    private long died;
    private long lived;
    private long executions;
    private long killed;
    private long moved;
    private long matches;
    private long absorbed;

    public Stats() {
    }

    public Stats(JSONObject json) {
        // TODO Stats(JSONOBject)
        // Don't know what is JSON looks like
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getLosses() {
        return losses;
    }

    public void setLosses(long losses) {
        this.losses = losses;
    }

    public long getDied() {
        return died;
    }

    public void setDied(long died) {
        this.died = died;
    }

    public long getLived() {
        return lived;
    }

    public void setLived(long lived) {
        this.lived = lived;
    }

    public long getExecutions() {
        return executions;
    }

    public void setExecutions(long executions) {
        this.executions = executions;
    }

    public long getKilled() {
        return killed;
    }

    public void setKilled(long killed) {
        this.killed = killed;
    }

    public long getMoved() {
        return moved;
    }

    public void setMoved(long moved) {
        this.moved = moved;
    }

    public long getMatches() {
        return matches;
    }

    public void setMatches(long matches) {
        this.matches = matches;
    }

    public long getAbsorbed() {
        return absorbed;
    }

    public void setAbsorbed(long absorbed) {
        this.absorbed = absorbed;
    }

    /**
     * Merge other stats with these stats
     *
     * @param other the other stats
     */
    public void mergeStats(Stats other) {
        this.absorbed += other.absorbed;
        this.died += other.died;
        this.executions += other.executions;
        this.killed += other.killed;
        this.lived += other.lived;
        this.losses += other.losses;
        this.matches += other.matches;
        this.moved += other.moved;
        this.wins += other.wins;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "absorbed=" + absorbed +
                ", died=" + died +
                ", executions=" + executions +
                ", killed=" + killed +
                ", lived=" + lived +
                ", losses=" + losses +
                ", matches=" + matches +
                ", moved=" + moved +
                ", wins=" + wins +
                '}';
    }
}
