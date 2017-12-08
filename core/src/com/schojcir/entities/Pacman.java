package com.schojcir.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.schojcir.pacmen.Pacmen;
import com.schojcir.screens.TitleScreen;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by jkraj on 12/2/2017.
 */

public class Pacman implements GestureDetector.GestureListener{

    private final float SPEED_CONSTANT = 4f;

    private Array<Sprite> pacman_sprites;
    private Vector2 speed = new Vector2();
    private TiledMapTileLayer playerLayer;
    private TextureAtlas pacmanSprites;

    private Animation<TextureRegion> pacmanRight;
    private Animation<TextureRegion> pacmanLeft;
    private Animation<TextureRegion> pacmanUp;
    private Animation<TextureRegion> pacmanDown;
    private Vector2 position = new Vector2();

    private BitmapFont font;

    private TiledMapTile emptyTile;

    private int score = 0;

    private float stateTime = 0f;

    private Pacmen game;

    public Pacman(TiledMapTileLayer playerLayer, Pacmen game){

        this.game = game;

        this.playerLayer = playerLayer;
        System.out.println("Tile Height: " + playerLayer.getTileHeight() + " Tile Width: " + playerLayer.getTileWidth());
        speed.x = 0;
        speed.y = 0;

        font = new BitmapFont();
        font.getData().setScale(1/16f);

        position.x = 13.5f;
        position.y = 31 - 24;
        emptyTile = playerLayer.getCell((int) position.x, (int) position.y).getTile();

        pacmanSprites = new TextureAtlas("spritesheet/pacman_spritesheet.txt");

        pacmanRight = new Animation<TextureRegion>(0.5f, pacmanSprites.findRegions("pacman_right"), Animation.PlayMode.LOOP);
        pacmanLeft = new Animation<TextureRegion>(0.5f, pacmanSprites.findRegions("pacman_left"), Animation.PlayMode.LOOP);
        pacmanUp = new Animation<TextureRegion>(0.5f, pacmanSprites.findRegions("pacman_up"), Animation.PlayMode.LOOP);
        pacmanDown = new Animation<TextureRegion>(0.5f, pacmanSprites.findRegions("pacman_down"), Animation.PlayMode.LOOP);
    }

    public void draw(Batch batch){
        update(Gdx.graphics.getDeltaTime());
        TextureRegion currentFrame = null;
        stateTime += Gdx.graphics.getDeltaTime();
        if(speed.x > 0){
            currentFrame = pacmanRight.getKeyFrame(stateTime, true);
        }
        else if(speed.x < 0){
            currentFrame = pacmanLeft.getKeyFrame(stateTime, true);
        }
        else if(speed.y > 0){
            currentFrame = pacmanUp.getKeyFrame(stateTime, true);
        }
        else if(speed.y < 0){
            currentFrame = pacmanDown.getKeyFrame(stateTime, true);
        }
        else{
            currentFrame = pacmanRight.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, position.x, position.y, 1, 1);
        font.draw(batch,Integer.toString(score), 0, 33f);
    }

    public void dispose(){
        pacmanSprites.dispose();
        font.dispose();
    }

    private void update(float delta){

        boolean collidedX = false;
        boolean collidedY = false;

        int x = (int) (position.x);
        //might have to subtract 31
        int y = (int) (position.y);
        if(speed.x < 0){
            collidedX = playerLayer.getCell(x, y) == null;
        }
        else if(speed.x > 0){
            collidedX = playerLayer.getCell(x + 1, y) == null;
        }

        if(!collidedX){
            position.x += (speed.x * delta);
        }

        if(speed.y < 0){
            collidedY = playerLayer.getCell(x, y) == null;
        }
        else if(speed.y > 0){
            collidedY = playerLayer.getCell(x, y + 1) == null;
        }

        if(!collidedY){
            position.y += (speed.y * delta);
        }

        TiledMapTileLayer.Cell cell = playerLayer.getCell((int) position.x, (int) position.y);

        if(cell != null && cell.getTile().getId() == 94){
            score += 1;
            cell.setTile(emptyTile);
        }

        if(score >= 244){
//            final String[] name = new String[1];
//            Gdx.input.getTextInput(new Input.TextInputListener() {
//                @Override
//                public void input(String text) {
//                    name[0] = text;
//                }
//
//                @Override
//                public void canceled() {
//
//                }
//            }, "Enter your name", "", "");
            Preferences prefs = Gdx.app.getPreferences("high_score");
            prefs.putInteger("score", score);
            prefs.putString("name", "Player");
            game.setScreen(new TitleScreen(game));
        }

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        int x = (int) (position.x);
        //might have to subtract 31
        int y = (int) (position.y);
        if(Math.abs(velocityX) > Math.abs(velocityY)){
            if(velocityX > 0){
                if(playerLayer.getCell(x + 1, (int) (y + 0.5)) != null){
                    speed.x = SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
            else{
                if(playerLayer.getCell(x, (int) (y + 0.5)) != null){
                    speed.x = -SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
        }
        else{
            if(velocityY > 0){
                if(playerLayer.getCell((int) (x + 0.5), y) != null){
                    speed.y = -SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
            else{
                if(playerLayer.getCell((int) (x + 0.5), y + 1) != null){
                    speed.y = SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
        }

        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}
