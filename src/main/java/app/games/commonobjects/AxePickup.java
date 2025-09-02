package app.games.commonobjects;

import app.gameengine.Game;
import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.Collectible;

public class AxePickup extends Collectible {

    public AxePickup(double x, double y, Game game) {
        super(x, y, game, "Axe");
        this.spriteSheetFilename = "MiniWorldSprites/Objects/Axe.png";
        this.defaultSpriteLocation = new SpriteLocation(0, 0);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
