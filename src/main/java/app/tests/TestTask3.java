package app.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import app.gameengine.Level;
import app.gameengine.LevelParser;
import app.gameengine.LinearGame;
import app.gameengine.model.ai.Decision;
import app.gameengine.model.ai.DecisionTree;
import app.gameengine.model.datastructures.BinaryTreeNode;
import app.gameengine.model.ai.pacman.Chase;
import app.gameengine.model.ai.pacman.Dead;
import app.gameengine.model.ai.pacman.Flee;
import app.gameengine.model.ai.pacman.Idle;
import app.gameengine.model.ai.pacman.IsActive;
import app.gameengine.model.ai.pacman.Scatter;
import app.gameengine.model.gameobjects.Player;
import app.gameengine.model.gameobjects.StaticGameObject;
import app.gameengine.model.physics.Vector2D;
import app.gameengine.utils.Randomizer;
import app.games.commonobjects.AxePickup;
import app.games.commonobjects.MagicPickup;
import app.games.commonobjects.PlayerAxeProjectile;
import app.games.commonobjects.PlayerMagicProjectile;
import app.games.commonobjects.PotionPickup;
import app.games.pacman.Ghost;
import app.games.pacman.PacmanGame;
import app.games.pacman.PacmanLevel;
import app.games.topdownobjects.Demon;
import app.games.topdownobjects.TopDownLevel;

public class TestTask3 {

    public static final double EPSILON = 1e-5;

    @Test
    public void testBaseDecision() {
        Demon d1 = new Demon(5, 10);
        Decision decision = new Decision(d1, "name") {
            @Override
            public boolean decide(double dt, Level level) {
                return false;
            }

            @Override
            public void doAction(double dt, Level level) {
            }
        };
        assertEquals("name", decision.getName());
        decision.setName("new name");
        assertEquals("new name", decision.getName());
        assertSame(d1, decision.getAgent());
    }

    @Test
    public void testChaseDecide() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        String[] colors = new String[]{"Red", "Pink", "Cyan", "Orange"};
        String[] states = new String[]{"Chase", "Frightened", "Scatter", "Dead", "Spawner"};

        ArrayList<String> expectedStates = new ArrayList<>(Arrays.asList("Chase"));

        for (String color : colors) {
            for (String state : states) {
                Ghost ghost = new Ghost(5, 5, game, color);
                ghost.setState(state);
                Decision dec = new Chase(ghost, game, "isChase");

                assertSame(ghost, dec.getAgent());
                String failString = String.format("Incorrect decision for color '%s' and state '%s'", color, state);
                assertEquals(failString, expectedStates.contains(state), dec.decide(1, level));
            }
        }
    }

    @Test
    public void testIsActive() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        String[] colors = new String[]{"Red", "Pink", "Cyan", "Orange"};
        String[] states = new String[]{"Chase", "Frightened", "Scatter", "Dead", "Spawner"};

        ArrayList<String> expectedStates = new ArrayList<>(Arrays.asList("Chase", "Scatter"));

        for (String color : colors) {
            for (String state : states) {
                Ghost ghost = new Ghost(5, 5, game, color);
                ghost.setState(state);
                Decision dec = new IsActive(ghost, "isActive");

                assertSame(ghost, dec.getAgent());
                String failString = String.format("Incorrect decision for color '%s' and state '%s'", color, state);
                assertEquals(failString, expectedStates.contains(state), dec.decide(1, level));
            }
        }
    }

    @Test
    public void testDeadDecide() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        String[] colors = new String[]{"Red", "Pink", "Cyan", "Orange"};
        String[] states = new String[]{"Chase", "Frightened", "Scatter", "Dead", "Spawner"};

        ArrayList<String> expectedStates = new ArrayList<>(Arrays.asList("Dead"));

        for (String color : colors) {
            for (String state : states) {
                Ghost ghost = new Ghost(5, 5, game, color);
                ghost.setState(state);
                Decision dec = new Dead(ghost, game, "isDead");

                assertSame(ghost, dec.getAgent());
                String failString = String.format("Incorrect decision for color '%s' and state '%s'", color, state);
                assertEquals(failString, expectedStates.contains(state), dec.decide(1, level));
            }
        }
    }

    @Test
    public void testFleeDecide() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        String[] colors = new String[]{"Red", "Pink", "Cyan", "Orange"};
        String[] states = new String[]{"Chase", "Frightened", "Scatter", "Dead", "Spawner"};

        ArrayList<String> expectedStates = new ArrayList<>(Arrays.asList("Frightened"));

        for (String color : colors) {
            for (String state : states) {
                Ghost ghost = new Ghost(5, 5, game, color);
                ghost.setState(state);
                Decision dec = new Flee(ghost, game, "isFrightened");

                assertSame(ghost, dec.getAgent());
                String failString = String.format("Incorrect decision for color '%s' and state '%s'", color, state);
                assertEquals(failString, expectedStates.contains(state), dec.decide(1, level));
            }
        }
    }

    @Test
    public void testChaseBasic() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        level.getPlayer().setLocation(level.getWidth() - 2, 1);
        Ghost ghost = new Ghost(1, 1, game, "Red");
        ghost.setOrientation(0, -1);

        Chase chase = new Chase(ghost, game, "chase");
        assertSame(ghost, chase.getAgent());

        // Can act, will change orientation
        chase.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Not allowed to act yet, but will still follow path
        chase.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Not allowed to act yet, will not change orientation
        level.getPlayer().setLocation(1, level.getHeight() - 2);
        chase.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Can now act, will change orientation and follow path
        ghost.setLocation(1, 1.8);
        chase.doAction(1, level);
        assertEquals(new Vector2D(0, 1), ghost.getOrientation());
    }

    @Test
    public void testFleeBasic() {
        Randomizer.setSeed(116);
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        level.getPlayer().setLocation(level.getWidth() - 2, 1);
        Ghost ghost = new Ghost(1, 1, game, "Red");
        ghost.setOrientation(0, -1);

        Flee flee = new Flee(ghost, game, "flee");
        assertSame(ghost, flee.getAgent());

        // Can act, will change orientation
        flee.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Not allowed to act yet, but will still follow path
        flee.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Not allowed to act yet, will not change orientation
        level.getPlayer().setLocation(1, level.getHeight() - 2);
        flee.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Can now act, will change orientation and follow path
        ghost.setLocation(1, 1.8);
        flee.doAction(1, level);
        assertEquals(new Vector2D(0, -1), ghost.getOrientation());
    }

    @Test
    public void testDeadBasic() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        level.getPlayer().setLocation(level.getWidth() - 2, 1);
        Ghost ghost = new Ghost(8, 5, game, "Red");
        ghost.setOrientation(0, -1);

        Dead goHome = new Dead(ghost, game, "go home");
        assertSame(ghost, goHome.getAgent());

        // Can act, will change orientation
        goHome.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Not allowed to act yet, but will still follow path
        goHome.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Not allowed to act yet, will not change orientation
        level.getPlayer().setLocation(1, level.getHeight() - 2);
        goHome.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Can now act, will change orientation and follow path
        ghost.setLocation(8.8, 5);
        goHome.doAction(1, level);
        assertEquals(new Vector2D(0, 1), ghost.getOrientation());
    }

    @Test
    public void testScatterBasic() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        level.getPlayer().setLocation(1, level.getHeight() - 2);
        Ghost ghost = new Ghost(1, 1, game, "Red");
        ghost.setOrientation(0, -1);

        Scatter scatter = new Scatter(ghost, game, "scatter");
        assertSame(ghost, scatter.getAgent());

        // Can act, will change orientation
        scatter.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Not allowed to act yet, but will still follow path
        scatter.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Not allowed to act yet, will not change orientation
        level.getPlayer().setLocation(1, level.getHeight() - 2);
        scatter.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());
        // Can now act, will change orientation and follow path
        ghost.setLocation(1, 1.8);
        scatter.doAction(1, level);
        assertEquals(new Vector2D(0, -1), ghost.getOrientation());
    }

    @Test
    public void testIdleWithVelocity() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        Ghost ghost = new Ghost(1, 1, game, "Red");
        ghost.setVelocity(10, 0);
        ghost.setOrientation(0, 1);

        // Ghost will do nothing but follow path
        Idle idle = new Idle(ghost, "idle");
        idle.doAction(1, level);
        assertEquals(new Vector2D(10, 0), ghost.getVelocity());
        assertEquals(new Vector2D(0, 1), ghost.getOrientation());

        ghost.setVelocity(0, -0.5);
        ghost.setOrientation(0, -1);
        idle.doAction(1, level);
        assertEquals(new Vector2D(0, -0.5), ghost.getVelocity());
        assertEquals(new Vector2D(0, -1), ghost.getOrientation());

        ghost.setVelocity(0.1, -0.5);
        ghost.setOrientation(-1, 0);
        idle.doAction(1, level);
        assertEquals(new Vector2D(0.1, -0.5), ghost.getVelocity());
        assertEquals(new Vector2D(-1, 0), ghost.getOrientation());
    }

    @Test
    public void testIdleNoVelocity() {
        PacmanGame game = new PacmanGame();
        game.init();
        PacmanLevel level = game.getCurrentLevel();
        Ghost ghost = new Ghost(1, 1, game, "Red");
        ghost.setVelocity(0, 0);
        ghost.setOrientation(0, 1);

        Idle idle = new Idle(ghost, "idle");
        idle.doAction(1, level);
        assertEquals(new Vector2D(0, -1), ghost.getOrientation());

        ghost.setVelocity(0, 0);
        idle.doAction(1, level);
        assertEquals(new Vector2D(0, 1), ghost.getOrientation());

        ghost.setVelocity(0, 0);
        ghost.setOrientation(-1, 0);
        idle.doAction(1, level);
        assertEquals(new Vector2D(1, 0), ghost.getOrientation());

        ghost.setVelocity(0, 0);
        idle.doAction(1, level);
        assertEquals(new Vector2D(-1, 0), ghost.getOrientation());
    }

    @Test
    public void testPickupCollisions() {
        LinearGame game = new LinearGame();
        MagicPickup magic = new MagicPickup(2.5, 2.8, game);
        AxePickup axe = new AxePickup(2.5, 2.8, game);
        PotionPickup potion = new PotionPickup(2.5, 2.8, 5, game);
        Player p1 = new Player(2.5, 2.8, 10);
        Player p2 = new Player(2.5, 2.8, 10);
        Player p3 = new Player(2.5, 2.8, 10);

        game.setPlayer(p1);
        magic.collideWithDynamicObject(p1);
        assertEquals("Magic", p1.getActiveItemID());
        assertTrue(magic.isDestroyed());

        game.setPlayer(p2);
        axe.collideWithDynamicObject(p2);
        assertEquals("Axe", p2.getActiveItemID());
        assertTrue(axe.isDestroyed());

        game.setPlayer(p3);
        potion.collideWithDynamicObject(p3);
        assertEquals("Health Potion", p3.getActiveItemID());
        assertTrue(potion.isDestroyed());
    }

    @Test
    public void testMagicPickup() {
        LinearGame game = new LinearGame();
        TopDownLevel level = new TopDownLevel(game, 10, 10, "level");
        MagicPickup magic = new MagicPickup(2.5, 2.8, game);
        assertEquals("Magic", magic.getItemID());
        assertEquals(2.5, magic.getLocation().getX(), EPSILON);
        assertEquals(2.8, magic.getLocation().getY(), EPSILON);

        magic.update(0.5, level);
        assertNotNull(magic.getTimer());
        assertEquals(0.5, magic.getTimer().getElapsedTime(), EPSILON);
    }

    @Test
    public void testMagicPickupUse() {
        LinearGame game = new LinearGame();
        TopDownLevel level = new TopDownLevel(game, 10, 10, "level");
        MagicPickup magic = new MagicPickup(2.5, 2.8, game);
        assertEquals(0, level.getDynamicObjects().size());

        magic.update(10, level);
        magic.use(level);
        assertEquals(1, level.getDynamicObjects().size());
        assertEquals(PlayerMagicProjectile.class, level.getDynamicObjects().get(0).getClass());
        assertEquals(10, level.getDynamicObjects().get(0).getVelocity().magnitude(), EPSILON);
    }

    @Test
    public void testAxePickup() {
        LinearGame game = new LinearGame();
        TopDownLevel level = new TopDownLevel(game, 10, 10, "level");
        AxePickup axe = new AxePickup(2.5, 2.8, game);
        assertEquals("Axe", axe.getItemID());
        assertEquals(2.5, axe.getLocation().getX(), EPSILON);
        assertEquals(2.8, axe.getLocation().getY(), EPSILON);

        axe.update(0.5, level);
        assertNotNull(axe.getTimer());
        assertEquals(0.5, axe.getTimer().getElapsedTime(), EPSILON);
    }

    @Test
    public void testAxePickupUse() {
        LinearGame game = new LinearGame();
        TopDownLevel level = new TopDownLevel(game, 10, 10, "level");
        AxePickup axe = new AxePickup(2.5, 2.8, game);
        assertEquals(0, level.getDynamicObjects().size());

        axe.update(10, level);
        axe.use(level);
        assertEquals(1, level.getDynamicObjects().size());
        assertEquals(PlayerAxeProjectile.class, level.getDynamicObjects().get(0).getClass());
        assertEquals(5, level.getDynamicObjects().get(0).getVelocity().magnitude(), EPSILON);
    }

    @Test
    public void testPotionPickup() {
        LinearGame game = new LinearGame();
        PotionPickup potion = new PotionPickup(2.5, 2.8, 5, game);
        assertEquals("Health Potion", potion.getItemID());
        assertEquals(2.5, potion.getLocation().getX(), EPSILON);
        assertEquals(2.8, potion.getLocation().getY(), EPSILON);
        assertEquals(5, potion.getHealAmount());
    }

    @Test
    public void testPotionPickupUse() {
        LinearGame game = new LinearGame();
        TopDownLevel level = new TopDownLevel(game, 10, 10, "level");
        PotionPickup potion = new PotionPickup(2.5, 2.8, 5, game);
        Player p = game.getPlayer();
        p.addInventoryItem(potion);
        p.setHP(2);
        assertEquals(0, level.getDynamicObjects().size());
        assertEquals("Health Potion", p.getActiveItemID());

        potion.use(level);
        assertEquals(0, level.getDynamicObjects().size());
        assertEquals(7, p.getHP());
        assertEquals("No item equipped", p.getActiveItemID());
    }

    @Test
    public void testReadStaticObject() {
        LinearGame game = new LinearGame();
        TopDownLevel level = new TopDownLevel(game, 100, 100, "test level");
        StaticGameObject object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "AxePickup", "25", "2")));
        assertTrue(object instanceof AxePickup);
        assertEquals(new Vector2D(25, 2), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "MagicPickup", "12", "8")));
        assertTrue(object instanceof MagicPickup);
        assertEquals(new Vector2D(12, 8), object.getLocation());

        object = LevelParser.readStaticObject(game, level,
                new ArrayList<>(Arrays.asList("StaticGameObject", "PotionPickup", "10", "5", "12")));
        assertTrue(object instanceof PotionPickup);
        assertEquals(new Vector2D(10, 5), object.getLocation());
        assertEquals(12, ((PotionPickup) object).getHealAmount());
    }

    @Test
    public void testDecisionTreeTraverseReturnsDecision() {
        LinearGame game = new LinearGame();
        TopDownLevel level = new TopDownLevel(game, 10, 10, "test");
        Demon agent = new Demon(0, 0);

        DecisionTree tree = new DecisionTree(null);
        Decision result = tree.traverse(null, 1.0, level);
        assertEquals(null, result);

        TestDecision leaf = new TestDecision(agent, "leaf", false);
        BinaryTreeNode<Decision> singleNode = new BinaryTreeNode<>(leaf, null, null);
        result = tree.traverse(singleNode, 1.0, level);
        assertSame(leaf, result);

        TestDecision root = new TestDecision(agent, "root", false);
        TestDecision left = new TestDecision(agent, "left", false);
        TestDecision right = new TestDecision(agent, "right", false);
        BinaryTreeNode<Decision> leftNode = new BinaryTreeNode<>(left, null, null);
        BinaryTreeNode<Decision> rightNode = new BinaryTreeNode<>(right, null, null);
        BinaryTreeNode<Decision> rootNode = new BinaryTreeNode<>(root, leftNode, rightNode);
        result = tree.traverse(rootNode, 1.0, level);
        assertSame(left, result);

        TestDecision root2 = new TestDecision(agent, "root2", true);
        BinaryTreeNode<Decision> rootNode2 = new BinaryTreeNode<>(root2, leftNode, rightNode);
        result = tree.traverse(rootNode2, 1.0, level);
        assertSame(right, result);
    }

    @Test
    public void testDecisionTreeTraverseCallsDoAction() {
        LinearGame game = new LinearGame();
        TopDownLevel level = new TopDownLevel(game, 10, 10, "test");
        Demon agent = new Demon(0, 0);

        TestDecision leaf = new TestDecision(agent, "leaf", false);
        BinaryTreeNode<Decision> singleNode = new BinaryTreeNode<>(leaf, null, null);
        DecisionTree tree = new DecisionTree(singleNode);
        tree.traverse(1.0, level);
        assertTrue(leaf.isUsed());

        TestDecision root = new TestDecision(agent, "root", false);
        TestDecision left = new TestDecision(agent, "left", false);
        TestDecision right = new TestDecision(agent, "right", false);
        BinaryTreeNode<Decision> leftNode = new BinaryTreeNode<>(left, null, null);
        BinaryTreeNode<Decision> rightNode = new BinaryTreeNode<>(right, null, null);
        BinaryTreeNode<Decision> rootNode = new BinaryTreeNode<>(root, leftNode, rightNode);
        tree = new DecisionTree(rootNode);
        tree.traverse(1.0, level);
        assertTrue(!root.isUsed());
        assertTrue(left.isUsed());
        assertTrue(!right.isUsed());

        TestDecision root2 = new TestDecision(agent, "root2", true);
        TestDecision left2 = new TestDecision(agent, "left2", false);
        TestDecision right2 = new TestDecision(agent, "right2", false);
        BinaryTreeNode<Decision> leftNode2 = new BinaryTreeNode<>(left2, null, null);
        BinaryTreeNode<Decision> rightNode2 = new BinaryTreeNode<>(right2, null, null);
        BinaryTreeNode<Decision> rootNode2 = new BinaryTreeNode<>(root2, leftNode2, rightNode2);
        DecisionTree tree2 = new DecisionTree(rootNode2);
        tree2.traverse(1.0, level);
        assertTrue(!root2.isUsed());
        assertTrue(!left2.isUsed());
        assertTrue(right2.isUsed());
    }

    @Test
    public void testPlayerAddInventoryAndCycle() {
        LinearGame game = new LinearGame();
        Player player = new Player(0, 0, 10);

        assertEquals("No item equipped", player.getActiveItemID());

        player.addInventoryItem(new MagicPickup(0, 0, game));
        player.addInventoryItem(new AxePickup(0, 0, game));
        player.addInventoryItem(new PotionPickup(0, 0, 5, game));
        assertEquals(3, player.getInventorySize());
        assertEquals("Magic", player.getActiveItemID());

        player.cycleInventory();
        assertEquals("Axe", player.getActiveItemID());

        player.cycleInventory();
        assertEquals("Health Potion", player.getActiveItemID());

        player.cycleInventory();
        assertEquals("Magic", player.getActiveItemID());
    }

    @Test
    public void testPlayerRemoveActiveItem() {
        LinearGame game = new LinearGame();
        Player player = new Player(0, 0, 10);

        player.addInventoryItem(new MagicPickup(0, 0, game));
        player.addInventoryItem(new AxePickup(0, 0, game));
        player.addInventoryItem(new PotionPickup(0, 0, 5, game));
        assertEquals("Magic", player.getActiveItemID());

        player.removeActiveItem();
        assertEquals(2, player.getInventorySize());
        assertEquals("Axe", player.getActiveItemID());

        player.removeActiveItem();
        assertEquals(1, player.getInventorySize());
        assertEquals("Health Potion", player.getActiveItemID());

        player.removeActiveItem();
        assertEquals(0, player.getInventorySize());
        assertEquals("No item equipped", player.getActiveItemID());
    }

}