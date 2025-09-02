package app.games.mario;

import app.display.common.SpriteLocation;

public class HiddenBlock extends QuestionBlock {

    public HiddenBlock(double x, double y) {
        super(x, y);
        this.defaultSpriteLocation = new SpriteLocation(0, 0);
    }

    @Override
    public void initAnimations() {

    }

}
