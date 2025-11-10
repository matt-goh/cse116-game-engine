package app.games.commonobjects;

import app.display.common.SpriteLocation;
import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.model.gameobjects.Collectible;
import app.gameengine.utils.Timer;

public class MagicPickup extends Collectible {

    private Timer timer;

    public MagicPickup(double x, double y, Game game) {
        super(x, y, game, "Magic");
        this.spriteSheetFilename = "MiniWorldSprites/Objects/BallistaBolt.png";
        this.defaultSpriteLocation = new SpriteLocation(0, 0);
        this.timer = new Timer(0.25);
    }

    public Timer getTimer() {
        return timer;
    }

    @Override
    public void use(Level level) {
        if (timer.check()) {
            PlayerMagicProjectile projectile = new PlayerMagicProjectile(0, 0);
            level.getPlayer().fireProjectile(projectile, 10, level);
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
