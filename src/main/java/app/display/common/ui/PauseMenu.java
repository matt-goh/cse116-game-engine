package app.display.common.ui;

import app.Configuration;
import app.display.common.FontManager;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.statistics.GameStat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * A general purpose pause menu with a scoreboard display.
 * <p>
 * Basic pause screen with a semi-transparent background and the text "GAME
 * PAUSED". Also includes buttons for returning to the game and displaying the
 * scoreboard.
 * 
 * @see UIElement
 * @see UICollection
 */
public class PauseMenu extends UIElement {

    private static double FONT_SIZE_LARGE = Configuration.ZOOM * 20;
    private static double FONT_SIZE_MEDIUM = Configuration.ZOOM * 12;
    private static double BUTTON_SPACING = Configuration.ZOOM * 5;
    private static double BUTTON_WIDTH = 120 * Configuration.ZOOM;
    private static double BUTTON_HEIGHT = 15 * Configuration.ZOOM;
    private static double SCOREBOARD_HEIGHT = Configuration.ZOOM * 20;
    private static double SCOREBOARD_PADDING = Configuration.ZOOM * 3;

    private Node root;
    private StackPane pane;
    private Game game;
    private Rectangle background;
    private VBox mainMenu;
    private VBox scoreboardMenu;

    /**
     * Constructs a new pause menu. This is intended to be the default for most
     * games. Includes a scoreboard display.
     * 
     * @param game the game associated with this pause menu
     */
    @SuppressWarnings("unused")
    public PauseMenu(Game game) {
        if (!JFXManager.isInitialized()) {
            this.root = new PlaceholderNode();
            return;
        }
        this.game = game;
        double width = 0;
        double height = 0;
        if (game.getCurrentLevel() != null) {
            width = Configuration.SCALE_FACTOR * game.getCurrentLevel().getViewWidth();
            height = Configuration.SCALE_FACTOR * game.getCurrentLevel().getViewHeight();
        }
        this.background = new Rectangle(width, height);
        this.background.setFill(Color.BLACK.deriveColor(0, 1, 1, 0.5));

        Text title = new Text("GAME PAUSED");
        title.setFill(Color.RED);
        title.setFont(FontManager.getFont("Minecraft.ttf", FONT_SIZE_LARGE));

        Button resumeButton = new Button("Return to Game");
        resumeButton.setOnAction(e -> game.unpause());
        resumeButton.setFont(FontManager.getFont("Minecraft.ttf", FONT_SIZE_MEDIUM));
        resumeButton.setTextFill(Color.BLACK);
        resumeButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        Button scoreboardButton = new Button("Scoreboard");
        scoreboardButton.setOnAction(e -> {
            this.pane.getChildren().removeIf(a -> a instanceof VBox);
            this.pane.getChildren().add(scoreboardMenu);
        });
        scoreboardButton.setFont(FontManager.getFont("Minecraft.ttf", FONT_SIZE_MEDIUM));
        scoreboardButton.setTextFill(Color.BLACK);
        scoreboardButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        this.mainMenu = new VBox(BUTTON_SPACING, title, resumeButton, scoreboardButton);
        this.mainMenu.setAlignment(Pos.CENTER);

        this.scoreboardMenu = buildScoreboardMenu();

        this.pane = new StackPane(background, mainMenu);
        this.root = this.pane;
        StackPane.setAlignment(background, Pos.TOP_LEFT);
    }

    private String formatDuration(double duration) {
        int seconds = (int) duration;
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @SuppressWarnings("unused")
    private VBox buildScoreboardMenu() {
        Text sbTitle = new Text("SCOREBOARD");
        sbTitle.setFill(Color.RED);
        sbTitle.setFont(FontManager.getFont("Minecraft.ttf", FONT_SIZE_LARGE));

        VBox menu = new VBox(BUTTON_SPACING, sbTitle);
        menu.setAlignment(Pos.CENTER);

        boolean scoreboardExists = true;
        if (game.getScoreboard() == null) {
            scoreboardExists = false;
        } else {
            this.game.getScoreboard().loadStats();
            LinkedListNode<GameStat> node = this.game.getScoreboard().getScoreList();
            if (node == null) {
                scoreboardExists = false;
            }
        }
        if (!scoreboardExists) {
            Text none = new Text("No scoreboard available");
            none.setFill(Color.LIGHTGRAY);
            none.setFont(FontManager.getFont("Minecraft.ttf", FONT_SIZE_MEDIUM));
            menu.getChildren().add(none);
        } else {
            Text header = new Text(String.format("%-15s %5s %6s", "NAME", "TIME", "SCORE"));
            header.setFill(Color.YELLOW);
            header.setFont(FontManager.getFont("digital-7/digital-7 (mono).ttf", FONT_SIZE_MEDIUM));
            VBox.setMargin(header, new Insets(0, 0, 0, SCOREBOARD_PADDING));
            menu.getChildren().add(header);

            double availableHeight = this.background.getHeight() - FONT_SIZE_LARGE - BUTTON_SPACING - FONT_SIZE_MEDIUM;
            double entryHeight = SCOREBOARD_HEIGHT + BUTTON_SPACING;
            int maxEntries = (int) (availableHeight / entryHeight);

            int count = 0;
            LinkedListNode<GameStat> node = this.game.getScoreboard().getScoreList();
            for (; count < maxEntries && node != null; count++, node = node.getNext()) {
                GameStat stat = node.getValue();
                String line = String.format("%-15s %5s %6.0f", stat.getEntryName(), formatDuration(stat.getPlaytime()),
                        stat.getScore());
                Text entry = new Text(line);
                entry.setFill(Color.LIMEGREEN);
                entry.setFont(FontManager.getFont("digital-7/digital-7 (mono).ttf", FONT_SIZE_MEDIUM));
                VBox.setMargin(entry, new Insets(0, 0, 0, SCOREBOARD_PADDING));
                menu.getChildren().add(entry);
            }
            for (; count < maxEntries; count++) {
                Text empty = new Text(String.format("%-15s %5s %6s", "---", "--:--", "---"));
                empty.setFill(Color.LIMEGREEN);
                empty.setFont(FontManager.getFont("digital-7/digital-7 (mono).ttf", FONT_SIZE_MEDIUM));
                VBox.setMargin(empty, new Insets(0, 0, 0, SCOREBOARD_PADDING));
                menu.getChildren().add(empty);
            }
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            this.pane.getChildren().removeIf(a -> a instanceof VBox);
            this.pane.getChildren().add(mainMenu);
        });
        backButton.setFont(FontManager.getFont("Minecraft.ttf", FONT_SIZE_MEDIUM));
        backButton.setTextFill(Color.BLACK);
        backButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        menu.getChildren().add(backButton);
        return menu;
    }

    @Override
    public Node getRenderable() {
        return this.root;
    }

    @Override
    public void update(double dt, Level level) {
        if (background != null) {
            background.setWidth(Configuration.SCALE_FACTOR * level.getViewWidth());
            background.setHeight(Configuration.SCALE_FACTOR * level.getViewHeight());
        }
    }
}
