package com.schojcir.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.schojcir.entities.Pacman.IntendedDirection.*;

/**
 * Created by jkraj on 12/2/2017.
 */

public class Pacman implements GestureDetector.GestureListener{

    private final float SPEED_CONSTANT = 4f;

    private Array<Sprite> pacman_sprites;
    private Vector2 speed = new Vector2();
    private TiledMapTileLayer collisionLayer;
    private TextureAtlas pacmanSprites;
    private Array<Sprite> pacmanRight;
    private Array<Sprite> pacmanLeft;
    private Array<Sprite> pacmanUp;
    private Array<Sprite> pacmanDown;
    private Vector2 position = new Vector2();
    private float stateTime;
    private IntendedDirection intendedDirection = NONE;

    enum IntendedDirection{
        LEFT, RIGHT, UP, DOWN, NONE
    }

//    private Array<TextureAtlas.AtlasRegion> currentPacman;

    public Pacman(Sprite sprite, TiledMapTileLayer collisionLayer){
//        super(sprite);
        this.collisionLayer = collisionLayer;
        System.out.println("Tile Height: " + collisionLayer.getTileHeight() + " Tile Width: " + collisionLayer.getTileWidth());
        speed.x = 0;
        speed.y = 0;
//        setSize(1, 1);
//        setX(13.5f);
//        setY(31 - 24);
        position.x = 13.5f;
        position.y = 31 - 24;

        pacmanSprites = new TextureAtlas("spritesheet/pacman_spritesheet.txt");
        pacmanRight = pacmanSprites.createSprites("pacman_right");
        pacmanLeft = pacmanSprites.createSprites("pacman_left");
        pacmanUp = pacmanSprites.createSprites("pacman_up");
        pacmanDown = pacmanSprites.createSprites("pacman_down");
        for(int i = 0; i < pacmanRight.size; i++){
            pacmanRight.get(i).setSize(1, 1);
            pacmanLeft.get(i).setSize(1, 1);
            pacmanUp.get(i).setSize(1, 1);
            pacmanDown.get(i).setSize(1, 1);
        }
    }

    public void draw(Batch batch){
        update(Gdx.graphics.getDeltaTime());
        if(speed.x > 0){
            pacmanRight.get(0).setX(position.x);
            pacmanRight.get(0).setY(position.y);
            pacmanRight.get(0).draw(batch);
        }
        else if(speed.x < 0){
            pacmanLeft.get(0).setX(position.x);
            pacmanLeft.get(0).setY(position.y);
            pacmanLeft.get(0).draw(batch);
        }
        else if(speed.y > 0){
            pacmanUp.get(0).setX(position.x);
            pacmanUp.get(0).setY(position.y);
            pacmanUp.get(0).draw(batch);
        }
        else if(speed.y < 0){
            pacmanDown.get(0).setX(position.x);
            pacmanDown.get(0).setY(position.y);
            pacmanDown.get(0).draw(batch);
        }
        else{
            pacmanDown.get(1).setX(position.x);
            pacmanDown.get(1).setY(position.y);
            pacmanDown.get(1).draw(batch);
        }
    }

    public void dispose(){
        pacmanSprites.dispose();
    }

    public void update(float delta){

        int x = (int) (position.x);
        //might have to subtract 31
        int y = (int) (position.y);

        if(intendedDirection == RIGHT && collisionLayer.getCell((int) (x + 1.5), (int) (y + 0.5)) != null){
            speed.y = 0;
            speed.x = SPEED_CONSTANT;
        }
        else if(intendedDirection == LEFT && collisionLayer.getCell((int) (x + speed.x * delta), (int) (y + 0.5)) != null){
            speed.y = 0;
            speed.x = -SPEED_CONSTANT;
        }
        else if(intendedDirection == UP && collisionLayer.getCell((int) (x + 0.5), (int) (y +  speed.y * delta + 1)) != null){
            speed.x = 0;
            speed.y = -SPEED_CONSTANT;
        }
        else if(intendedDirection == DOWN && collisionLayer.getCell((int) (x + 0.5), (int) (y +  speed.y * delta)) != null){
            speed.x = 0;
            speed.y = SPEED_CONSTANT;
        }

        float oldX = position.x;
        float oldY = position.y;

        boolean collidedX = false;
        boolean collidedY = false;

//        setX(getX() + speed.x * delta);
        position.x += (speed.x * delta);

//        int x = (int) (position.x);
//        //might have to subtract 31
//        int y = (int) (position.y);
//        if(speed.x < 0){
////            collidedX = collisionLayer.getCell(x, (int) (y + 0.5)).getTile().getProperties().containsKey("blocked");
//            collidedX = collisionLayer.getCell(x, (int) (y + 0.5)) == null;
//        }
//        else if(speed.x > 0){
////            System.out.println("Tile ID: " + collisionLayer.getCell((int)position.x, (int)(position.y)).getTile().getId() + " Tile blocked: " + collisionLayer.getCell((int)position.x, (int)(position.y)).getTile().getProperties());
////            collidedX = collisionLayer.getCell(x + 1, (int)(y + 0.5)).getTile().getProperties().containsKey("blocked");
//            collidedX = collisionLayer.getCell(x + 1, (int)(y + 0.5)) == null;
//        }

        collidedX = collisionLayer.getCell((int) (x + 0.5), (int) (y + 0.5)) == null;


        if(collidedX){
//            if(speed.y )
            position.x = oldX;
            speed.x = 0;
        }

//        setY(getY() + speed.y * delta);
        position.y += (speed.y * delta);
//        if(speed.y < 0){
////            collidedY = collisionLayer.getCell((int)(x + 0.5), y).getTile().getProperties().containsKey("blocked");
//            collidedY = collisionLayer.getCell((int)(x + 0.5), y) == null;
//        }
//        else if(speed.y > 0){
////            collidedY = collisionLayer.getCell((int)(x + 0.5), y + 1).getTile().getProperties().containsKey("blocked");
//            collidedY = collisionLayer.getCell((int)(x + 0.5), y + 1) == null;
//        }

        collidedY = collisionLayer.getCell((int) (x + 0.5), (int) (y + 0.5)) == null;


        if(collidedY && speed.x == 0){
            position.y = oldY;
            speed.y = 0;
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
        if(Math.abs(velocityX) > Math.abs(velocityY)){
//            speed.y = 0;
            if(velocityX > 0){
//                speed.x = SPEED_CONSTANT;
                intendedDirection = RIGHT;
            }
            else{
//                speed.x = -SPEED_CONSTANT;
                intendedDirection = LEFT;
            }
        }
        else{
//            speed.x = 0;
            if(velocityY > 0){
//                speed.y = -SPEED_CONSTANT;
                intendedDirection = UP;
            }
            else{
//                speed.y = SPEED_CONSTANT;
                intendedDirection = DOWN;
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
