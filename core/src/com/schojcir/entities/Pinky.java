package com.schojcir.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by mlscholl on 12/8/17.
 */

public class Pinky implements GestureDetector.GestureListener{

    private final float SPEED_CONSTANT = 4f;

    private Vector2 speed = new Vector2();
    private TiledMapTileLayer playerLayer;
    private TextureAtlas pinkySprites;
    private Array<Sprite> pinkyRight;
    private Array<Sprite> pinkyLeft;
    private Array<Sprite> pinkyUp;
    private Array<Sprite> pinkyDown;
    private Vector2 position = new Vector2();
    private int count = 0;

    public Pinky(TiledMapTileLayer playerLayer){
        this.playerLayer = playerLayer;
        System.out.println("Tile Height: " + playerLayer.getTileHeight() + " Tile Width: " + playerLayer.getTileWidth());

        speed.x = 4;
        speed.y = 0;

        position.x = 13.5f;
        position.y = 31 - 24;

        pinkySprites = new TextureAtlas("spritesheet/character_spritesheet.txt");
        pinkyRight = pinkySprites.createSprites("pinky_right");
        pinkyLeft = pinkySprites.createSprites("pinky_left");
        pinkyUp = pinkySprites.createSprites("pinky_up");
        pinkyDown = pinkySprites.createSprites("pinky_down");

        for(int i = 0; i < pinkyRight.size; i++) {
            pinkyRight.get(i).setSize(1, 1);
            pinkyLeft.get(i).setSize(1, 1);
            pinkyUp.get(i).setSize(1, 1);
            pinkyDown.get(i).setSize(1, 1);
        }
    }

    public void draw(Batch batch){
        update(Gdx.graphics.getDeltaTime());
        if(speed.x > 0){
            pinkyRight.get(0).setX(position.x);
            pinkyRight.get(0).setY(position.y);
            pinkyRight.get(0).draw(batch);
        }
        else if(speed.x < 0){
            pinkyLeft.get(0).setX(position.x);
            pinkyLeft.get(0).setY(position.y);
            pinkyLeft.get(0).draw(batch);
        }
        else if(speed.y > 0){
            pinkyUp.get(0).setX(position.x);
            pinkyUp.get(0).setY(position.y);
            pinkyUp.get(0).draw(batch);
        }
        else if(speed.y < 0){
            pinkyDown.get(0).setX(position.x);
            pinkyDown.get(0).setY(position.y);
            pinkyDown.get(0).draw(batch);
        }
        else{
            pinkyDown.get(1).setX(position.x);
            pinkyDown.get(1).setY(position.y);
            pinkyDown.get(1).draw(batch);
        }
    }

    public void dispose(){
        pinkySprites.dispose();
    }

    public void update(float delta){

        boolean collidedX = false;
        boolean collidedY = false;

        int x = (int) (position.x);
        //might have to subtract 31
        int y = (int) (position.y);

        if (speed.x < 0){
            collidedX = playerLayer.getCell(x, (int) (y)) == null;
        }
        else if(speed.x > 0){
            collidedX = playerLayer.getCell(x + 1, (int)(y)) == null;
        }

        if (!collidedX) {
            position.x += (speed.x * delta);
        }

        if (speed.y < 0){
            collidedY = playerLayer.getCell((int)(x), y) == null;
        }
        else if(speed.y > 0){
            collidedY = playerLayer.getCell((int)(x), y + 1) == null;
        }

        if (!collidedY){
            position.y += (speed.y * delta);
        }

        if (speed.x == SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x + 0.5), (int) (y + 1)) != null) {
                if (count % 33 == 0) {
                    speed.y = SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
            else if (playerLayer.getCell((int) (x + 0.5), (int) (y)) != null) {
                if (count % 23 == 0) {
                    speed.y = -SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
        }
        if (speed.y == SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x), (int) (y + 0.5)) != null) {
                if (count % 37 == 0) {
                    speed.x = -SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
            if (playerLayer.getCell((int) (x + 1), (int) (y + 0.5)) != null) {
                if (count % 22 == 0) {
                    speed.x = SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
        }

        if (speed.x == -SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x + 0.5), (int) (y + 1)) != null) {
                if (count % 38 == 0) {
                    speed.y = SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
            else if (playerLayer.getCell((int) (x + 0.5), (int) (y)) != null) {
                if (count % 21 == 0) {
                    speed.y = -SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
        }
        if (speed.y == -SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x), (int) (y + 0.5)) != null) {
                if (count % 31 == 0) {
                    speed.x = -SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
            if (playerLayer.getCell((int) (x + 1), (int) (y + 0.5)) != null) {
                if (count % 24 == 0) {
                    speed.x = SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
        }

        count++;
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
        return false;
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