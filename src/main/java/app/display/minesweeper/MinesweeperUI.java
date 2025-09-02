package app.display.minesweeper;

import java.util.HashMap;
import java.util.Map;

import app.Configuration;
import app.display.common.ui.UICollection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * UI that is preset with the elements necessary for Minesweeper.
 * <p>
 * Replaces the root node with a {@link BeveledBorderPane}, and fills with a
 * {@link FaceDisplay}, {@link SegmentedTimer}, and {@link SegmentedCounter}.
 * 
 * @see UICollection
 * @see BeveledBorderPane
 * @see MinesweeperGame
 * @see FaceDisplay
 * @see SegmentedTimer
 * @see SegmentedCounter
 */
public class MinesweeperUI extends UICollection {

    private static double INSETS_SIZE = 2 * Configuration.ZOOM;
    private static double BORDER_SIZE = 2.5 * Configuration.ZOOM;
    private static Color BACKGROUND_COLOR = Color.WHITE.deriveColor(0, 1, 0.65, 1);
    private static Color BOTTOM_RIGHT_COLOR = Color.WHITE.deriveColor(0, 1, 0.8, 1);

    /**
     * Constructs a new UI collection for a game of Minesweeper. Shows a
     * {@link SegmentedTimer}, a {@link SegmentedCounter}, and a
     * {@link FaceDisplay}.
     * 
     * @param game the game of Minesweeper
     */
    public MinesweeperUI(MinesweeperGame game) {
        super(new HashMap<>(Map.of("timer", new SegmentedTimer(game), "face", new FaceDisplay(game), "flag counter",
                new SegmentedCounter(game))));
        StackPane.setAlignment(this.elements.get("timer").getRenderable(), Pos.CENTER_RIGHT);
        StackPane.setMargin(this.elements.get("timer").getRenderable(), new Insets(INSETS_SIZE));
        StackPane.setAlignment(this.elements.get("face").getRenderable(), Pos.CENTER);
        StackPane.setAlignment(this.elements.get("flag counter").getRenderable(), Pos.CENTER_LEFT);
        StackPane.setMargin(this.elements.get("flag counter").getRenderable(), new Insets(INSETS_SIZE));

        this.UIPane = new BeveledBorderPaneBuilder(this.UIPane).backgroundColor(BACKGROUND_COLOR)
                .bottomRightColor(BOTTOM_RIGHT_COLOR).bevelThickness(BORDER_SIZE).build();
    }

}
