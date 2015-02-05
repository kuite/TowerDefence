package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screen.Menu;
import com.mygdx.game.Screen.ScreenManager;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener {

	private SpriteBatch batch;
    public static int WIDTH = 480, HEIGHT = 800;

    @Override
	public void create () {
        batch = new SpriteBatch();
        ScreenManager.setScreen(new Menu());
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().update();
        }

        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().render(batch);
        }
	}


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().resize(WIDTH, HEIGHT);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().dispose();
        }
        batch.dispose();
    }

    @Override
    public void pause() {
        super.pause();
        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().pause();
        }
    }

    @Override
    public void resume() {
        super.resume();
        if(ScreenManager.getCurrentScreen() != null){
            ScreenManager.getCurrentScreen().resume();
        }
    }
}
