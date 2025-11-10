package app.tests;

import app.gameengine.Level;
import app.gameengine.model.ai.Decision;
import app.gameengine.model.gameobjects.Agent;

/**
 * Testing utility class for Decision trees.
 * Simplifies testing by making decide() and doAction() behaviors controllable.
 */
public class TestDecision extends Decision {

    private boolean decideResult;
    private boolean used;

    /**
     * Constructs a TestDecision with controllable behavior.
     *
     * @param agent  the agent for this decision
     * @param name   the name of this decision
     * @param decide the value to return from decide()
     */
    public TestDecision(Agent agent, String name, boolean decide) {
        super(agent, name);
        this.decideResult = decide;
        this.used = false;
    }

    /**
     * Returns whether this decision's action has been used.
     *
     * @return true if doAction has been called, false otherwise
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Returns the predetermined decision result.
     *
     * @param dt    the time delta
     * @param level the current level
     * @return the decide result passed to the constructor
     */
    @Override
    public boolean decide(double dt, Level level) {
        return decideResult;
    }

    /**
     * Marks this decision as used.
     *
     * @param dt    the time delta
     * @param level the current level
     */
    @Override
    public void doAction(double dt, Level level) {
        used = true;
    }
}
