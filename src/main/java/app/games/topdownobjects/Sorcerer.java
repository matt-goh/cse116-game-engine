package app.games.topdownobjects;

import java.util.HashMap;

import app.display.common.SpriteLocation;

public class Sorcerer extends Enemy {

    public Sorcerer(double x, double y, int maxHP, int strength) {
        super(x, y, maxHP, strength);
        this.spriteSheetFilename = "MiniWorldSprites/Characters/Soldiers/Ranged/PurpleRanged/MagePurple.png";
        this.defaultSpriteLocation = new SpriteLocation(1, 0);
        this.animations = new HashMap<>();
    }

    public Sorcerer(double x, double y) {
        this(x, y, 100, 30);
    }

}
