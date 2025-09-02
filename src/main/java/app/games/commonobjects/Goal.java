package app.games.commonobjects;

import app.gameengine.Game;
import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.gameobjects.StaticGameObject;

/**
 * A {@link StaticGameObject} that simply advances to the next level when
 * collision with the player occurs.
 */
public class Goal extends StaticGameObject {

    private Game game;

    public Goal(double x, double y, Game game) {
        super(x, y);
        this.game = game;
        this.spriteSheetFilename = "MiniWorldSprites/Buildings/Lime/LimeWorkshops.png";
        this.defaultSpriteLocation = new SpriteLocation(1, 1);
    }

    public Game getGame() {
        return this.game;
    }

    @Override
    public void collideWithDynamicObject(DynamicGameObject otherObject) {
        if (otherObject.isPlayer()) {
            this.game.markAdvanceLevel();
        }
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
