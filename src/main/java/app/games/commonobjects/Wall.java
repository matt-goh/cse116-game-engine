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

        // Wall edges
        double wallLeft = wallHitbox.getLocation().getX();
        double wallRight = wallLeft + wallHitbox.getDimensions().getX();
        double wallTop = wallHitbox.getLocation().getY();
        double wallBottom = wallTop + wallHitbox.getDimensions().getY();

        // Other object's hitbox edges
        double otherLeft = otherHitbox.getLocation().getX();
        double otherRight = otherLeft + otherHitbox.getDimensions().getX();
        double otherTop = otherHitbox.getLocation().getY();
        double otherBottom = otherTop + otherHitbox.getDimensions().getY();

        // Calculate minimum push distances to clear the overlap from each side
        double pushRightDistance = wallRight - otherLeft;
        double pushLeftDistance = otherRight - wallLeft;
        double pushDownDistance = wallBottom - otherTop;
        double pushUpDistance = otherBottom - wallTop;

        // Find the minimum of the four possible push distances
        double minPush = pushRightDistance;

        if (pushLeftDistance < minPush) {
            minPush = pushLeftDistance;
        }
        if (pushDownDistance < minPush) {
            minPush = pushDownDistance;
        }
        if (pushUpDistance < minPush) {
            minPush = pushUpDistance;
        }

        Vector2D otherLocation = otherObject.getLocation();

        // Apply the push in the direction of minimum distance
        if (minPush == pushUpDistance) {
            // Push Up
            otherObject.setLocation(otherLocation.getX(), otherLocation.getY() - pushUpDistance);
            otherObject.getVelocity().setY(0);
        } else if (minPush == pushDownDistance) {
            // Push Down
            otherObject.setLocation(otherLocation.getX(), otherLocation.getY() + pushDownDistance);
            otherObject.getVelocity().setY(0);
        } else if (minPush == pushLeftDistance) {
            // Push Left
            otherObject.setLocation(otherLocation.getX() - pushLeftDistance, otherLocation.getY());
            otherObject.getVelocity().setX(0);
        } else { // minPush == pushRightDist
            // Push Right
            otherObject.setLocation(otherLocation.getX() + pushRightDistance, otherLocation.getY());
            otherObject.getVelocity().setX(0);
        }
    }

}
