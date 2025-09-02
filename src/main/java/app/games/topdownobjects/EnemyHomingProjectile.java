package app.games.topdownobjects;

import app.Configuration;
import app.display.common.SpriteLocation;
import app.display.common.effects.PurpleExplosionEffect;
import app.display.common.sound.AudioManager;
import app.gameengine.Level;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.physics.Vector2D;
import app.games.commonobjects.Projectile;

public class EnemyHomingProjectile extends Projectile {

    private double homingRate;

    public EnemyHomingProjectile(double x, double y) {
        this(x, y, 30, 180);
    }

    private EnemyHomingProjectile(double x, double y, int damage, double homingRate) {
        super(x, y, damage);
        this.homingRate = homingRate;
        this.spriteSheetFilename = "MiniWorldSprites/Objects/FireballProjectile.png";
        this.defaultSpriteLocation = new SpriteLocation(3, 0);
        this.getHitbox().setDimensions(0.5, 0.5);
        this.getHitbox().setOffset(0.25, 0.25);
    }

    @Override
    public void onDestroy() {
        double offsetX = this.getSpriteWidth() / (Configuration.SPRITE_SIZE * 2.0);
        double offsetY = this.getSpriteHeight() / (Configuration.SPRITE_SIZE * 2.0);
        this.getEffects().add(new PurpleExplosionEffect(new Vector2D(offsetX, offsetY), 0.5));
        AudioManager.playSoundEffect("explosion.wav");
    }

    @Override
    public void collideWithDynamicObject(DynamicGameObject otherObject) {
        if (!otherObject.getObjectType().equals("Sorcerer")
                && !otherObject.getObjectType().equals("EnemyHomingProjectile")) {
            otherObject.takeDamage(this.getDamage());
            this.destroy();
        }
    }

    @Override
    public void update(double dt, Level level) {
        super.update(dt, level);
        Vector2D to = Vector2D.sub(level.getPlayer().getLocation().copy(), this.getLocation().copy());
        Vector2D vel = this.getVelocity().copy();
        double angle = Vector2D.signedAngleBetween(vel, to) * dt;
        double actualAngle = Math.clamp(angle * this.homingRate / 90, -this.homingRate * dt, this.homingRate * dt);

        Vector2D rotate = Vector2D.rotateBy(vel, actualAngle);
        this.setVelocity(rotate.getX(), rotate.getY());

        Vector2D orientation = Vector2D.normalize(rotate);
        this.setOrientation(orientation.getX(), orientation.getY());
    }

}
