package app.games.minesweeper;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.StaticGameObject;

public class Bomb extends StaticGameObject {

    public Bomb(double x, double y) {
        super(x, y);
        this.spriteSheetFilename = "minesweeper/minesweeperColors.png";
        this.defaultSpriteLocation = new SpriteLocation(4, 1);
    }

    public void detonate() {
        this.defaultSpriteLocation.setColumn(5);
    }

}
