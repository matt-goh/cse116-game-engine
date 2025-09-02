package app.games.pacman;

import java.util.ArrayList;
import java.util.Arrays;

import app.display.common.SpriteLocation;

public class PowerPellet extends Pellet {

    public PowerPellet(double x, double y, PacmanGame game) {
        super(x, y, game);
        this.spriteSheetFilename = "pacman/pacmanSprites.png";
        this.defaultSpriteLocation = new SpriteLocation(1, 0);
    }

    @Override
    public void initAnimations() {
        this.animationDuration = 0.25;
        this.animations.put("default", new ArrayList<>(Arrays.asList(
                new SpriteLocation(1, 0),
                new SpriteLocation(1, 1))));
    }

}
