package app.games.topdownobjects;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.games.commonobjects.Projectile;

public class EnemyArrowProjectile extends Projectile {

    public EnemyArrowProjectile(double x, double y) {
        this(x, y, 50);
    }

    private EnemyArrowProjectile(double x, double y, int damage) {
        super(x, y, damage);
        this.spriteSheetFilename = "MiniWorldSprites/Objects/ArrowLong.png";
        this.defaultSpriteLocation = new SpriteLocation(0, 0);
        this.getHitbox().setDimensions(0.5, 0.5);
        this.getHitbox().setOffset(0.25, 0.25);
    }

    @Override
    public void collideWithDynamicObject(DynamicGameObject otherObject) {
        otherObject.takeDamage(this.getDamage());
        this.destroy();
    }

}
