package app.display.common;

import app.gameengine.model.gameobjects.StaticGameObject;

/**
 * A static tile with an unchanging sprite.
 * <p>
 * A blank tile is intended to be used to display a single sprite, typically
 * across the background of the game. Although it can be included within a game
 * directly to display a sprite, it was created for use by the game engine for
 * rendering tiled backgrounds.
 * 
 * @see StaticGameObject
 * @see Background
 * @see SpriteLocation
 */
public class BlankTile extends StaticGameObject {

    /**
     * Constructs a blank tile with the given sprite sheet and location within it.
     * 
     * @param x                   the x location of the tile, in game units
     * @param y                   the y location of the tile, in game units
     * @param spriteSheetFilename the file name of the sprite sheet to use
     * @param spriteLocation      the location within the sprite sheet
     */
    public BlankTile(double x, double y, String spriteSheetFilename, SpriteLocation spriteLocation) {
        super(x, y);
        this.spriteSheetFilename = spriteSheetFilename;
        this.defaultSpriteLocation = spriteLocation;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
