package com.mygdx.game.Enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screen.GameScreen;
import com.mygdx.game.TextureManager;

public class EntityManager {

    private static Array<Entity> enemies = new Array<Entity>();
    private static Array<Towers> turrets = new Array<Towers>();
    private static Array<Entity> missiles = new Array<Entity>();
    private int fn, sn, amount;
    public static long score = 0;

    public EntityManager(int difficulty) {
        float y = MathUtils.random(MyGdxGame.HEIGHT, MyGdxGame.HEIGHT + TextureManager.ENEMY1.getHeight() / 4);
        fn = sn = amount = 2;

        if (difficulty < 4) {
            amount = MathUtils.random(3, 4);
            fn = 1;
            sn = 3;
        } else if (difficulty >= 4 && difficulty < 9) {
            amount = MathUtils.random(3, 4);
            fn = 3;
            sn = 5;
        } else if (difficulty >= 9 && difficulty <= 13) {
            amount = MathUtils.random(3, 4);
            fn = 6;
            sn = 8;
        } else if (difficulty > 13 && difficulty < 19) {
            amount = MathUtils.random(3, 4);
            fn = 7;
            sn = 12;
        } else if (difficulty >= 19) {
            amount = Math.round(difficulty * (float) 0.4);
            fn = 9;
            sn = 12;
            double suprise = Math.random();
            if (suprise > 0.8) {
                fn = 12;
                sn = 13;
                amount = 2;
            }
        }

        for (int i = 0; i < amount; i++) {
            int random = MathUtils.random(fn, sn);
            float x = 31;
            y += MathUtils.random(70, 100);
            if (random == 1) {
                addEntity(new Enemy1(new Vector2(x, y), new Vector2(0, -0.8f), 8));
            } else if (random == 2) {
                addEntity(new Enemy2(new Vector2(x, y), new Vector2(0, -0.8f), 13));
            } else if (random == 3) {
                addEntity(new Enemy3(new Vector2(x, y), new Vector2(0, -0.8f), 22));
            } else if (random == 4) {
                addEntity(new Enemy4(new Vector2(x, y), new Vector2(0, -0.8f), 35));
            } else if (random == 5) {
                addEntity(new Enemy5(new Vector2(x, y), new Vector2(0, -0.8f), 48));
            } else if (random == 6) {
                addEntity(new Enemy6(new Vector2(x, y), new Vector2(0, -0.8f), 60));
            } else if (random == 7) {
                addEntity(new Enemy7(new Vector2(x, y), new Vector2(0, -0.8f), 80));
            } else if (random == 8) {
                addEntity(new Enemy8(new Vector2(x, y), new Vector2(0, -0.8f), 100));
            } else if (random == 9) {
                addEntity(new Enemy9(new Vector2(x, y), new Vector2(0, -0.8f), 150));
            } else if (random == 10) {
                addEntity(new Enemy10(new Vector2(x, y), new Vector2(0, -0.8f), 200));
            } else if (random == 11) {
                addEntity(new Enemy11(new Vector2(x, y), new Vector2(0, -0.8f), 220));
            } else if (random == 12) {
                addEntity(new Enemy12(new Vector2(x, y), new Vector2(0, -0.8f), 300));
            } else if (random == 13) {
                addEntity(new Enemy13(new Vector2(x, y), new Vector2(0, -0.8f), 1000));
            }

        }
    }

    public void clear(){
        enemies = new Array<Entity>();
        turrets = new Array<Towers>();
        missiles = new Array<Entity>();
    }

    public static void addEntity(Entity enem) {
        enemies.add(enem);
    }

    public static void addTurret(Towers twr) {
        turrets.add(twr);
    }

    public static void addMissile(Entity missile) {
        missiles.add(missile);
    }

    public static Array<Entity> getEnemies() {
        return enemies;
    }

    public static Array<Towers> getTurrets() {
        return turrets;
    }

    public static Array<Entity> getGreenMissles() {
        Array<Entity> greens = new Array<Entity>();
        for (Entity m : missiles) {
            if (m instanceof MissleGreen) {
                greens.add(m);
            }
        }
        return greens;
    }

    public static Array<Entity> getRedMissles() {
        Array<Entity> red = new Array<Entity>();
        for (Entity m : missiles) {
            if (m instanceof MissleRed) {
                red.add(m);
            }
        }
        return red;
    }

    public static Array<Entity> getBlueMissles() {
        Array<Entity> blue = new Array<Entity>();
        for (Entity m : missiles) {
            if (m instanceof MissleBlue) {
                blue.add(m);
            }
        }
        return blue;
    }

    public void update() {
        for (Entity m : missiles) {
            m.update();
        }
        for (Entity e : enemies) {
            e.update();
        }
        for (Towers t : turrets) {
            t.update();
        }
    }

    public void render(SpriteBatch sp) {
        for (Entity m : missiles) {
            m.renderTexture(sp);
        }
        for (Towers t : turrets) {
            t.render(sp);
        }
        for (Entity e : enemies) {
            e.render(sp);
            e.drawHp(sp);
        }
        checkCollision();
    }


    private void checkCollision() {
        for (Entity e : enemies) {
            for (Entity g : getGreenMissles()) {
                if (e.getRounds().overlaps(g.getRounds())) {
                    e.removeHp(2);
                    getGreenMissles().removeValue(g, false);
                }
            }
            for (Entity r : getRedMissles()) {
                if (e.getRounds().overlaps(r.getRounds())) {
                    e.removeHp(3);
                    getRedMissles().removeValue(r, false);
                }
            }
            for (Entity b : getBlueMissles()) {
                if (e.getRounds().overlaps(b.getRounds())) {
                    e.removeHp(1);
                    e.direction.x = -(e.direction.x - (e.direction.x * (float) 0.6));
                    e.direction.y = -(e.direction.y - (e.direction.y * (float) 0.6));
                    e.pos.add(e.direction);
                    getBlueMissles().removeValue(b, false);
                }
            }
            for (Entity m : missiles) {
                if (e.getRounds().overlaps(m.getRounds())) {
                    missiles.removeValue(m, false); // if true == comparison will be used, if false .equals() will be used
                }
            }
            if (e.below0()) {
                enemies.removeValue(e, false);
                e.addMoney();
                score++;
            }
            if (e.pos.x > TextureManager.PANEL.getWidth()) {
                enemies.removeValue(e, false);
                GameScreen.templife = GameScreen.templife - 1;
            }
        }
        for (Entity m : missiles) {
            if (m.pos.x > TextureManager.PANEL.getWidth() || m.pos.y > TextureManager.PANEL.getHeight()) {
                missiles.removeValue(m, false);
            }
            if (m.pos.x < 0 || m.pos.y < 0) {
                missiles.removeValue(m, false);
            }
        }
    }
}
