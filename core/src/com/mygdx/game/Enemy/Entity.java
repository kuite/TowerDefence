package com.mygdx.game.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.TextureManager;

public abstract class Entity {

    private Texture txE;
    protected TextureRegion txr, health;
    protected Vector2 pos;
    protected Vector2 direction;
    protected int col,row, hp;
    protected int fullhp;



    public Entity(Texture txE, TextureRegion txr, Vector2 pos, Vector2 direction, int col, int row, int hp){
        this.txE = txE;
        this.txr = txr;
        this.pos = pos;
        this.direction = direction;
        this.col = col;
        this.row = row;
        this.hp = hp;
        fullhp = hp;
    }


    public abstract void update();

    public abstract void addMoney();

    public void render(SpriteBatch sp){
        sp.draw(txr, pos.x, pos.y);
    }
    public void renderTexture(SpriteBatch sp){
        sp.draw(txE, pos.x, pos.y);
    }

    public Rectangle getRounds(){
        return new Rectangle(pos.x, pos.y, txE.getWidth()/col, txE.getHeight()/row);
    }

    public Vector2 getPosition(){
        return pos;
    }

    public void removeHp(int i){
        hp = hp -i;
    }

    public void drawHp(SpriteBatch sp){
        sp.draw(health, pos.x + txE.getWidth()/(2*col) - TextureManager.HEALTH.getWidth()/2, pos.y + txE.getHeight()/row);
    }

    public boolean below0(){
        boolean dead;
        if(hp <= 0){
            dead = true;
        }else{
            dead = false;
        }
        return dead;
    }
}
