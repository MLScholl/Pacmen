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

public class Blinky implements GestureDetector.GestureListener{

    private final float SPEED_CONSTANT = 4f;

    private Vector2 speed = new Vector2();
    private TiledMapTileLayer playerLayer;
    private TextureAtlas blinkySprites;
    private Array<Sprite> blinkyRight;
    private Array<Sprite> blinkyLeft;
    private Array<Sprite> blinkyUp;
    private Array<Sprite> blinkyDown;
    private Vector2 position = new Vector2();
    private int count = 0;

    public Blinky(TiledMapTileLayer playerLayer){
        this.playerLayer = playerLayer;
        System.out.println("Tile Height: " + playerLayer.getTileHeight() + " Tile Width: " + playerLayer.getTileWidth());

        speed.x = 0;
        speed.y = 4;

        position.x = 13.5f;
        position.y = 31 - 24;

        blinkySprites = new TextureAtlas("spritesheet/character_spritesheet.txt");
        blinkyRight = blinkySprites.createSprites("blinky_right");
        blinkyLeft = blinkySprites.createSprites("blinky_left");
        blinkyUp = blinkySprites.createSprites("blinky_up");
        blinkyDown = blinkySprites.createSprites("blinky_down");

        for(int i = 0; i < blinkyRight.size; i++) {
            blinkyRight.get(i).setSize(1, 1);
            blinkyLeft.get(i).setSize(1, 1);
            blinkyUp.get(i).setSize(1, 1);
            blinkyDown.get(i).setSize(1, 1);
        }
    }

    public void draw(Batch batch){
        update(Gdx.graphics.getDeltaTime());
        if(speed.x > 0){
            blinkyRight.get(0).setX(position.x);
            blinkyRight.get(0).setY(position.y);
            blinkyRight.get(0).draw(batch);
        }
        else if(speed.x < 0){
            blinkyLeft.get(0).setX(position.x);
            blinkyLeft.get(0).setY(position.y);
            blinkyLeft.get(0).draw(batch);
        }
        else if(speed.y > 0){
            blinkyUp.get(0).setX(position.x);
            blinkyUp.get(0).setY(position.y);
            blinkyUp.get(0).draw(batch);
        }
        else if(speed.y < 0){
            blinkyDown.get(0).setX(position.x);
            blinkyDown.get(0).setY(position.y);
            blinkyDown.get(0).draw(batch);
        }
        else{
            blinkyDown.get(1).setX(position.x);
            blinkyDown.get(1).setY(position.y);
            blinkyDown.get(1).draw(batch);
        }
    }

    public void dispose(){
        blinkySprites.dispose();
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
                if (count % 30 == 0) {
                    speed.y = SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
            else if (playerLayer.getCell((int) (x + 0.5), (int) (y)) != null) {
                if (count % 20 == 0) {
                    speed.y = -SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
        }
        if (speed.y == SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x), (int) (y + 0.5)) != null) {
                if (count % 30 == 0) {
                    speed.x = -SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
            if (playerLayer.getCell((int) (x + 1), (int) (y + 0.5)) != null) {
                if (count % 20 == 0) {
                    speed.x = SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
        }

        if (speed.x == -SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x + 0.5), (int) (y + 1)) != null) {
                if (count % 30 == 0) {
                    speed.y = SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
            else if (playerLayer.getCell((int) (x + 0.5), (int) (y)) != null) {
                if (count % 20 == 0) {
                    speed.y = -SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
        }
        if (speed.y == -SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x), (int) (y + 0.5)) != null) {
                if (count % 30 == 0) {
                    speed.x = -SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
            if (playerLayer.getCell((int) (x + 1), (int) (y + 0.5)) != null) {
                if (count % 20 == 0) {
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