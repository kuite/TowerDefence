package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemy.EntityManager;
import com.mygdx.game.TextureManager;

import static com.mygdx.game.Enemy.EntityManager.getEnemies;

public class GameScreen extends Screen {

    private OrthoCamera camera;
    public static EntityManager entityManager;
    private Panel panel;
    private OrthogonalTiledMapRenderer renderer;
    public static Array<Rectangle> places = new Array<Rectangle>();
    private int lvl;
    private Texture fontTexture = new Texture(Gdx.files.internal("CarnevaleeFreakshow.png"));
    private BitmapFont font = new BitmapFont(Gdx.files.internal("CarnevaleeFreakshow.fnt"), new TextureRegion(fontTexture), false);
    public static int money;
    public static int templife;
    private String life = "V";

    @Override
    public void create() {
        lvl = 1;
        templife = 5;
        money = 40;
        camera = new OrthoCamera();
        entityManager = new EntityManager(lvl);
        TiledMap map = new TmxMapLoader().load("brownmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        for(int i = 0; i < 50; i++){
            for(int j = 0; j < 50; j++){
                TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2); //accessing to layer num. 2 in map (counting from bottom)
                if(layer.getCell(i, j) != null){ //if cell is not empty
                    places.add(new Rectangle(i * 16, j * 16, 16, 16));
                }
            }
        }
        panel = new Panel();
        camera.resize();
    }

    @Override
    public void update() {
        camera.update();
        entityManager.update();
    }

    @Override
    public void render(SpriteBatch sp) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sp.setProjectionMatrix(camera.combined);


        sp.begin();
        renderer.setView(camera);
        renderer.render();
        sp.end();

        switch(templife){
            case 5:
                life = "V";
                break;
            case 4:
                life = "IV";
                break;
            case 3:
                life = "III";
                break;
            case 2:
                life = "II";
                break;
            case 1:
                life = "I";
                break;
        }

        sp.begin();
        entityManager.render(sp);
        sp.draw(TextureManager.PANEL, 0, 0);
        panel.render(sp, camera);
        font.draw(sp,"$13", 180, 125);
        font.draw(sp,"$10", 294, 125);
        font.draw(sp,"$25", 408, 125);
        font.draw(sp,"$: " + money, 160, 60);
        font.draw(sp,"Lives : " + life, 160, 90);
        sp.end();


        if(getEnemies().size == 0){
            lvl++;
            entityManager = new EntityManager(lvl);
        }

        if(templife <= 0){
            entityManager.clear();
            ScreenManager.setScreen(new ScoresScreen());
        }
    }

    public Array getPlaces(){
        return places;
    }


    @Override
    public void resize(int width, int height) {
        camera.resize();
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
