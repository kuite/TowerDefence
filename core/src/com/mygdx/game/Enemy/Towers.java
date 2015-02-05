package com.mygdx.game.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.TextureManager;

public abstract class Towers {

    private Texture body;
    private TextureRegion cannon;
    public Vector2 pos;
    private float i;

    public Towers(Texture body, TextureRegion cannon, Vector2 pos){
        this.body = body;
        this.cannon = cannon;
        this.pos = pos;
    }


    public abstract void update();


    public void render(SpriteBatch sp){
            sp.draw(body, pos.x, pos.y);
            sp.draw(cannon, pos.x - 2, pos.y + 12, 3* TextureManager.LVL1RED.getWidth()/4, TextureManager.LVL1RED.getHeight()/2, TextureManager.LVL1RED.getWidth(), TextureManager.LVL1RED.getHeight(), 1, 1, i);
    }

    public Vector2 getPosition(){
        return pos;
    }

    public Rectangle getRounds(){
        return new Rectangle(pos.x, pos.y, body.getWidth(), body.getHeight());
    }

    //call in Turret class
    public void setI(float i){
        this.i = i;
    }
}
