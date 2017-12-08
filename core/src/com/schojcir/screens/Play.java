package com.schojcir.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.schojcir.entities.Blinky;
import com.schojcir.entities.Clyde;
import com.schojcir.entities.Inky;
import com.schojcir.entities.Pacman;
import com.schojcir.entities.Pinky;
import com.schojcir.pacmen.Pacmen;

/**
 * Created by jkraj on 12/2/2017.
 */

public class Play implements Screen {

    private Pacmen game;

    private TiledMap mMap;
    private OrthogonalTiledMapRenderer mRenderer;
    private OrthographicCamera camera;

    private Pacman pacman;
    private Blinky blinky;
    private Inky inky;
    private Clyde clyde;
    private Pinky pinky;

    private float unitScale = 1 / 8f;

    public Play(Pacmen game){
        this.game = game;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
//        mMap = new AtlasTmxMapLoader().load("maps/maze.tmx")
//        AssetManager assetManager = new AssetManager();
//        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
//        assetManager.load("maps/maze.tmx", TiledMap.class);
//
//        mMap = assetManager.get("maps/maze.tmx");

        mMap = new TmxMapLoader().load("maps/maze.tmx");
        mRenderer = new OrthogonalTiledMapRenderer(mMap, unitScale);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 28, 34);
        pacman = new Pacman((TiledMapTileLayer) mMap.getLayers().get("Player Layer"), game);
        blinky = new Blinky((TiledMapTileLayer) mMap.getLayers().get("Player Layer"));
        inky = new Inky((TiledMapTileLayer) mMap.getLayers().get("Player Layer"));
        pinky = new Pinky((TiledMapTileLayer) mMap.getLayers().get("Player Layer"));
        clyde = new Clyde((TiledMapTileLayer) mMap.getLayers().get("Player Layer"));

        Gdx.input.setInputProcessor(new GestureDetector(pacman));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new TitleScreen(game));
        }

        camera.update();
        mRenderer.setView(camera);
        mRenderer.render();

//        mRenderer.getBatch().setProjectionMatrix(camera.combined);
        mRenderer.getBatch().begin();
        pacman.draw(mRenderer.getBatch());
        blinky.draw(mRenderer.getBatch());
        inky.draw(mRenderer.getBatch());
        pinky.draw(mRenderer.getBatch());
        clyde.draw(mRenderer.getBatch());
        mRenderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
//        camera.viewportHeight = height;
//        camera.viewportWidth = width;
//        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        mMap.dispose();
        mRenderer.dispose();
        game.dispose();
        pacman.dispose();
        inky.dispose();
        pinky.dispose();
        clyde.dispose();
    }
}
