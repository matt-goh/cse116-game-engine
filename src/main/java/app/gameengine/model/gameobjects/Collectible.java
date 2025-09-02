package app.gameengine.model.gameobjects;

import app.gameengine.Game;

public abstract class Collectible extends StaticGameObject {

    public Collectible(double x, double y, Game game, String itemID) {
        super(x, y);
    }

	@Override
	public boolean isSolid() {
		return false;
	}
}
