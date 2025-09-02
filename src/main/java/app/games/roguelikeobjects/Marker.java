package app.games.roguelikeobjects;

import app.gameengine.model.gameobjects.StaticGameObject;

/**
 * Marks the location of an item spawn in a {@link RoguelikeGame}.
 * 
 * @see RoguelikeGame
 */
public class Marker extends StaticGameObject {

    private String markerID;

    public Marker(double x, double y, String markerID) {
        super(x, y);
        this.markerID = markerID;
    }

    public String getMarkerID() {
        return this.markerID;
    }
}
