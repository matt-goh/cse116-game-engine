package app.games.platformerobjects;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.physics.Hitbox;
import app.games.commonobjects.Wall;

public class PlatformerWall extends Wall {

    public PlatformerWall(double x, double y) {
        super(x, y);
        this.spriteSheetFilename = "MiniWorldSprites/Ground/Cliff.png";
        this.defaultSpriteLocation = new SpriteLocation(4, 0);
    }

    @Override
    public void collideWithDynamicObject(DynamicGameObject other) {
        super.collideWithDynamicObject(other);

        Hitbox hitbox1 = this.getHitbox();
        Hitbox hitbox2 = other.getHitbox();

        double left = hitbox1.getLocation().getX();
        double right = hitbox1.getLocation().getX() + hitbox1.getDimensions().getX();
        double top = hitbox1.getLocation().getY();

        double otherLeft = hitbox2.getLocation().getX();
        double otherRight = hitbox2.getLocation().getX() + hitbox2.getDimensions().getX();
        double otherTop = hitbox2.getLocation().getY();

        boolean collideX = otherRight > left && otherLeft < right;
        if (otherTop < top && other.getVelocity().getY() > 0 && collideX) {
            // ground collision
            other.getVelocity().setY(0);
        } else if (otherTop > top && other.getVelocity().getY() < 0 && collideX) {
            // ceiling collision
            other.getVelocity().setY(0);
        }
    }

}
