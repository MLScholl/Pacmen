package com.schojcir.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.schojcir.pacmen.Pacmen;

import static com.schojcir.pacmen.Pacmen.gameSkin;

/**
 * Created by mlscholl on 12/7/17.
 */

public class SettingsScreen implements Screen {

    private Pacmen game;
    private Stage stage;

    public SettingsScreen(Pacmen aGame) {

        game = aGame;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Gdx.input.setCatchBackKey(true);

        Table root = new Table(gameSkin);
        root.setDebug(true);
        root.setBackground("bg");
        root.setFillParent(true);
        stage.addActor(root);

        Table booth = new Table(gameSkin);
        booth.setDebug(true);
        booth.setBackground("boothscore");
        root.add(booth).fillY().fillX().expandX().expandY();

        Table highscores = new Table(gameSkin);
        //highscores.setDebug(true);
        booth.add(highscores).size(600,1000);

        Label label = new Label("Settings", gameSkin, "title");
        label.setFontScale(1.8f);
        label.setAlignment(Align.center);
        highscores.add(label).colspan(2);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new TitleScreen(game));
        }

        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        game.dispose();
        stage.dispose();
    }
}
