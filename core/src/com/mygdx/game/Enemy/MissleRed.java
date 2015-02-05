package com.mygdx.game.Enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.TextureManager;

public class MissleRed extends Entity {

    private static TextureRegion currentFrame;

    @Override
    public void addMoney() {

    }

    public MissleRed(Vector2 posE, Vector2 directionE) {
        super(TextureManager.MISSLERED, currentFrame, posE, directionE, 1, 1, 3);
    }

    @Override
    public void update() {
        pos.add(direction); //direction określone w klasie Entity
    }
}
