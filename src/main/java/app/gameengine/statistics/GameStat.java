package app.gameengine.statistics;

/**
 * A simple data storage class for tracking statistics about a game.
 * 
 * @see Scoreboard
 */
public class GameStat {

    private String entryName;
    private double playTime;
    private double score;

    public GameStat(String name, double playtime, double score) {
        this.entryName = name;
        this.playTime = playtime;
        this.score = score;
    }

    public String getEntryName() {
        return entryName;
    }

    public double getPlaytime() {
        return playTime;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return String.format("%s,%.2f,%.2f", entryName, playTime, score);
    }

}
