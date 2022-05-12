package com.demo.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player{

    public Point pos;
    public double speed = 0.0;
    public Point direction, null_point;
    float angle = 0;
    public static int width , height;

    private final TiledMapTileLayer collisonLayer;
    float tileWidth, tileHeight, mapWidth, mapHeight, k_x, k_y, k;
    public boolean isMove = false, finished = false;

    private static int FRAME_COLS;
    private static int FRAME_ROWS;

    private static final int BARRIER_ID = 0, NOT_NONE_ID = 1, NONE_ID = -1;

    private boolean check_1 = false, check_2 = false, check_3 = false, dead;

    private final Point start_pos;

    private int collision_id;

    Animation moveAnimation, deadAnimation, happyAnimation;
    TextureRegion currentFrame;

    private float stateTime, countTime;
    Sound dead_sound;

    public Player(Texture img, Texture dead, Texture happy, Point pos, TiledMapTileLayer collisonLayer){

        dead_sound = Gdx.audio.newSound(Gdx.files.internal("sound/dead.m4a"));

        FRAME_COLS = 6;
        FRAME_ROWS = 1;

        TextureRegion[][] tmp1 = TextureRegion.split(img, img.getWidth() / FRAME_COLS, img.getHeight() / FRAME_ROWS);
        TextureRegion[] moveFrames1 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                moveFrames1[index++] = tmp1[i][j];
            }
        }
        moveAnimation = new Animation(0.5f, moveFrames1);

        FRAME_COLS = 5;

        TextureRegion[][] tmp2 = TextureRegion.split(dead, dead.getWidth() / FRAME_COLS, dead.getHeight() / FRAME_ROWS);
        TextureRegion[] moveFrames2 = new TextureRegion[5 * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                moveFrames2[index++] = tmp2[i][j];
            }
        }
        deadAnimation = new Animation(0.5f, moveFrames2);

        FRAME_COLS = 2;

        TextureRegion[][] tmp3 = TextureRegion.split(happy, happy.getWidth() / FRAME_COLS, happy.getHeight() / FRAME_ROWS);
        TextureRegion[] moveFrames3 = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                moveFrames3[index++] = tmp3[i][j];
            }
        }
        happyAnimation = new Animation(0.5f, moveFrames3);

        stateTime = 0f;

        this.collisonLayer = collisonLayer;

        tileWidth = collisonLayer.getTileWidth();
        tileHeight = collisonLayer.getTileHeight();
        mapWidth = collisonLayer.getWidth() * tileWidth;
        mapHeight = collisonLayer.getHeight() * tileHeight;

        k_x = GameScreen.WIDTH / mapWidth;
        k_y = GameScreen.HEIGHT / mapHeight;
        k = (k_x + k_y) / 2;

        start_pos = new Point(pos.getX() * tileWidth * k_x, pos.getY() * tileHeight * k_y);
        this.pos = new Point(start_pos);

        width = (int) (tileWidth * 2 * k_x);
        height = (int) (tileHeight * 2 * k_y);

        direction = new Point(0, 0);
        null_point = new Point(0, 0);
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        if(dead){
            if(stateTime - countTime >= 1){
                dead = false;
                reset();
            }
            currentFrame = (TextureRegion) deadAnimation.getKeyFrame(stateTime, true);
        }
        else if (!isMove){
            currentFrame = (TextureRegion) moveAnimation.getKeyFrame(0);
        }
        else{
            currentFrame = (TextureRegion) moveAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, pos.getX(), pos.getY(), (float) width/2, (float) height/2, (float) width, (float) height, 1, 1, angle);
    }

    public void reset(){
        pos.setPoint(start_pos);
        finished = false;
        check_1 = false; check_2 = false; check_3 = false;
    }

    public void setDirection( Point dir){ direction = dir;}

    public int collisionDetect(float x, float y){
        MapProperties cell =  collisonLayer.getCell((int) (x / (tileWidth * k_x)), (int) (y /  (tileHeight * k_y ))).getTile().getProperties();
        if(cell.containsKey("barrier")){
            return BARRIER_ID;
        }
        else if(cell.containsKey("finish")){
            if(check_1 & check_2 & check_3){
                finished = true;
            }
            return NOT_NONE_ID;
        }
        else if(cell.containsKey("check_1")){
            check_1 = true; return NOT_NONE_ID; }
        else if(cell.containsKey("check_2")){
            check_2 = true; return NOT_NONE_ID; }
        else if(cell.containsKey("check_3")){
            check_3 = true; return NOT_NONE_ID; }
        return NONE_ID;
    }

    public void update() {
        if (dead){ angle = 0;speed = 0; }
        pos.add(direction.getX() * (float) speed* k, direction.getY() * (float) speed * k);
        collision_id = collisionDetect(pos.getX(), pos.getY());
        if(collision_id == NONE_ID){
            collision_id = collisionDetect((float) (-width / 2 * Math.cos(angle) - height / 2 * Math.sin(angle) + pos.getX() + width / 2),
                    (float) (-width / 2 * Math.sin(angle) + height / 2 * Math.cos(angle) + pos.getY() + height / 2));
        }
        if(collision_id == NONE_ID){
            collision_id = collisionDetect((float) (width / 2 * Math.cos(angle) - height / 2 * Math.sin(angle) + pos.getX() + width / 2),
                    (float) (width / 2 * Math.sin(angle) + height / 2 * Math.cos(angle) + pos.getY() + height / 2));
        }
        if(collision_id == NONE_ID){
            collision_id = collisionDetect((float) (width / 2 * Math.cos(angle) + height / 2 * Math.sin(angle) + pos.getX() + width / 2),
                    (float) (width / 2 * Math.sin(angle) - height / 2 * Math.cos(angle) + pos.getY() + height / 2));
        }
        if(collision_id == BARRIER_ID && !dead){ dead = true;countTime = stateTime; dead_sound.play();}
    }
}
