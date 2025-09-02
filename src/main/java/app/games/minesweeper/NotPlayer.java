package app.games.minesweeper;

import java.util.HashMap;

import app.gameengine.model.gameobjects.Player;

public class NotPlayer extends Player {

    public NotPlayer(double x, double y) {
        super(x, y, 1);
        this.spriteSheetFilename = "minesweeper/notPlayer.png";
        this.animations = new HashMap<>();
    }

    @Override
    public void showHitbox() {

    }

}
