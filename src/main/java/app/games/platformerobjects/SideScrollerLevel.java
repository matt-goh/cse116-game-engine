package app.games.platformerobjects;

import app.gameengine.Game;

public class SideScrollerLevel extends PlatformerLevel {

    public SideScrollerLevel(Game game, int width, int height, String name) {
        super(game, width, height, name);
    }

    @Override
    public int getViewWidth() {
        return 30;
    }

}
