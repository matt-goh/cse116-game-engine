package app.gameengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.display.common.Background;
import app.display.minesweeper.MinesweeperGame;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.gameobjects.StaticGameObject;
import app.games.commonobjects.AxePickup;
import app.games.commonobjects.Goal;
import app.games.commonobjects.InfoNode;
import app.games.commonobjects.MagicPickup;
import app.games.commonobjects.PotionPickup;
import app.games.commonobjects.Wall;
import app.games.mario.*;
import app.games.topdownobjects.Demon;
import app.games.topdownobjects.TopDownLevel;
import app.games.topdownobjects.Tower;

/**
 * Static class for creating levels from formatted csv files.
 */
public class LevelParser {
    /**
     * Parse the csv file at the given location within the levels directory, and
     * return the level which that file represents.
     *
     * @param game the game the level will be part of
     * @param path the path within the levels directory to the level
     * @return the parsed level
     */
    public static Level parseLevel(Game game, String path) {
        // construct the complete file path
        String fullPath = "data/levels/" + path;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fullPath));

            // initialize the level variable
            Level level = null;

            // loop through each line from the file
            for (String line : lines) {
                String[] parts = line.split(",");
                String type = parts[0]; // the first part of the line determines the action

                // use a switch statement to handle different types of lines
                switch (type) {
                    case "TopDownLevel":
                        // "LevelType=TopDownLevel,LevelName,Width,Height"
                        String levelName = parts[1];
                        int width = Integer.parseInt(parts[2]);
                        int height = Integer.parseInt(parts[3]);
                        // create the level object
                        level = new TopDownLevel(game, width, height, levelName);
                        break;

                    case "MarioLevel":
                        // "MarioLevel,LevelName,Width,Height"
                        String marioLevelName = parts[1];
                        int marioWidth = Integer.parseInt(parts[2]);
                        int marioHeight = Integer.parseInt(parts[3]);
                        // create the MarioLevel object using the new constructor
                        level = new MarioLevel(game, marioWidth, marioHeight, marioLevelName);
                        break;

                    case "PlayerStartLocation":
                        // PlayerStartLocation,XLocation,YLocation
                        if (level != null) {
                            double x = Double.parseDouble(parts[1]);
                            double y = Double.parseDouble(parts[2]);
                            level.setPlayerStartLocation(x, y);
                        }
                        break;

                    case "BackgroundImage":
                    case "BackgroundTile":
                        if (level != null) {
                            // The readBackground method takes an ArrayList of Strings, and will return a Background object
                            // Use the Level.setBackground method to set the background of that level
                            ArrayList<String> partsList = new ArrayList<>(Arrays.asList(parts));
                            Background background = readBackground(partsList);
                            level.setBackground(background);
                        }
                        break;

                    case "StaticGameObject":
                        if (level != null) {
                            ArrayList<String> partsList = new ArrayList<>(Arrays.asList(parts));
                            StaticGameObject staticObject = readStaticObject(game, level, partsList);
                            // Note that if readDynamicObject or readStaticObject cannot parse the line correctly, they will return null.
                            // In this case, you should not add it to the level.
                            if (staticObject != null) {
                                level.getStaticObjects().add(staticObject);
                            }
                        }
                        break;

                    case "DynamicGameObject":
                        if (level != null) {
                            ArrayList<String> partsList = new ArrayList<>(Arrays.asList(parts));
                            DynamicGameObject dynamicObj = readDynamicObject(game, level, partsList);
                            // Note that if readDynamicObject or readStaticObject cannot parse the line correctly, they will return null.
                            // In this case, you should not add it to the level.
                            if (dynamicObj != null) {
                                level.getDynamicObjects().add(dynamicObj);
                            }
                        }
                        break;
                }
            }

            // return the configured level
            return level;

        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Creates and returns a {@code Background} object as defined by the input list
     * of properties. If the first item in {@code split} must be either
     * <i>BackgroundImage</i> or <i>BackgroundTile</i>.
     * <p>
     * If the line defines a <i>BackgroundImage</i>, the following entries must all
     * be strings specifying the filepaths of those backgrounds within the
     * background directory. If multiple images are specified, they will be layered
     * back to front with evenly spaced parallax ratios from 0.0 to 1.0.
     * <p>
     * If the line defines a <i>BackgroundTile</i>, the following entries must be a
     * string specifying the filepath of that tile sprite sheet within the sprites
     * directory and two ints for the column and row within that sprite sheet, in
     * that order.
     *
     * @param split the split line from the csv file describing the object
     * @return the background that is described by {@code split}
     */
    public static Background readBackground(ArrayList<String> split) {
        switch (split.get(0)) {
            case "BackgroundImage":
                return new Background(new ArrayList<>(split.subList(1, split.size())));
            case "BackgroundTile":
            default:
                int column = Integer.parseInt(split.get(2));
                int row = Integer.parseInt(split.get(3));
                return new Background(split.get(1), column, row);
        }
    }

    /**
     * Creates and returns a {@code DynamicGameObject} as defined by the input list
     * of properties. The format of the input {@code ArrayList} is:
     * <p>
     * "DynamicGameObject,SubType,x,y,..."
     * <p>
     * Where SubType is the name of the class, x and y are the location of the
     * object, and any following items are additional constructor parameters.
     *
     * @param game  the game this object will be a member of
     * @param level the level this object will be a member of
     * @param split the split line from the csv file describing the object
     * @return the object that is described by {@code split}
     */
    public static DynamicGameObject readDynamicObject(Game game, Level level, ArrayList<String> split) {
        double x = Double.parseDouble(split.get(2));
        double y = Double.parseDouble(split.get(3));
        switch (split.get(1)) {
            case "Demon":
                return new Demon(x, y, Integer.parseInt(split.get(4)), Integer.parseInt(split.get(5)));
            case "Tower":
                return new Tower(x, y);
            case "Goomba":
                return new Goomba(x,y);
            case "Koopa":
                return new Koopa(x,y);
            default:
                String line = String.join(",", split);
                System.out.println("** Dynamic object for line \"" + line + "\" could not be read **");
                return null;
        }
    }

    /**
     * Creates and returns a {@code StaticGameObject} as defined by the input list
     * of properties. The format of the input {@code ArrayList} is:
     * <p>
     * "StaticGameObject,SubType,x,y,..."
     * <p>
     * Where SubType is the name of the class, x and y are the location of the
     * object, and any following items are additional constructor parameters.
     *
     * @param game  the game this object will be a member of
     * @param level the level this object will be a member of
     * @param split the split line from the csv file describing the object
     * @return the object that is described by {@code split}
     */
    public static StaticGameObject readStaticObject(Game game, Level level, ArrayList<String> split) {
        String type = split.get(1);
        double x = Double.parseDouble(split.get(2));
        double y = Double.parseDouble(split.get(3));
        switch (split.get(1)) {
            case "Wall":
                return new Wall(x, y);
            case "Goal":
                return new Goal(x, y, game);
            case "InfoNode":
                return new InfoNode(x, y, split.get(4));
            case "Block":
            case "Bricks":
            case "Ground":
                return new Block(x, y, type);
            case "QuestionBlock":
                return new QuestionBlock(x,y);
            case "HiddenBlock":
                return new HiddenBlock(x,y);
            case "PipeEnd":
                return new PipeEnd(x,y);
            case "PipeStem":
                return new PipeStem(x,y);
            case "Flag":
                return new Flag(x, y, game);
            case "AxePickup":
                return new AxePickup(x, y, game);
            case "MagicPickup":
                return new MagicPickup(x, y, game);
            case "PotionPickup":
                int heal = Integer.parseInt(split.get(4));
                return new PotionPickup(x, y, heal, game);
            default:
                String line = String.join(",", split);
                System.out.println("** Static object for line \"" + line + "\" could not be read **");
                return null;
        }
    }

}
