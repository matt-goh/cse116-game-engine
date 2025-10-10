package app.games.commonobjects;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.gameobjects.StaticGameObject;
import app.gameengine.model.physics.Hitbox;
import app.gameengine.model.physics.PhysicsEngine;
import app.gameengine.model.physics.Vector2D;

/**
 * A {@code StaticGameObject} that prevents collision by moving any
 * {@code DynamicGameObject}s that collide with it.
 */
public class Wall extends StaticGameObject {

    public Wall(double x, double y) {
        super(x, y);
        this.spriteSheetFilename = "MiniWorldSprites/Ground/Cliff.png";
        this.defaultSpriteLocation = new SpriteLocation(3, 0);
    }

    @Override
    public void collideWithDynamicObject(DynamicGameObject otherObject) {
        PhysicsEngine physics = new PhysicsEngine();
        Hitbox wallHitbox = this.getHitbox();
        Hitbox otherHitbox = otherObject.getHitbox();

        if (!physics.detectCollision(wallHitbox, otherHitbox)) {
            return;
        }

        double wallRight = wallHitbox.getLocation().getX() + wallHitbox.getDimensions().getX();
        double wallBottom = wallHitbox.getLocation().getY() + wallHitbox.getDimensions().getY();
        double otherRight = otherHitbox.getLocation().getX() + otherHitbox.getDimensions().getX();
        double otherBottom = otherHitbox.getLocation().getY() + otherHitbox.getDimensions().getY();

        double xOverlap = Math.min(wallRight, otherRight) - Math.max(wallHitbox.getLocation().getX(), otherHitbox.getLocation().getX());
        double yOverlap = Math.min(wallBottom, otherBottom) - Math.max(wallHitbox.getLocation().getY(), otherHitbox.getLocation().getY());

        Vector2D otherLocation = otherObject.getLocation();

        if (xOverlap < yOverlap) {
            // Horizontal Resolution
            if (otherHitbox.getLocation().getX() + otherHitbox.getDimensions().getX() / 2 < wallHitbox.getLocation().getX() + wallHitbox.getDimensions().getX() / 2) {
                // Push left
                otherObject.setLocation(otherLocation.getX() - xOverlap, otherLocation.getY());
            } else {
                // Push right
                otherObject.setLocation(otherLocation.getX() + xOverlap, otherLocation.getY());
            }
            otherObject.getVelocity().setX(0);
        } else {
            // Vertical Resolution
            if (otherHitbox.getLocation().getY() + otherHitbox.getDimensions().getY() / 2 < wallHitbox.getLocation().getY() + wallHitbox.getDimensions().getY() / 2) {
                // Push up
                otherObject.setLocation(otherLocation.getX(), otherLocation.getY() - yOverlap);
            } else {
                // Push down
                otherObject.setLocation(otherLocation.getX(), otherLocation.getY() + yOverlap);
            }
            otherObject.getVelocity().setY(0);
        }
    }

}
