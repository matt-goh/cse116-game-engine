package app.gameengine.model.datastructures;
import app.gameengine.statistics.GameStat;

public class LevelNameComparator implements Comparator<GameStat> {

    @Override
    public boolean compare(GameStat a, GameStat b) {
        return a.getEntryName().compareTo(b.getEntryName()) < 0;
    }

}
