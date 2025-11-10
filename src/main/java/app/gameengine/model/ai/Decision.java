package app.gameengine.model.ai;
import app.gameengine.Level;
import app.gameengine.model.gameobjects.Agent;

public abstract class Decision {

    private Agent agent;
    private String name;

    /**
     * Constructs a Decision with the specified agent and name.
     *
     * @param agent the agent associated with this decision
     * @param name  the name of the decision
     */
    public Decision(Agent agent, String name) {
        this.agent = agent;
        this.name = name;
    }

    /**
     * Returns the name of this decision.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this decision.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the agent associated with this decision.
     *
     * @return the agent
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Determines whether this decision should be made.
     *
     * @param dt    the time delta since last update
     * @param level the current level
     * @return true if the decision is made, false otherwise
     */
    public abstract boolean decide(double dt, Level level);

    /**
     * Executes the action associated with this decision.
     *
     * @param dt    the time delta since last update
     * @param level the current level
     */
    public abstract void doAction(double dt, Level level);
}
