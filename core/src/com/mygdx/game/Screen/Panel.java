package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemy.EntityManager;
import com.mygdx.game.Enemy.TurretBlue;
import com.mygdx.game.Enemy.TurretGreen;
import com.mygdx.game.Enemy.TurretRed;
import com.mygdx.game.TextureManager;

public class Panel {

    private static float xt, yt;
    private static int isChosen = 0;
    private FPSLogger fps = new FPSLogger();
    private GameScreen gs = new GameScreen();
    private Array<Rectangle> occupied = new Array<Rectangle>();

    public void render(SpriteBatch sp, OrthoCamera camera) {
        Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
        xt = touch.x;
        yt = touch.y;

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int x, int y, int pointer, int button) {
                if (xt >= 403 && xt < 403 + TextureManager.TOWERRED.getWidth()) {
                    if (yt > 130 && yt < 130 + TextureManager.TOWERRED.getHeight()) {
                        isChosen = 1;
                    }
                }
                if (xt >= 289 && xt < 289 + TextureManager.TOWERRED.getWidth()) {
                    if (yt > 130 && yt < 130 + TextureManager.TOWERRED.getHeight()) {
                        isChosen = 2;
                    }
                }
                if (xt >= 175 && xt < 175 + TextureManager.TOWERRED.getWidth()) {
                    if (yt > 130 && yt < 130 + TextureManager.TOWERRED.getHeight()) {
                        isChosen = 3;
                    }
                }
                return true;
            }

            public boolean touchUp(int xUp, int yUp, int pointer, int button) {
                boolean avaliable = true;
                if (isChosen != 0) {
                    for (Rectangle r : occupied) {
                        if (r.overlaps(new Rectangle(xt - TextureManager.TOWERRED.getWidth() / 2, yt - TextureManager.TOWERBLUE.getHeight() / 2, TextureManager.TOWERRED.getWidth(), TextureManager.TOWERBLUE.getHeight()))) {
                            avaliable = false;
                        }
                    }

                    for (int i = 0; i < gs.getPlaces().size; i++) {
                        if (GameScreen.places.get(i).contains(xt, yt) && avaliable) {
                            addTurret(isChosen, xt, yt);
                        }
                    }
                    isChosen = 0;
                }
                return true;
            }
        });
        tempDraw(sp);
        fps.log();
    }


    public void addTurret(int i, float x, float y) {
        if (i == 1) {
            if(GameScreen.money >= 25) {
                EntityManager.addTurret(new TurretRed(new Vector2(x - TextureManager.TOWERRED.getWidth() / 2, y - TextureManager.TOWERRED.getHeight() / 2)));
                occupied.add(new Rectangle(xt - TextureManager.TOWERRED.getWidth() / 2, yt - TextureManager.TOWERBLUE.getHeight() / 2, TextureManager.TOWERRED.getWidth(), TextureManager.TOWERBLUE.getHeight()));
                GameScreen.money = GameScreen.money - 25;
            }
        }
        if (i == 2) {
            if(GameScreen.money >= 10) {
                EntityManager.addTurret(new TurretBlue(new Vector2(x - TextureManager.TOWERRED.getWidth() / 2, y - TextureManager.TOWERRED.getHeight() / 2)));
                occupied.add(new Rectangle(xt - TextureManager.TOWERRED.getWidth() / 2, yt - TextureManager.TOWERBLUE.getHeight() / 2, TextureManager.TOWERRED.getWidth(), TextureManager.TOWERBLUE.getHeight()));
                GameScreen.money = GameScreen.money - 10;
            }
        }
        if (i == 3) {
            if(GameScreen.money >= 13) {
                EntityManager.addTurret(new TurretGreen(new Vector2(x - TextureManager.TOWERRED.getWidth() / 2, y - TextureManager.TOWERRED.getHeight() / 2)));
                occupied.add(new Rectangle(xt - TextureManager.TOWERRED.getWidth() / 2, yt - TextureManager.TOWERBLUE.getHeight() / 2, TextureManager.TOWERRED.getWidth(), TextureManager.TOWERBLUE.getHeight()));
                GameScreen.money = GameScreen.money - 13;
            }
        }
    }

    //drawing when dragging turrets from panel
    public void tempDraw(SpriteBatch sp) {
        if (isChosen == 1) {
            sp.draw(TextureManager.TOWERRED, xt - TextureManager.TOWERRED.getWidth() / 2, yt - TextureManager.TOWERRED.getHeight() / 2);
        }
        if (isChosen == 2) {
            sp.draw(TextureManager.TOWERBLUE, xt - TextureManager.TOWERRED.getWidth() / 2, yt - TextureManager.TOWERRED.getHeight() / 2);
        }
        if (isChosen == 3) {
            sp.draw(TextureManager.TOWERGREEN, xt - TextureManager.TOWERRED.getWidth() / 2, yt - TextureManager.TOWERRED.getHeight() / 2);
        }

        if (isChosen != 0) {
            for (int i = 0; i < gs.getPlaces().size; i++) {
                if (GameScreen.places.get(i).contains(xt, yt)) {
                    sp.draw(TextureManager.RANGE1, xt - TextureManager.RANGE1.getWidth() / 2, yt - TextureManager.RANGE1.getHeight() / 2);
                }else if (!GameScreen.places.get(i).contains(xt, yt)) {
                    //sp.draw(TextureManager.RANGERD, xt - TextureManager.RANGE1.getWidth() / 2, yt - TextureManager.RANGE1.getHeight() / 2);
                }
            }
        }
    }
}
