package app.games.commonobjects;

import app.display.common.SpriteLocation;
import app.gameengine.Game;
import app.gameengine.model.gameobjects.Collectible;

public class MagicPickup extends Collectible {


    public MagicPickup(double x, double y, Game game) {
        super(x, y, game, "Magic");
        this.spriteSheetFilename = "MiniWorldSprites/Objects/BallistaBolt.png";
        this.defaultSpriteLocation = new SpriteLocation(0, 0);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
