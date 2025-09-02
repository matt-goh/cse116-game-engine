package app.display.minesweeper;

import app.Configuration;
import app.Settings;
import app.gameengine.Game;
import app.gameengine.model.physics.Vector2D;
import app.games.minesweeper.MinesweeperLevel;
import app.games.minesweeper.MinesweeperLevel.GameState;
import app.games.minesweeper.NotPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MinesweeperGame extends Game {

    private static double BORDER_SIZE = 2.5 * Configuration.ZOOM;
    private static Color BACKGROUND_COLOR = Color.WHITE.deriveColor(0, 1, 0.65, 1);
    private static Color BORDER_COLOR = Color.GRAY;
    private static Color TOP_LEFT_COLOR = Color.WHITE.deriveColor(0, 1, 0.25, 1);
    private static Color BOTTOM_RIGHT_COLOR = Color.WHITE.deriveColor(0, 1, 0.8, 1);

    private Group menuGroup = new Group();
    private Group gameGroup;
    private MinesweeperMenu menu;
    private Rectangle background = new Rectangle(0, 0, Color.TRANSPARENT);

    /**
     * Constructs a new game of Minesweeper.
     */
    public MinesweeperGame() {
        super();
        this.setPlayer(new NotPlayer(0, 0));
        this.setIconPath("minesweeper.png");
    }

    public GameState getGameState() {
        return this.getCurrentLevel().getState();
    }

    @Override
    public String getName() {
        return "Minesweeper";
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        this.menu.update(dt, getCurrentLevel());
        this.background.setWidth(Configuration.SCALE_FACTOR * this.getCurrentLevel().getViewWidth());
        this.background.setHeight(Configuration.SCALE_FACTOR * this.getCurrentLevel().getViewHeight());
    }

    @Override
    public void resetCurrentLevel() {
        this.getCurrentLevel().reset();
    }

    @Override
    public MinesweeperLevel getCurrentLevel() {
        return (MinesweeperLevel) super.getCurrentLevel();
    }

    @Override
    public void changeLevel(String name) {
        this.loadLevel(new MinesweeperLevel(this, name));
        this.unpause();
    }

    @Override
    public void init() {
        super.init();
        this.UI = new MinesweeperUI(this);
        this.menu = new MinesweeperMenu(this);
        this.changeLevel("trivial");
        this.pause();
    }

    @Override
    public void pause() {
        Settings.setPaused(true);
        this.menuGroup.getChildren().clear();
        this.menuGroup.getChildren().add(this.menu.getRenderable());
    }

    @Override
    public void unpause() {
        Settings.setPaused(false);
        this.menuGroup.getChildren().clear();
        this.menuGroup.getChildren().add(this.background);
    }

    @Override
    public Parent createRootNode(Group bgGroup, Group fgGroup) {
        this.gameGroup = fgGroup;
        StackPane main = new StackPane(bgGroup, fgGroup, menuGroup);
        StackPane beveledPane = new BeveledBorderPaneBuilder(main).backgroundColor(BACKGROUND_COLOR)
                .bottomRightColor(BOTTOM_RIGHT_COLOR).topLeftColor(TOP_LEFT_COLOR).bevelThickness(BORDER_SIZE).build();
        beveledPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Region separator = new Region();
        separator.setPrefHeight(BORDER_SIZE * 2);
        separator.setBorder(new Border(new BorderStroke(BORDER_COLOR, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(BORDER_SIZE), Insets.EMPTY)));

        VBox box = new VBox(this.getUICollection().getRenderableUI(), separator, beveledPane);
        box.setAlignment(Pos.TOP_CENTER);
        box.setBorder(new Border(new BorderStroke(BORDER_COLOR, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(BORDER_SIZE * 2), Insets.EMPTY)));
        StackPane beveledBox = new BeveledBorderPaneBuilder(box).backgroundColor(BORDER_COLOR)
                .bottomRightColor(TOP_LEFT_COLOR).topLeftColor(BOTTOM_RIGHT_COLOR).bevelThickness(BORDER_SIZE).build();

        return beveledBox;
    }

    @Override
    public double getWindowHeight() {
        return super.getWindowHeight() + this.getUICollection().getRenderableUI().getLayoutBounds().getHeight()
                + BORDER_SIZE * 10;
    }

    @Override
    public double getWindowWidth() {
        return super.getWindowWidth() + BORDER_SIZE * 8;
    }

    @Override
    public Vector2D screenSpaceToWorldSpace(Vector2D rootSceneLocation) {
        Point2D gameLocation = this.gameGroup.localToScene(0, 0);

        rootSceneLocation.setX(rootSceneLocation.getX() - gameLocation.getX());
        rootSceneLocation.setY(rootSceneLocation.getY() - gameLocation.getY());

        return Vector2D.div(rootSceneLocation, Configuration.SCALE_FACTOR);
    }

}
