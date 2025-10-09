package app.games.minesweeper;

import java.util.ArrayList;
import java.util.HashMap;

import app.display.common.Background;
import app.display.common.controller.KeyboardControls;
import app.display.minesweeper.MinesweeperGame;
import app.gameengine.Level;
import app.gameengine.model.gameobjects.StaticGameObject;
import app.gameengine.model.physics.PhysicsEngine;
import app.gameengine.model.physics.Vector2D;
import app.gameengine.statistics.GameStat;
import app.gameengine.utils.GameUtils;
import app.gameengine.utils.Randomizer;
import app.games.minesweeper.CoverTile.TileState;

public class MinesweeperLevel extends Level {

    private HashMap<Vector2D, CoverTile> hiddenTiles = new HashMap<>();
    private HashMap<Vector2D, Bomb> bombs = new HashMap<>();
    private HashMap<Vector2D, Integer> counts = new HashMap<>();
    private int numBombs;
    private int flags;
    private GameState gameState = GameState.PLAYING;
    private double scoreMultiplier = 1;

    public MinesweeperLevel(MinesweeperGame game, String difficulty) {
        super(game, new PhysicsEngine(), 0, 0, difficulty);
        this.keyboardControls = new KeyboardControls(game);
        this.mouseControls = new MinesweeperControls(game);
        this.background = new Background("minesweeper/minesweeperColors.png", 0, 0);

        switch (difficulty.toLowerCase()) {
            case "expert":
                this.width = 30;
                this.height = 16;
                this.numBombs = 99;
                this.scoreMultiplier = 10_000;
                break;
            case "intermediate":
                this.width = 16;
                this.height = 16;
                this.numBombs = 40;
                this.scoreMultiplier = 1_000;
                break;
            case "trivial":
                this.width = 9;
                this.height = 9;
                this.numBombs = 3;
                this.scoreMultiplier = 1;
                break;
            case "beginner":
            default:
                this.width = 9;
                this.height = 9;
                this.numBombs = 10;
                this.scoreMultiplier = 100;
        }
        this.flags = numBombs;
        this.init();
    }

    public MinesweeperLevel(MinesweeperGame game, int width, int height, int bombs) {
        super(game, new PhysicsEngine(), width, height, "Custom");
        this.keyboardControls = new KeyboardControls(game);
        this.mouseControls = new MinesweeperControls(game);
        this.background = new Background("minesweeper/minesweeperColors.png", 0, 0);
        this.numBombs = bombs;
        this.flags = numBombs;
        init();
    }

    /**
     * Returns a list of vectors horizontally and diagonally adjacent to the input
     * vector.
     *
     * @param v the central vector
     * @return a list of adjacent vectors
     */
    public ArrayList<Vector2D> getAdjacentVectors(Vector2D v) {
        // create the list that we will return at the end
        ArrayList<Vector2D> surroundingVectors = new ArrayList<>();

        // check if vector input "v" is in the board. if not, return the empty list
        if (!GameUtils.isInBounds(this, v)) {
            return surroundingVectors;
        }

        // get xy coordinates of the center tile
        double xPoint = v.getX();
        double yPoint = v.getY();

        // use offsets to check all the surrounding tiles:
        // [-1,-1], [-1,0], [-1,1]
        // [0,-1], skip [0,0], [0,1]
        // [1,-1], [1,0], [1,1]
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {

                // skip the center tile
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                // add up the offsets
                double adjacentTileX = xPoint + xOffset;
                double adjacentTileY = yPoint + yOffset;
                Vector2D adjacentTile = new Vector2D(adjacentTileX, adjacentTileY);

                // final check
                if (GameUtils.isInBounds(this, adjacentTile)) {
                    surroundingVectors.add(adjacentTile);
                }
            }
        }

        return surroundingVectors;
    }

    public HashMap<Vector2D, CoverTile> getHiddenTiles() {
        return this.hiddenTiles;
    }

    public GameState getState() {
        return this.gameState;
    }

    public void setState(GameState state) {
        this.gameState = state;
        if (state == GameState.WIN) {
            this.game.getScoreboard().addScore(new GameStat(this.getName(), this.playtime, this.getScore()));
        }
    }

    public int getFlags() {
        return this.flags;
    }

    private void init() {
        for (int i = 0; i < this.numBombs; i++) {
            int x = Randomizer.randomInt(this.getWidth());
            int y = Randomizer.randomInt(this.getHeight());
            while (this.bombs.containsKey(new Vector2D(x, y))) {
                x = Randomizer.randomInt(this.getWidth());
                y = Randomizer.randomInt(this.getHeight());
            }
            this.bombs.put(new Vector2D(x, y), new Bomb(x, y));
            for (Vector2D loc : getAdjacentVectors(new Vector2D(x, y))) {
                this.counts.put(loc, this.counts.getOrDefault(loc, 0) + 1);
            }
        }
        this.counts.entrySet().removeIf(e -> this.bombs.keySet().contains(e.getKey()));
        super.getStaticObjects().addAll(this.bombs.values());
        this.counts
                .forEach((loc, count) -> super.getStaticObjects().add(new NumberTile(loc.getX(), loc.getY(), count)));

        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                this.hiddenTiles.put(new Vector2D(i, j), new CoverTile(i, j));
            }
        }
    }

    @Override
    public double getScore() {
        return this.scoreMultiplier / this.playtime;
    }

    @Override
    public void reset() {
        this.playtime = 0;
        super.getStaticObjects().forEach(StaticGameObject::destroy);
        super.getStaticObjects().clear();
        this.setState(GameState.PLAYING);
        this.bombs.clear();
        this.hiddenTiles.clear();
        this.counts.clear();
        this.flags = numBombs;
        this.init();
    }

    @Override
    public ArrayList<StaticGameObject> getStaticObjects() {
        ArrayList<StaticGameObject> objs = new ArrayList<>(super.getStaticObjects());
        objs.addAll(this.hiddenTiles.values());
        return objs;
    }

    private void uncoverTiles(Vector2D location) {
        if (this.hiddenTiles.get(location).isFlagged()) {
            this.flags++;
        }
        this.hiddenTiles.remove(location);
        if (!counts.containsKey(location)) {
            for (Vector2D neighbor : getAdjacentVectors(location)) {
                if (this.hiddenTiles.containsKey(neighbor)) {
                    this.uncoverTiles(neighbor);
                }
            }
        }
    }

    @Override
    public void handleLeftClick(Vector2D location) {
        if (!this.hiddenTiles.containsKey(location) || this.hiddenTiles.get(location).isFlagged()) {
            return;
        }
        // Lose
        if (bombs.containsKey(location)) {
            bombs.get(location).detonate();
            this.setState(GameState.LOSE);
            for (Vector2D bombLoc : this.bombs.keySet()) {
                if (this.hiddenTiles.containsKey(bombLoc) && !this.hiddenTiles.get(bombLoc).isFlagged()) {
                    this.hiddenTiles.remove(bombLoc);
                }
            }
            for (Vector2D tileLoc : this.hiddenTiles.keySet()) {
                if (this.hiddenTiles.get(tileLoc).isFlagged() && !this.bombs.containsKey(tileLoc)) {
                    this.hiddenTiles.get(tileLoc).setState(TileState.FLAGGEDWRONG);
                }
            }
            return;
        }
        this.uncoverTiles(location);
        // Win
        if (this.hiddenTiles.size() <= this.bombs.size()) {
            this.setState(GameState.WIN);
            this.flags = 0;
            for (CoverTile tile : this.hiddenTiles.values()) {
                if (!tile.isFlagged()) {
                    tile.setState(TileState.FLAGGED);
                }
            }
        }
    }

    @Override
    public void handleRightClick(Vector2D location) {
        if (this.hiddenTiles.containsKey(location)) {
            if (this.hiddenTiles.get(location).isFlagged()) {
                this.hiddenTiles.get(location).setState(TileState.QUESTION);
                this.flags += 1;
            } else if (this.hiddenTiles.get(location).getTileState() == TileState.QUESTION) {
                this.hiddenTiles.get(location).setState(TileState.COVER);
            } else if (this.flags > 0) {
                this.hiddenTiles.get(location).setState(TileState.FLAGGED);
                this.flags -= 1;
            } else {
                this.hiddenTiles.get(location).setState(TileState.QUESTION);
            }
        }
    }

    public enum GameState {
        WIN, LOSE, PLAYING, CLICK
    }

}
