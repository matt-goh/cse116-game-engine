package app.games.minesweeper;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.StaticGameObject;

public class NumberTile extends StaticGameObject {

    public NumberTile(double x, double y, int num) {
        super(x, y);
        this.spriteSheetFilename = "minesweeper/minesweeperColors.png";
        this.defaultSpriteLocation = new SpriteLocation(num, 0);
    }

}
