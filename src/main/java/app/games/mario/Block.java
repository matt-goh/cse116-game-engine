package app.games.mario;

import app.display.common.SpriteLocation;
import app.games.platformerobjects.PlatformerWall;

public class Block extends PlatformerWall {

    public Block(double x, double y, String type) {
        super(x, y);
        this.spriteSheetFilename = "mario/smb_blocks.png";
        this.chooseSpriteLocation(type);
    }

    public Block(double x, double y) {
        this(x, y, "Ground");
    }

    private void chooseSpriteLocation(String type) {
        switch (type) {
            case "Block":
                this.defaultSpriteLocation = new SpriteLocation(5, 13);
                break;
            case "Bricks":
                this.defaultSpriteLocation = new SpriteLocation(17, 12);
                break;
            case "Ground":
                this.defaultSpriteLocation = new SpriteLocation(5, 12);
                break;
            default:
                this.spriteSheetFilename = "";
                this.defaultSpriteLocation = new SpriteLocation(0, 0);
        }
    }

}
