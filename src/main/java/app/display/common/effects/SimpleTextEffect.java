package app.display.common.effects;

import app.Configuration;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.gameengine.model.physics.Vector2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Displays a temporary text message.
 * <p>
 * The effect shows a simple label, configurable with font and color.
 * 
 * @see TimedEffect
 * @see FancyTextEffect
 */
public class SimpleTextEffect extends TimedEffect {

    /**
     * Constructs a text effect with the given message and duration. Uses the system
     * default font at a size of {@value Configuration#DEFAULT_TEXT_SIZE}, and the
     * default color of black.
     * 
     * @param message  the text to display
     * @param duration the duration, in seconds, to display
     */
    public SimpleTextEffect(String message, double duration) {
        this(message, duration, new Font(Configuration.DEFAULT_TEXT_SIZE), Color.BLACK);
    }

    /**
     * Constructs a text effect with the given message, duration and font. Uses the
     * default color of black.
     * 
     * @param message  the text to display
     * @param duration the duration, in seconds, to display
     * @param font     the font to use
     */
    public SimpleTextEffect(String message, double duration, Font font) {
        this(message, duration, font, Color.BLACK);
    }

    /**
     * Constructs a text effect with the given message, duration and color. Uses the
     * system default font at a size of {@value Configuration#DEFAULT_TEXT_SIZE}.
     * 
     * @param message  the text to display
     * @param duration the duration, in seconds, to display
     * @param color    the color of the text
     */
    public SimpleTextEffect(String message, double duration, Color color) {
        this(message, duration, new Font(Configuration.DEFAULT_TEXT_SIZE), color);
    }

    /**
     * Constructs a text effect with the given message, duration, font, and color.
     * 
     * @param message  the text to display
     * @param duration the duration, in seconds, to display
     * @param font     the font to use
     * @param color    the color of the text
     */
    public SimpleTextEffect(String message, double duration, Font font, Color color) {
        super(createFrame(message, font, color), duration);
    }

    private static Node createFrame(String message, Font font, Color color) {
        if (!JFXManager.isInitialized()) {
            return new PlaceholderNode();
        }
        Text text = new Text(message);
        text.setFont(font);
        text.setFill(color);
        return text;
    }

    @Override
    public Node getFrame(Vector2D origin) {
        Node node = super.getFrame(origin);
        node.setLayoutY(node.getLayoutY() + Configuration.SCALE_FACTOR);
        return node;
    }

}
