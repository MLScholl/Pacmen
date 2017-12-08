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

import java.util.PriorityQueue;

import static com.schojcir.pacmen.Pacmen.gameSkin;

/**
 * Created by mlscholl on 12/6/17.
 */

public class HighScoreScreen implements Screen {

    private Pacmen game;
    private Stage stage;
    private String users[]= new String[10];
    private String scores[]= new String[10];

    public HighScoreScreen(Pacmen aGame) {

        fillTables();

        game = aGame;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Gdx.input.setCatchBackKey(true);

        Table root = new Table(gameSkin);
        //root.setDebug(true);
        root.setBackground("bg");
        root.setFillParent(true);
        stage.addActor(root);

        Table booth = new Table(gameSkin);
        //booth.setDebug(true);

        booth.setBackground("boothscore");
        root.add(booth).fillY().fillX().expandX().expandY();

        Table highscores = new Table(gameSkin);
        //highscores.setDebug(true);
        booth.add(highscores).size(600,1000);

        Label label = new Label("Highscores", gameSkin, "title");
        label.setFontScale(1.8f);
        label.setAlignment(Align.center);
        highscores.add(label).colspan(2).top();

        highscores.row();
        label = new Label("User", gameSkin);
        label.setFontScale(1.5f);
        highscores.add(label);
        label = new Label("Score", gameSkin);
        label.setFontScale(1.5f);
        highscores.add(label);

        for (int i = 0; i < 10; i++) {

            Label user = new Label(users[i], gameSkin, "screen");
            Label score = new Label(scores[i], gameSkin, "screen");
            user.setFontScale(2);
            score.setFontScale(2);
            highscores.row();
            highscores.add(user);
            highscores.add(score);

        }


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

    public void fillTables() {
        scores[0] = "823";
        scores[1] = "734";
        scores[2] = "703";
        scores[3] = "634";
        scores[4] = "623";
        scores[5] = "597";
        scores[6] = "562";
        scores[7] = "532";
        scores[8] = "506";
        scores[9] = "497";

        users[0] = "Mike";
        users[1] = "Jim";
        users[2] = "Kate";
        users[3] = "Aaron";
        users[4] = "Nis";
        users[5] = "Sam";
        users[6] = "Tom";
        users[7] = "Greg";
        users[8] = "Lexi";
        users[9] = "Joe";

    }
}
