package app.display.minesweeper;

import app.Configuration;
import app.display.common.FontManager;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.display.common.ui.UIElement;
import app.gameengine.Level;
import app.games.minesweeper.MinesweeperLevel.GameState;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

/**
 * UIElement that displays the elapsed time in a game of Minesweeper.
 * <p>
 * Using a 7-segment font, displays the playtime in the game. Uses a
 * {@link BeveledBorderPane} to appear inset.
 * 
 * @see UIElement
 * @see BeveledBorderPane
 * @see MinesweeperGame
 */
public class SegmentedTimer extends UIElement {

    private static double FONT_SIZE = 20 * Configuration.ZOOM;
    private static double WIDTH_PADDING = 4 * Configuration.ZOOM;

    private MinesweeperGame game;
    private Label label;
    private StackPane pane;
    private Node root;

    /**
     * Constructs a new timer for a game of Minesweeper
     * 
     * @param game the game of Minesweeper
     */
    public SegmentedTimer(MinesweeperGame game) {
        this.game = game;
        if (!JFXManager.isInitialized()) {
            this.root = new PlaceholderNode();
            return;
        }
        this.label = new Label("00:00");

        this.label.setFont(FontManager.getFont("digital-7/digital-7 (mono).ttf", FONT_SIZE));
        this.label.setTextAlignment(TextAlignment.CENTER);
        this.label.setTextFill(Color.RED);

        Label bgLabel = new Label("88:88");
        bgLabel.setFont(FontManager.getFont("digital-7/digital-7 (mono).ttf", FONT_SIZE));
        bgLabel.setTextAlignment(TextAlignment.CENTER);
        bgLabel.setTextFill(Color.DARKRED.deriveColor(0, 1, 0.5, 1));

        Rectangle background = new Rectangle();
        background.setFill(Color.BLACK);
        background.widthProperty().bind(bgLabel.widthProperty().add(WIDTH_PADDING));
        background.heightProperty().bind(bgLabel.heightProperty());

        this.pane = new BeveledBorderPaneBuilder(new StackPane(background, bgLabel, this.label)).build();
        this.root = this.pane;
        this.pane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    @Override
    public Node getRenderable() {
        return this.root;
    }

    @Override
    public void update(double dt, Level level) {
        if (this.label == null) {
            return;
        } else if (this.game.getGameState() == GameState.WIN || this.game.getGameState() == GameState.LOSE) {
            return;
        }
        int seconds = (int) level.getPlaytime();
        int minutes = seconds / 60;
        seconds %= 60;
        this.label.setText(String.format("%02d:%02d", minutes, seconds));
    }

}
