package app.games.mario;

import app.display.common.Background;
import app.display.common.sound.AudioManager;
import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.model.physics.PhysicsEngineWithGravity;
import app.gameengine.model.physics.Vector2D;
import app.games.platformerobjects.PlatformerLevel;

public class MarioLevel extends PlatformerLevel {

    public MarioLevel(Game game, int width, int height, String name) {
        super(game, new PhysicsEngineWithGravity(), width, height, name);
        this.keyboardControls = new MarioControls(this.game);
        this.background = new Background("mario/smb_background.png", 1.0);
    }

    @Override
    public int getViewWidth() {
        return Math.min(this.getHeight(), 16);
    }

    @Override
    public int getViewHeight() {
        return Math.min(this.getHeight(), 16);
    }

    @Override
    public void onStart() {
        AudioManager.playMusic("mario/GroundTheme.wav");
    }

    @Override
    public void update(double dt) {
        if (!isInBounds(this, this.getPlayer().getLocation())) {
            this.getPlayer().destroy();
        }
        super.update(dt);
    }

    private static boolean isInBounds(Level level, Vector2D v) {
        return v.getX() >= 0 && v.getX() < level.getWidth() && v.getY() < level.getHeight();
    }

}
