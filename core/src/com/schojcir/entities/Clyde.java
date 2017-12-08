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

public class Clyde implements GestureDetector.GestureListener{

    private final float SPEED_CONSTANT = 4f;

    private Vector2 speed = new Vector2();
    private TiledMapTileLayer playerLayer;
    private TextureAtlas clydeSprites;
    private Array<Sprite> clydeRight;
    private Array<Sprite> clydeLeft;
    private Array<Sprite> clydeUp;
    private Array<Sprite> clydeDown;
    private Vector2 position = new Vector2();
    private int count = 0;

    public Clyde(TiledMapTileLayer playerLayer){
        this.playerLayer = playerLayer;
        System.out.println("Tile Height: " + playerLayer.getTileHeight() + " Tile Width: " + playerLayer.getTileWidth());

        speed.x = 0;
        speed.y = 4;

        position.x = 13.5f;
        position.y = 31 - 24;

        clydeSprites = new TextureAtlas("spritesheet/character_spritesheet.txt");
        clydeRight = clydeSprites.createSprites("clyde_right");
        clydeLeft = clydeSprites.createSprites("clyde_left");
        clydeUp = clydeSprites.createSprites("clyde_up");
        clydeDown = clydeSprites.createSprites("clyde_down");

        for(int i = 0; i < clydeRight.size; i++) {
            clydeRight.get(i).setSize(1, 1);
            clydeLeft.get(i).setSize(1, 1);
            clydeUp.get(i).setSize(1, 1);
            clydeDown.get(i).setSize(1, 1);
        }
    }

    public void draw(Batch batch){
        update(Gdx.graphics.getDeltaTime());
        if(speed.x > 0){
            clydeRight.get(0).setX(position.x);
            clydeRight.get(0).setY(position.y);
            clydeRight.get(0).draw(batch);
        }
        else if(speed.x < 0){
            clydeLeft.get(0).setX(position.x);
            clydeLeft.get(0).setY(position.y);
            clydeLeft.get(0).draw(batch);
        }
        else if(speed.y > 0){
            clydeUp.get(0).setX(position.x);
            clydeUp.get(0).setY(position.y);
            clydeUp.get(0).draw(batch);
        }
        else if(speed.y < 0){
            clydeDown.get(0).setX(position.x);
            clydeDown.get(0).setY(position.y);
            clydeDown.get(0).draw(batch);
        }
        else{
            clydeDown.get(1).setX(position.x);
            clydeDown.get(1).setY(position.y);
            clydeDown.get(1).draw(batch);
        }
    }

    public void dispose(){
        clydeSprites.dispose();
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
                if (count % 35 == 0) {
                    speed.y = SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
            else if (playerLayer.getCell((int) (x + 0.5), (int) (y)) != null) {
                if (count % 24 == 0) {
                    speed.y = -SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
        }
        if (speed.y == SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x), (int) (y + 0.5)) != null) {
                if (count % 39 == 0) {
                    speed.x = -SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
            if (playerLayer.getCell((int) (x + 1), (int) (y + 0.5)) != null) {
                if (count % 28 == 0) {
                    speed.x = SPEED_CONSTANT;
                    speed.y = 0;
                }
            }
        }

        if (speed.x == -SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x + 0.5), (int) (y + 1)) != null) {
                if (count % 32 == 0) {
                    speed.y = SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
            else if (playerLayer.getCell((int) (x + 0.5), (int) (y)) != null) {
                if (count % 25 == 0) {
                    speed.y = -SPEED_CONSTANT;
                    speed.x = 0;
                }
            }
        }
        if (speed.y == -SPEED_CONSTANT) {
            if (playerLayer.getCell((int) (x), (int) (y + 0.5)) != null) {
                if (count % 36 == 0) {
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