package app.games.commonobjects;

import app.gameengine.Game;
import app.gameengine.Level;
import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.Collectible;
import app.gameengine.utils.Timer;

public class AxePickup extends Collectible {

    private Timer timer;

    public AxePickup(double x, double y, Game game) {
        super(x, y, game, "Axe");
        this.spriteSheetFilename = "MiniWorldSprites/Objects/Axe.png";
        this.defaultSpriteLocation = new SpriteLocation(0, 0);
        this.timer = new Timer(0.25);
    }

    public Timer getTimer() {
        return timer;
    }

    @Override
    public void use(Level level) {
        if (timer.check()) {
            PlayerAxeProjectile projectile = new PlayerAxeProjectile(0, 0);
            level.getPlayer().fireProjectile(projectile, 5, level);
        }
    }

    @Override
    public void update(double dt, Level level) {
        super.update(dt, level);
        timer.advance(dt);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
