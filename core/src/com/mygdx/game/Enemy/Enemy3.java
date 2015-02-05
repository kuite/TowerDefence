package com.mygdx.game.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Screen.GameScreen;
import com.mygdx.game.TextureManager;

public class Enemy3 extends Entity{

    private static final int col = 4;
    private static final int row = 4;
    private static Animation animation, animationH;
    private static TextureRegion[] down, up, right, left, healthbars;
    private static TextureRegion currentFrame;
    private float frame;
    private int hpE, healthframe;

    public Enemy3(Vector2 posE, Vector2 directionE, int hpE) {
        super(TextureManager.ENEMY3, currentFrame, posE, directionE, col, row, hpE);
        this.hpE = hpE;

        TextureRegion[][] temp = TextureRegion.split(TextureManager.ENEMY3, TextureManager.ENEMY3.getWidth()/col, TextureManager.ENEMY3.getHeight()/row);

        down = new TextureRegion[col];
        int index = 0;
        for (int j = 0; j < col; j++) {
            down[index++] = temp[0][j];
        }

        up = new TextureRegion[col];
        index = 0;
        for (int j = 0; j < col; j++) {
            up[index++] = temp[3][j];
        }

        right = new TextureRegion[col];
        index = 0;
        for (int j = 0; j < col; j++) {
            right[index++] = temp[2][j];
        }

        left = new TextureRegion[col];
        index = 0;
        for (int j = 0; j < col; j++) {
            left[index++] = temp[1][j];
        }

        TextureRegion[][] tempH = TextureRegion.split(TextureManager.HEALTH, TextureManager.HEALTH.getWidth(), TextureManager.HEALTH.getHeight()/7);
        index = 0;
        healthbars = new TextureRegion[7];
        for(int j = 0; j < 7; j++){
            healthbars[index++] = tempH[j][0];
        }

        animationH = new Animation(1, healthbars);
        animation = new Animation(0.25f, down); //(szybkosc zmiany między klatkami, Zbiór tekstur z klatkami)
        frame = 0f;
    }

    @Override
    public void addMoney() {
        GameScreen.money = GameScreen.money + 3;
    }

    @Override
    public void update() {
        pos.add(direction);

        //animation
        frame += Gdx.graphics.getDeltaTime();

        //moving setting
        if((pos.y > 324 && pos.y <= 999) && (pos.x >= 30 && pos.x <= 33) ){
            direction =  new Vector2(0, -0.8f);
            animation = new Animation(0.25f, down);
        }if((pos.y > 324 && pos.y <= 326) && (pos.x >= 30 && pos.x < 223) ) {
            direction =  new Vector2(0.8f, 0);
            animation = new Animation(0.25f, right);
        }else if((pos.y > 325 && pos.y <= 596) && (pos.x >= 223 && pos.x < 227) ){
            direction =  new Vector2(0, 0.8f);
            animation = new Animation(0.25f, up);
        }else if((pos.y <= 650 && pos.y > 596) && (pos.x >= 223 && pos.x < 999) ) {
            direction = new Vector2(0.8f, 0);
            animation = new Animation(0.25f, right);
        }

        txr = animation.getKeyFrame(frame, true);

        float ratio = (float) hp / fullhp;
        if(ratio >= 1){
            healthframe = 0;
        }else if(ratio >= 0.83 && ratio < 1){
            healthframe = 1;
        }else if(ratio >= 0.66 && ratio < 0.83){
            healthframe = 2;
        }else if(ratio >= 0.5 && ratio < 0.66){
            healthframe = 3;
        }else if(ratio >= 0.33 && ratio < 5){
            healthframe = 4;
        }else if(ratio >= 0.16 && ratio < 0.33){
            healthframe = 5;
        }else if(ratio >= 0 && ratio < 0.16){
            healthframe = 6;
        }

        health = animationH.getKeyFrame(healthframe, true);
    }

}

