package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Enemy.EntityManager;
import com.mygdx.game.Save;
import com.mygdx.game.TextureManager;


public class ScoresScreen extends Screen {

    private Stage stage;
    private TextButton button;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private OrthoCamera camera;
    private Texture fontTexture = new Texture(Gdx.files.internal("CarnevaleeFreakshow.png"));
    private BitmapFont font = new BitmapFont(Gdx.files.internal("CarnevaleeFreakshow.fnt"), new TextureRegion(fontTexture), false);
    private long[] highScores;
    private boolean newHighScore;

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();

        Save.load(); //creating file if do not exist
        Save.gd.setTenativeScore(EntityManager.score);
        newHighScore = Save.gd.isHighScore(Save.gd.getTentativeScore());

        if(newHighScore) {
            Save.gd.addHighScore(
                    Save.gd.getTentativeScore()
            );
            Save.save();
        }
        highScores = Save.gd.getHighScores();


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up-button"); //usual button?
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");

        button = new TextButton("Main Menu", textButtonStyle);
        stage.addActor(button);
        button.setWidth(Gdx.graphics.getWidth());
        button.setHeight(Gdx.graphics.getHeight() / 6);
        button.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth() / 2), 100);

        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(new Menu());
                stage.clear();
            }
        });
    }

    @Override
    public void update() {
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(185/255f, 122/255f, 87/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(camera.combined);

        sb.begin();
        font.draw(sb, "Your last score : " + EntityManager.score, TextureManager.PANEL.getWidth()/2 - 150, 4*TextureManager.PANEL.getHeight()/5 + 40);
        font.draw(sb, "High Scores : ", TextureManager.PANEL.getWidth()/2 - 150, 4*TextureManager.PANEL.getHeight()/5);
        for(int i = 0; i < highScores.length; i++){
            String s = String.format("%d. \t %s", i + 1, highScores[i]);
            font.draw(sb, s, TextureManager.PANEL.getWidth()/2 - 100, 4*TextureManager.PANEL.getHeight()/5 - 40*(i+1));
        }
        sb.end();

        sb.begin();

        stage.act();
        stage.draw();

        sb.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
