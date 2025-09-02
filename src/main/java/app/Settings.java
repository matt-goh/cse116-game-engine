package app;

/**
 * Static class containing properties which may change at runtime.
 * <p>
 * The static variables defined in this class determine certain properties of
 * the game, currently mostly for debugging, including showing object hitboxes,
 * enabling god mode, and pausing the game. Unlike {@link Configuration}, these
 * variables may change at runtime.
 * <p>
 * Note that these properties are essentially flags, and it is up to the
 * game/level/object to actually obey these settings properly. In that way,
 * these settings more or less act as a way to communicate important properties
 * between disparate parts of the engine.
 * 
 * @see Configuration
 */
public class Settings {

    /**
     * Prevent instantiation, as this class is intended to be static.
     */
    private Settings() {
    }

    private static boolean showHitboxes = false;
    private static boolean godMode = false;
    private static boolean paused = false;

    /**
     * Returns whether hitboxes should be displayed for game objects.
     *
     * @return {@code true} if hitboxes are shown, {@code false} otherwise
     */
    public static boolean showHitboxes() {
        return Settings.showHitboxes;
    }

    /**
     * Sets whether hitboxes should be displayed for game objects.
     *
     * @param shown {@code true} to show hitboxes, {@code false} to hide them
     */
    public static void setShowHitboxes(boolean shown) {
        Settings.showHitboxes = shown;
    }

    /**
     * Toggles the hitbox display setting.
     * <p>
     * If hitboxes are currently shown, they will be hidden; if hidden, they will be
     * shown.
     */
    public static void toggleShowHitboxes() {
        Settings.showHitboxes = !Settings.showHitboxes;
    }

    /**
     * Returns whether god mode is enabled.
     *
     * @return {@code true} if god mode is enabled, {@code false} otherwise
     */
    public static boolean godMode() {
        return Settings.godMode;
    }

    /**
     * Sets whether god mode is enabled.
     *
     * @param enabled {@code true} to enable god mode, {@code false} to disable it
     */
    public static void setGodMode(boolean enabled) {
        Settings.godMode = enabled;
    }

    /**
     * Toggles the god mode setting.
     * <p>
     * If god mode is currently enabled, it will be disabled; if disabled, it will
     * be enabled.
     */
    public static void toggleGodMode() {
        Settings.godMode = !Settings.godMode;
    }

    /**
     * Returns whether the game is currently paused.
     *
     * @return {@code true} if the game is paused, {@code false} otherwise
     */
    public static boolean paused() {
        return Settings.paused;
    }

    /**
     * Sets whether the game is currently paused.
     *
     * @param paused {@code true} to pause the game, {@code false} to unpause
     */
    public static void setPaused(boolean paused) {
        Settings.paused = paused;
    }

    /**
     * Toggles the paused state of the game.
     * <p>
     * If the game is currently paused, it will be unpaused; if unpaused, it will be
     * paused.
     */
    public static void togglePaused() {
        Settings.paused = !Settings.paused;
    }

}
