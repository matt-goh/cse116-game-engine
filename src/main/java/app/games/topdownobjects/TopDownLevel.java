package app.games.topdownobjects;

import app.display.common.controller.TopDownControls;
import app.display.common.sound.AudioManager;
import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.model.physics.PhysicsEngine;

/**
 * A level played from a top-down perspective.
 * <p>
 * The simplest kind of level, with the basic physics and standard top-down
 * controls. Also some music.
 * 
 * @see Level
 * @see PhysicsEngine
 * @see TopDownControls
 */
public class TopDownLevel extends Level {

    public TopDownLevel(Game game, int width, int height, String name) {
        super(game, new PhysicsEngine(), width, height, name);
        this.keyboardControls = new TopDownControls(game);
    }

    @Override
    public void update(double dt) {
        this.keyboardControls.processInput(dt);
        super.update(dt);
    }

    @Override
    public void onStart() {
        AudioManager.playMusic("DungeonMusic.wav");
    }

}
