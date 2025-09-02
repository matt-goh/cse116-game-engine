package app.games.minesweeper;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.StaticGameObject;

public class CoverTile extends StaticGameObject {

    private TileState state = TileState.COVER;

    public enum TileState {
        COVER, INVISIBLE, FLAGGED, FLAGGEDWRONG, QUESTION
    }

    public CoverTile(double x, double y) {
        super(x, y);
        this.spriteSheetFilename = "minesweeper/minesweeperColors.png";
        this.defaultSpriteLocation = new SpriteLocation(0, 1);
    }

    public TileState getTileState() {
        return this.state;
    }

    public boolean isFlagged() {
        return this.state == TileState.FLAGGED;
    }

    public void setState(TileState state) {
        this.state = state;
        switch (this.state) {
            case COVER:
                this.defaultSpriteLocation.setColumn(0);
                this.defaultSpriteLocation.setRow(1);
                break;
            case INVISIBLE:
                this.defaultSpriteLocation.setColumn(0);
                this.defaultSpriteLocation.setRow(0);
                break;
            case FLAGGED:
                this.defaultSpriteLocation.setColumn(1);
                this.defaultSpriteLocation.setRow(1);
                break;
            case FLAGGEDWRONG:
                this.defaultSpriteLocation.setColumn(3);
                this.defaultSpriteLocation.setRow(1);
                break;
            case QUESTION:
                this.defaultSpriteLocation.setColumn(2);
                this.defaultSpriteLocation.setRow(1);
                break;
        }
    }

}
