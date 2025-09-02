package app.display.minesweeper;

import app.Configuration;
import app.display.common.FontManager;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Button class wrapped in a {@link BeveledBorderPane}.
 * <p>
 * Intended for use in the Minesweeper UI, and not configurable, this serves as
 * a simple beveled button.
 * 
 * @see BeveledBorderPane
 * @see MinesweeperMenu
 */
public class BeveledButton extends StackPane {

    private final Text label;
    private final BeveledBorderPane bevel;
    private final Runnable action;

    private final Color baseColor = Color.web("#C0C0C0");
    private final Color hoverColor = Color.web("#E0E0E0");
    private final Color pressedColor = Color.web("#A0A0A0");

    private final Color bevelLight = Color.WHITE;
    private final Color bevelDark = Color.WHITE.deriveColor(0, 1, 0.25, 1);

    private final double bevelThickness = 1.5 * Configuration.ZOOM;

    /**
     * Constructs a beveled button with the given properties.
     * 
     * @param text     the text to display
     * @param action   the action to occur on click
     * @param width    the width of the button, in pixels
     * @param height   the height of the button, in pixels
     * @param fontSize the font size for the text
     */
    public BeveledButton(String text, Runnable action, double width, double height, double fontSize) {
        this.action = action;

        this.label = new Text(text);
        label.setFont(FontManager.getFont("digital-7/digital-7.ttf", fontSize));
        label.setFill(Color.BLACK);

        StackPane labelPane = new StackPane(label);
        labelPane.setPrefWidth(width);
        labelPane.setPrefHeight(height);
        labelPane.setAlignment(Pos.CENTER);

        this.bevel = new BeveledBorderPaneBuilder(labelPane).bevelThickness(bevelThickness).backgroundColor(baseColor)
                .topLeftColor(bevelLight).bottomRightColor(bevelDark).build();

        this.getChildren().add(bevel);
        this.bevel.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        setMouseHandlers();
    }

    @SuppressWarnings("unused")
    private void setMouseHandlers() {
        this.setOnMouseEntered(e -> {
            bevel.setBackgroundColor(hoverColor);
            bevel.setBevelColors(bevelLight, bevelDark);
        });
        this.setOnMouseExited(e -> {
            bevel.setBackgroundColor(baseColor);
            bevel.setBevelColors(bevelLight, bevelDark);
        });
        this.setOnMousePressed(e -> {
            bevel.setBackgroundColor(pressedColor);
            bevel.setBevelColors(bevelDark, bevelLight);
        });
        this.setOnMouseReleased(e -> {
            bevel.setBackgroundColor(hoverColor);
            bevel.setBevelColors(bevelLight, bevelDark);
            if (action != null) {
                action.run();
            }
        });
    }
}
