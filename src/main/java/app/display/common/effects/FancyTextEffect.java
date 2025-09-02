package app.display.common.effects;

import app.Configuration;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.gameengine.model.physics.Vector2D;
import app.games.commonobjects.InfoNode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Displays a temporary text message with a fancy border, that fades over time.
 * <p>
 * The effect shows a styled label at a specified offset for a set duration,
 * fading out as time progresses. Useful for notifications or status messages.
 * 
 * @see TimedEffect
 * @see InfoNode
 * @see SimpleTextEffect
 */
public class FancyTextEffect extends TimedEffect {

    private StackPane content;
    private Vector2D offset;

    /**
     * Constructs a new text effect with a fancy border, that fades over time.
     * 
     * @param offset   the x and y offset from the owner's location
     * @param duration the visual duration of the effect
     * @param message  the text to be displayed
     */
    public FancyTextEffect(Vector2D offset, double duration, String message) {
        super(null, duration);
        if (!JFXManager.isInitialized()) {
            this.node = new PlaceholderNode();
            return;
        }
        this.content = createFrame(message);
        this.node = this.content;
        this.offset = offset;
    }

    private static StackPane createFrame(String message) {
        Label label = new Label();
        label.setFont(new Font("Arial", Configuration.ZOOM * 5));
        label.setTextFill(Color.WHITE);
        label.setStyle(
                "-fx-background-color: black; -fx-padding: 10; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10");
        label.setWrapText(true);
        label.setMaxWidth(100 * Configuration.ZOOM);
        label.setText(message);

        StackPane content = new StackPane(label);
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 10; -fx-background-radius: 20");
        return content;
    }

    @Override
    public Node getFrame(Vector2D origin) {
        if (this.content == null) {
            return super.getFrame(origin);
        }
        double posX = (this.offset.getX() + origin.getX()) * Configuration.SCALE_FACTOR - this.content.getWidth() / 2;
        double posY = (this.offset.getY() + origin.getY()) * Configuration.SCALE_FACTOR - this.content.getHeight();

        this.content.setLayoutX(Math.max(posX, 0));
        this.content.setLayoutY(Math.max(posY, 0));

        double start = this.duration / 2;
        double progress = Math.min((this.elapsed - start) / (this.duration - start), 1);

        this.content.setOpacity(1 - progress);
        return this.content;
    }

}
