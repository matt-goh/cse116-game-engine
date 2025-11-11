package app.gameengine.model.datastructures;
import app.gameengine.statistics.GameStat;

public class PlaytimeComparator implements Comparator<GameStat> {

    @Override
    public boolean compare(GameStat a, GameStat b) {
        return a.getPlaytime() < b.getPlaytime();
    }

}
