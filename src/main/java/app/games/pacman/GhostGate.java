package app.games.pacman;

import app.gameengine.model.gameobjects.DynamicGameObject;

public class GhostGate extends PacmanWall {

    public GhostGate(double x, double y) {
        super(x, y, "Gate");
    }

    @Override
    public void collideWithDynamicObject(DynamicGameObject otherObject) {
        if (otherObject.isPlayer()) {
            super.collideWithDynamicObject(otherObject);
        } else if (otherObject instanceof Ghost ghost && ghost.getState().equals("Spawner")) {
            super.collideWithDynamicObject(otherObject);
        }
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
