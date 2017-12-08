package com.schojcir.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.schojcir.pacmen.Pacmen;

import static com.schojcir.pacmen.Pacmen.gameSkin;

/**
 * Created by mlscholl on 12/6/17.
 */

public class TitleScreen implements Screen {

    private Stage stage;
    private Pacmen game;

    public TitleScreen(Pacmen aGame) {

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

        booth.setBackground("booth");
        root.add(booth).fillX().fillY().expandX().expandY();

        Label label = new Label("Pacmen Arcade", gameSkin, "title");
        booth.add(label).padTop(75);

        booth.row();
        label = new Label("INSERT COIN(S)\nTO CONTINUE\n7", gameSkin, "screen");
        label.setFontScale(2);
        label.setAlignment(Align.center);
        booth.add(label).padTop(500.0f);


        booth.row().expandX();
        Table table = new Table();
        //table.setDebug(true);

        booth.add(table).expandY().bottom();
        Stack stack = new Stack();
        table.add(stack);

        Image image = new Image(gameSkin, "button-bg");
        image.setSize(200,200);
        stack.add(image);

        Button button = new Button(gameSkin);
        button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Play(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        Container container = new Container(button);
        container.size(150,150);
        container.center();
        stack.add(container);

        label = new Label("Start", gameSkin);
        label.setScale(2);
        table.add(label).padLeft(10.0f);

        booth.row();
        Table bottom = new Table();
        booth.add(bottom).padBottom(20.0f);

        stack = new Stack();
        bottom.add(stack);

        image = new Image(gameSkin, "joystick-bg");
        stack.add(image);

        final Touchpad touchpad = new Touchpad(0, gameSkin, "red");
        stack.add(touchpad);

        //change joystick image based on touchpad position
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                int align = 0;

                if (touchpad.getKnobPercentX() < -.25f) {
                    align = Align.left;
                } else if (touchpad.getKnobPercentX() > .25f) {
                    align = Align.right;
                }

                if (touchpad.getKnobPercentY() < -.25f) {
                    align += Align.bottom;
                } else if (touchpad.getKnobPercentY() > .25f) {
                    align += Align.top;
                }

                switch(align) {
                    case Align.left:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-l-red");
                        break;
                    case Align.topLeft:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-ul-red");
                        break;
                    case Align.top:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-u-red");
                        break;
                    case Align.topRight:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-ur-red");
                        break;
                    case Align.right:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-r-red");
                        break;
                    case Align.bottomRight:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-dr-red");
                        break;
                    case Align.bottom:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-d-red");
                        break;
                    case Align.bottomLeft:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-dl-red");
                        break;
                    default:
                        touchpad.getStyle().background = gameSkin.getDrawable("joystick-red");
                }
            }
        });

        table = new Table();
        bottom.add(table).padLeft(10.0f).padRight(10.0f);

        stack = new Stack();
        table.add(stack).padLeft(10.0f).padRight(10.0f);

        image = new Image(gameSkin, "button-bg");
        stack.add(image);

        button = new Button(gameSkin, "red");
        button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HighScoreScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        container = new Container(button);
        container.size(150,150);
        container.center();
        stack.add(container);

        stack = new Stack();
        table.add(stack).padLeft(10.0f).padRight(10.0f);

        image = new Image(gameSkin, "button-bg");
        stack.add(image);

        button = new Button(gameSkin, "blue");

        container = new Container(button);
        button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingsScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        container.size(150,150);
        container.center();
        stack.add(container);

        stack = new Stack();
        table.add(stack).padLeft(10.0f).padRight(10.0f);

        image = new Image(gameSkin, "button-bg");
        stack.add(image);

        button = new Button(gameSkin);
        button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        container = new Container(button);
        container.size(150,150);
        container.center();
        stack.add(container);

        table.row();
        label = new Label("HighScores", gameSkin);
        label.setScale(2);
        table.add(label).padLeft(10.0f).padRight(10.0f);

        label = new Label("Settings", gameSkin);
        label.setScale(2);
        table.add(label).padLeft(10.0f).padRight(10.0f);

        label = new Label("Quit", gameSkin);
        label.setScale(2);
        table.add(label).padLeft(10.0f).padRight(10.0f);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

        gameSkin.dispose();
        stage.dispose();
    }
}
