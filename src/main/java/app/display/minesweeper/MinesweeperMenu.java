package app.display.minesweeper;

import app.Configuration;
import app.display.common.FontManager;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.display.common.ui.UIElement;
import app.gameengine.Level;
import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.statistics.GameStat;
import app.gameengine.statistics.Scoreboard;
import app.games.minesweeper.MinesweeperLevel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Pause menu for the Minesweeper game.
 * <p>
 * Intended to be displayed over the main body of the game when it is paused,
 * this menu has buttons for starting a new game at set difficulties, creating a
 * custom game, or viewing the scoreboard.
 * 
 * @see UIElement
 * @see MinesweeperGame
 * @see MinesweeperLevel
 * @see Scoreboard
 */
public class MinesweeperMenu extends UIElement {

    private static double FONT_SIZE_LARGE = 15 * Configuration.ZOOM;
    private static double FONT_SIZE_MEDIUM = 10 * Configuration.ZOOM;
    private static double FONT_SIZE_SMALL = 7 * Configuration.ZOOM;
    private static double BUTTON_SPACING = 3 * Configuration.ZOOM;
    private static double BUTTON_WIDTH = 70 * Configuration.ZOOM;
    private static double BUTTON_HEIGHT = 15 * Configuration.ZOOM;
    private static double SCOREBOARD_WIDTH = 100 * Configuration.ZOOM;
    private static double SCOREBOARD_HEIGHT = 15 * Configuration.ZOOM;
    private static double SCOREBOARD_PADDING = 2 * Configuration.ZOOM;

    private Node root;
    private StackPane pane;
    private MinesweeperGame game;
    private Rectangle background;
    private VBox diffSelect;
    private VBox customSelect;

    /**
     * Constructs a new menu for a game of minesweeper. This menu displays options
     * for starting new games and viewing the scoreboard.
     * 
     * @param game the game of Minesweeper
     */
    public MinesweeperMenu(MinesweeperGame game) {
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
        this.background.setFill(Color.GRAY);

        Text title = new Text("START NEW GAME");
        title.setFill(Color.BLACK);
        title.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_LARGE));

        BeveledButton beginner = new BeveledButton("Beginner", () -> game.changeLevel("beginner"), BUTTON_WIDTH,
                BUTTON_HEIGHT, FONT_SIZE_MEDIUM);
        BeveledButton intermediate = new BeveledButton("Intermediate", () -> game.changeLevel("intermediate"),
                BUTTON_WIDTH, BUTTON_HEIGHT,
                FONT_SIZE_MEDIUM);
        BeveledButton expert = new BeveledButton("Expert", () -> game.changeLevel("expert"), BUTTON_WIDTH,
                BUTTON_HEIGHT, FONT_SIZE_MEDIUM);
        BeveledButton custom = new BeveledButton("Custom", () -> {
            this.pane.getChildren().removeIf(a -> a instanceof VBox);
            this.pane.getChildren().add(customSelect);
        }, BUTTON_WIDTH, BUTTON_HEIGHT, FONT_SIZE_MEDIUM);
        BeveledButton scoreButton = new BeveledButton("Scoreboard", () -> {
            this.pane.getChildren().removeIf(a -> a instanceof VBox);
            this.pane.getChildren().add(getScoreboardMenu());
        }, BUTTON_WIDTH, BUTTON_HEIGHT, FONT_SIZE_MEDIUM);

        this.customSelect = getCustomMenu();

        this.diffSelect = new VBox(BUTTON_SPACING, title, beginner, intermediate, expert, custom, scoreButton);
        this.diffSelect.setAlignment(Pos.CENTER);

        this.pane = new StackPane(background, this.diffSelect);
        this.root = this.pane;
        StackPane.setAlignment(title, Pos.TOP_CENTER);
    }

    private String formatDuration(double duration) {
        int seconds = (int) duration;
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private VBox getScoreboardMenu() {
        Text text = new Text("SCOREBOARD");
        text.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_LARGE));

        VBox menu = new VBox(BUTTON_SPACING, text);
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
            Text noneText = new Text("No scoreboard available");
            noneText.setFill(Color.WHITE.deriveColor(0, 1, 0.15, 1));
            noneText.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_MEDIUM));
            menu.getChildren().add(noneText);
        } else {
            double availableHeight = this.background.getHeight() - FONT_SIZE_LARGE - BUTTON_HEIGHT - BUTTON_SPACING * 3;
            double entryHeight = SCOREBOARD_HEIGHT + BUTTON_SPACING;
            int maxEntries = (int) (availableHeight / entryHeight);

            this.game.getScoreboard().loadStats();
            int count = 0;
            LinkedListNode<GameStat> node = this.game.getScoreboard().getScoreList();
            for (; count < maxEntries && node != null; count++, node = node.getNext()) {
                GameStat stat = node.getValue();
                Text name = new Text(stat.getEntryName());
                name.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_MEDIUM));
                name.setFill(Color.LIMEGREEN);
                StackPane.setAlignment(name, Pos.CENTER_LEFT);

                Text time = new Text(formatDuration(stat.getPlaytime()));
                time.setFont(FontManager.getFont("digital-7/digital-7 (mono).ttf", FONT_SIZE_MEDIUM));
                time.setFill(Color.LIMEGREEN);
                StackPane.setAlignment(time, Pos.CENTER_RIGHT);

                StackPane row = new StackPane(name, time);
                row.setPadding(new Insets(0, SCOREBOARD_PADDING, 0, SCOREBOARD_PADDING));

                BeveledBorderPane entryPane = new BeveledBorderPaneBuilder(row).bottomRightColor(Color.GRAY.brighter())
                        .build();
                entryPane.setMaxWidth(SCOREBOARD_WIDTH);
                entryPane.setPrefWidth(SCOREBOARD_WIDTH);
                entryPane.setMaxHeight(SCOREBOARD_HEIGHT);
                entryPane.setPrefHeight(SCOREBOARD_HEIGHT);
                menu.getChildren().add(entryPane);
            }
            for (; count < maxEntries; count++) {
                Text name = new Text("---");
                name.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_MEDIUM));
                name.setFill(Color.DARKGRAY);
                StackPane.setAlignment(name, Pos.CENTER_LEFT);

                Text time = new Text("--:--");
                time.setFont(FontManager.getFont("digital-7/digital-7 (mono).ttf", FONT_SIZE_MEDIUM));
                time.setFill(Color.DARKGRAY);
                StackPane.setAlignment(time, Pos.CENTER_RIGHT);

                StackPane row = new StackPane(name, time);
                row.setPadding(new Insets(0, SCOREBOARD_PADDING, 0, SCOREBOARD_PADDING));

                BeveledBorderPane emptyPane = new BeveledBorderPaneBuilder(row).bottomRightColor(Color.GRAY.brighter())
                        .build();
                emptyPane.setMaxWidth(SCOREBOARD_WIDTH);
                emptyPane.setPrefWidth(SCOREBOARD_WIDTH);
                emptyPane.setMaxHeight(SCOREBOARD_HEIGHT);
                emptyPane.setPrefHeight(SCOREBOARD_HEIGHT);
                menu.getChildren().add(emptyPane);
            }
        }

        BeveledButton back = new BeveledButton("Back", () -> {
            this.pane.getChildren().removeIf(a -> a instanceof VBox);
            this.pane.getChildren().add(diffSelect);
        }, BUTTON_WIDTH, BUTTON_HEIGHT, FONT_SIZE_MEDIUM);
        menu.getChildren().add(back);
        return menu;
    }

    private VBox getCustomMenu() {
        Text text = new Text("CUSTOM LEVEL");
        text.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_LARGE));
        TextField widthField = new TextField();
        widthField.setPromptText("Width");
        widthField.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_SMALL));
        TextField heightField = new TextField();
        heightField.setPromptText("Height");
        heightField.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_SMALL));
        TextField bombsField = new TextField();
        bombsField.setPromptText("Bombs");
        bombsField.setFont(FontManager.getFont("digital-7/digital-7.ttf", FONT_SIZE_SMALL));

        widthField.setMaxWidth(BUTTON_WIDTH);
        heightField.setMaxWidth(BUTTON_WIDTH);
        bombsField.setMaxWidth(BUTTON_WIDTH);

        BeveledButton back = new BeveledButton("Back", () -> {
            this.pane.getChildren().removeIf(a -> a instanceof VBox);
            this.pane.getChildren().add(diffSelect);
        }, BUTTON_WIDTH, BUTTON_HEIGHT, FONT_SIZE_MEDIUM);

        BeveledButton start = new BeveledButton("Start", () -> {
            try {
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                int bombs = Integer.parseInt(bombsField.getText());
                if (width < 9 || height < 9 || bombs < 1 || bombs > width * height - 1) {
                    throw new NumberFormatException();
                }
                this.game.loadLevel(new MinesweeperLevel(game, width, height, bombs));
                this.game.unpause();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
        }, BUTTON_WIDTH, BUTTON_HEIGHT, FONT_SIZE_MEDIUM);

        VBox menu = new VBox(BUTTON_SPACING, text, back, widthField, heightField, bombsField, start);
        menu.setAlignment(Pos.CENTER);

        return menu;
    }

    @Override
    public Node getRenderable() {
        return this.root;
    }

    @Override
    public void update(double dt, Level level) {
        if (this.background != null) {
            this.background.setWidth(Configuration.SCALE_FACTOR * level.getViewWidth());
            this.background.setHeight(Configuration.SCALE_FACTOR * level.getViewHeight());
        }
    }

}
