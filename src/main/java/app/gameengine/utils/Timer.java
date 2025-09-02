package app.gameengine.utils;

/**
 * Timer that allows for actions to occur after a delay, synced with dt.
 * <p>
 * Certain actions should occur after a delay, or on a cooldown. This class
 * should be used whenever this is necessary, in order to sync these actions
 * with the time passed since the last frame throughout the game engine.
 */
public class Timer {

    private double elapsed;
    private double cooldown;
    private boolean isPaused;

    /**
     * Construct a timer with the given cooldown, in seconds.
     * 
     * @param cooldown the cooldown, in seconds
     */
    public Timer(double cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * Returns the cooldown
     * 
     * @return the cooldown
     */
    public double getCooldown() {
        return this.cooldown;
    }

    /**
     * Sets the cooldown
     * 
     * @param cooldown the cooldown to set
     */
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * Reset the timer to its initial state, with no elapsed time.
     */
    public void reset() {
        this.elapsed = 0;
    }

    /**
     * Returns the amount of time elapsed since the last reset.
     * 
     * @return the elapsed time
     */
    public double getElapsedTime() {
        return this.elapsed;
    }

    /**
     * Returns whether the timer is paused. If it is paused, updates cannot occur.
     * 
     * @return {@code true} if the timer is paused, {@code false} otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Sets whether the timer is paused. If it is paused, updates cannot occur.
     * 
     * @param isPaused whether the timer will be paused
     */
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * Increase the amount of time that has elapsed by dt. Suitable when the timer
     * needs to be updated without checking whether the cooldown has finished.
     * 
     * @param dt the elapsed time, in seconds
     */
    public void advance(double dt) {
        if (isPaused) {
            return;
        }
        this.elapsed += dt;
    }

    /**
     * Increase the amount of time that has elapsed by dt, and return whether the
     * cooldown has finished. If so, additionally reset the timer.
     * 
     * @param dt the elapsed time, in seconds
     * @return
     */
    public boolean tick(double dt) {
        if (isPaused) {
            return false;
        }
        this.elapsed += dt;
        if (elapsed > cooldown) {
            this.reset();
            return true;
        }
        return false;
    }

    /**
     * Returns whether the cooldown has finished. If so, reset the timer.
     * 
     * @return {@code true} if the cooldown has finished, {@code false} otherwise
     */
    public boolean check() {
        return tick(0);
    }

}
