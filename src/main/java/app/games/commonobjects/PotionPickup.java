package app.games.commonobjects;

import app.display.common.SpriteLocation;
import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.model.gameobjects.Collectible;
import app.gameengine.model.gameobjects.Player;

public class PotionPickup extends Collectible {

    private int heal;

    public PotionPickup(double x, double y, int heal, Game game) {
        super(x, y, game, "Health Potion");
        this.spriteSheetFilename = "MiniWorldSprites/User Interface/Icons-Essentials.png";
        this.defaultSpriteLocation = new SpriteLocation(3, 1);
        this.heal = heal;
    }

    public int getHealAmount() {
        return heal;
    }

    @Override
    public void use(Level level) {
        Player player = level.getPlayer();
        if (player != null) {
            player.setHP(player.getHP() + heal);
            player.removeActiveItem();
        }
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
