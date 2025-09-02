package app.games.topdownobjects;

import java.util.ArrayList;
import java.util.Arrays;

import app.display.common.SpriteLocation;

public class Minotaur extends Enemy {

    public Minotaur(double x, double y, int maxHP, int strength) {
        super(x, y, maxHP, strength);
        this.spriteSheetFilename = "MiniWorldSprites/Characters/Monsters/Orcs/Minotaur.png";
        this.defaultSpriteLocation = new SpriteLocation(0, 0);
    }

    public Minotaur(double x, double y) {
        this(x, y, 100, 30);
    }

    @Override
    public void initAnimations() {
        this.spriteSheetFilename = "MiniWorldSprites/Characters/Monsters/Demons/ArmouredRedDemon.png";
        this.animations.put("walk_down", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 0),
                new SpriteLocation(1, 0),
                new SpriteLocation(2, 0),
                new SpriteLocation(3, 0))));
        this.animations.put("walk_up", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 1),
                new SpriteLocation(1, 1),
                new SpriteLocation(2, 1),
                new SpriteLocation(3, 1))));
        this.animations.put("walk_right", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 2),
                new SpriteLocation(1, 2),
                new SpriteLocation(2, 2),
                new SpriteLocation(3, 2))));
        this.animations.put("walk_left", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 2, true, false),
                new SpriteLocation(1, 2, true, false),
                new SpriteLocation(2, 2, true, false),
                new SpriteLocation(3, 2, true, false))));
        this.animations.put("attack_down", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 3),
                new SpriteLocation(1, 3),
                new SpriteLocation(2, 3),
                new SpriteLocation(3, 3),
                new SpriteLocation(4, 3),
                new SpriteLocation(5, 3))));
        this.animations.put("attack_up", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 4),
                new SpriteLocation(1, 4),
                new SpriteLocation(2, 4),
                new SpriteLocation(3, 4),
                new SpriteLocation(4, 4),
                new SpriteLocation(5, 4))));
        this.animations.put("attack_right", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 5),
                new SpriteLocation(1, 5),
                new SpriteLocation(2, 5),
                new SpriteLocation(3, 5),
                new SpriteLocation(4, 5),
                new SpriteLocation(5, 5))));
        this.animations.put("attack_left", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 6),
                new SpriteLocation(1, 6),
                new SpriteLocation(2, 6),
                new SpriteLocation(3, 6),
                new SpriteLocation(4, 6),
                new SpriteLocation(5, 6))));
    }

}
