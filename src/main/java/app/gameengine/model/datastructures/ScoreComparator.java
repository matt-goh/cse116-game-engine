package app.gameengine.model.datastructures;
import app.gameengine.statistics.GameStat;

public class ScoreComparator implements Comparator<GameStat> {

    /**
     * Compares two GameStat objects based on their scores.
     *
     * @param a the first GameStat
     * @param b the second GameStat
     * @return true if a's score is greater than b's score, false otherwise
     */
    @Override
    public boolean compare(GameStat a, GameStat b) {
        return a.getScore() > b.getScore();
    }

}
