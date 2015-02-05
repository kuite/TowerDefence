package com.mygdx.game.Enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.TextureManager;

import static com.mygdx.game.Enemy.EntityManager.addMissile;
import static com.mygdx.game.Enemy.EntityManager.getEnemies;

public class TurretGreen extends Towers {

    private long lastFire;
    private float dx, dy, lowestdx, lowestdy;
    public Vector2 setUpPos;
    public static float angle;


    public TurretGreen(Vector2 posE) {
        super(TextureManager.TOWERGREEN, new TextureRegion(TextureManager.LVL1GREEN), posE);
        setUpPos = posE;
        lowestdx = lowestdy = dx = dy = 999;
    }

    @Override
    public void update() {
        for (Entity e : getEnemies()) {
            dx = getPosition().x + 3*TextureManager.LVL1RED.getWidth()/4 - e.getPosition().x;
            dy = getPosition().y  + TextureManager.LVL1RED.getHeight()/2 - e.getPosition().y;
            if (Math.sqrt((dx*dx + dy*dy)) < Math.sqrt((lowestdx*lowestdx + lowestdy*lowestdy)) ) {
                lowestdx = dx;
                lowestdy = dy;
            }
        }

        double  temp = Math.toDegrees( Math.atan(Math.abs(lowestdy) / Math.abs(lowestdx)) ); //angle between x asis of turret and line from turret to enemy

        //fixing calculate angle for 360 x,y axis
        if(lowestdy < 0 && lowestdx > 0){ //I
            temp =  360 - temp;
        }if(lowestdy > 0 && lowestdx < 0){ //III
            temp = 180 - temp;
        }if(lowestdy < 0 && lowestdx < 0){ //IV
            temp = 180 + temp;
        }
        angle = (float) Math.abs(temp);

        if (Math.abs(lowestdx) <= 7 + TextureManager.RANGE1.getWidth() / 2 & Math.abs(lowestdy) <= 7 + TextureManager.RANGE1.getWidth() / 2) {
            setI(angle);
        }else if (Math.abs(lowestdx) > 7 + TextureManager.RANGE1.getWidth() / 2 | Math.abs(lowestdy) > 7 + TextureManager.RANGE1.getWidth() / 2){
            setI(0);
        }

        if (System.currentTimeMillis() - lastFire >= 400) {
            //firing when unit in the zone
            if (Math.abs(lowestdx) <= 7 + TextureManager.RANGE1.getWidth() / 2 & Math.abs(lowestdy) <= 7 + TextureManager.RANGE1.getWidth() / 2) {
                if(lowestdy < 0 && lowestdx > 0){ //I
                    addMissile(new MissleGreen(pos.cpy().add(-2 + 3 * TextureManager.LVL1RED.getWidth() / 4, 12 + TextureManager.LVL1RED.getHeight() / 2), new Vector2(-lowestdx / 6, -lowestdy / 5)));
                }if(lowestdy > 0 && lowestdx > 0){ //II
                    addMissile(new MissleGreen(pos.cpy().add(-2 + 3 * TextureManager.LVL1RED.getWidth() / 4, 12 + TextureManager.LVL1RED.getHeight() / 2), new Vector2(-lowestdx / 6, -lowestdy / 5)));
                }if(lowestdy > 0 && lowestdx < 0){ //III
                    addMissile(new MissleGreen(pos.cpy().add(-2 + 3 * TextureManager.LVL1RED.getWidth() / 4, 12 + TextureManager.LVL1RED.getHeight() / 2), new Vector2(-lowestdx / 6, -lowestdy / 5)));
                }if(lowestdy < 0 && lowestdx < 0){ //IV
                    addMissile(new MissleGreen(pos.cpy().add(-2 + 3 * TextureManager.LVL1RED.getWidth() / 4, 12 + TextureManager.LVL1RED.getHeight() / 2), new Vector2(-lowestdx / 6, -lowestdy / 5)));
                }
            }
            lastFire = System.currentTimeMillis();
        }

        lowestdx = TextureManager.RANGE1.getWidth();
        lowestdy = TextureManager.RANGE1.getWidth();
    }
}


