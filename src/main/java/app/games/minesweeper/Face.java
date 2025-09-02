package app.games.minesweeper;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.StaticGameObject;

public class Face extends StaticGameObject {

    public Face() {
        super(0, 0);
        this.spriteSheetFilename = "minesweeper/faces.png";
        this.defaultSpriteLocation = new SpriteLocation(0, 0);
    }

    @Override
    public int getSpriteWidth() {
        return 32;
    }

    @Override
    public int getSpriteHeight() {
        return 32;
    }

    @Override
    public int getSpriteTileWidth() {
        return 32;
    }

    @Override
    public int getSpriteTileHeight() {
        return 32;
    }

}
