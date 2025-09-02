package app.display.pacman;

import java.util.HashMap;
import java.util.Map;

import app.display.common.ui.UICollection;
import app.games.pacman.PacmanGame;
import app.games.pacman.PacmanLevel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * UI for Pacman.
 * <p>
 * Displays the number of lives and score for a level of Pacman.
 * 
 * @see UICollection
 * @see PacmanGame
 * @see PacmanLevel
 */
public class PacmanUI extends UICollection {

    public PacmanUI(PacmanGame game) {
        super(new HashMap<>(Map.of("lives", new LivesDisplay(game), "score", new ScoreDisplay(game))));
        StackPane.setAlignment(this.elements.get("lives").getRenderable(), Pos.CENTER_RIGHT);
        StackPane.setMargin(this.elements.get("lives").getRenderable(), new Insets(10));
        StackPane.setAlignment(this.elements.get("score").getRenderable(), Pos.CENTER_LEFT);
        StackPane.setMargin(this.elements.get("score").getRenderable(), new Insets(10));

        this.UIPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
