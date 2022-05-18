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
    public float speed = 0.0f;
    public Point direction, null_point;
    float angle = 0;
    public static int width , height;

    private final TiledMapTileLayer collisionLayer;
    private final float  k, w_half, h_half,
                  tile_k_x, tile_k_y;
    public boolean isMove = false, finished = false;

    private static final int BARRIER_ID = 0, NOT_NONE_ID = 1, NONE_ID = -1;

    private boolean check_1 = false, check_2 = false, check_3 = false, dead;

    private final Point start_pos;

    private int collision_id;

    Animation moveAnimation, deadAnimation, happyAnimation;
    TextureRegion currentFrame;

    private float stateTime, countTime;
    Sound dead_sound;

    public Player(Texture img, Texture dead, Texture happy, Point pos, TiledMapTileLayer collisionLayer){

        dead_sound = Gdx.audio.newSound(Gdx.files.internal("sound/dead.m4a"));

        int FRAME_COLS = 6;
        int FRAME_ROWS = 1;

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

        this.collisionLayer = collisionLayer;

        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();
        float mapWidth = collisionLayer.getWidth() * tileWidth;
        float mapHeight = collisionLayer.getHeight() * tileHeight;

        float k_x = GameScreen.WIDTH / mapWidth;
        float k_y = GameScreen.HEIGHT / mapHeight;
        k = (k_x + k_y) / 2;

        start_pos = new Point(pos.getX() * tileWidth * k_x, pos.getY() * tileHeight * k_y);
        this.pos = new Point(start_pos);

        width = (int) (tileWidth * 2 * k_x);
        height = (int) (tileHeight * 2 * k_y);
        w_half = width / 2;
        h_half = height / 2;
        tile_k_x = tileWidth * k_x;
        tile_k_y = tileWidth * k_y;

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
        batch.draw(currentFrame, pos.getX(), pos.getY(), w_half, h_half, width,  height, 1, 1, angle);
    }

    public void reset(){
        pos.setPoint(start_pos);
        finished = false;
        check_1 = false; check_2 = false; check_3 = false;
    }

    public void setDirection( Point dir){ direction = dir;}

    public int collisionDetect(float x, float y){
        MapProperties cell =  collisionLayer.getCell((int) (x / tile_k_x), (int) (y /  tile_k_y)).getTile().getProperties();
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
        pos.add(direction.getX() * speed * k, direction.getY() * speed * k);
        float x = pos.getX();
        float y = pos.getY();
        float w_cos = (float) (w_half * Math.cos(angle));
        float w_sin = (float) (w_half * Math.sin(angle));
        float h_cos = (float) (h_half * Math.cos(angle));
        float h_sin = (float) (h_half * Math.sin(angle));
        collision_id = collisionDetect(x, y);
        if(collision_id == NONE_ID){
            collision_id = collisionDetect((- w_cos - h_sin + x + w_half), (- w_sin + h_cos + y + h_half));
        }
        if(collision_id == NONE_ID){
            collision_id = collisionDetect((w_cos - h_sin + x + w_half), (w_sin + h_cos + y + h_half));
        }
        if(collision_id == NONE_ID){
            collision_id = collisionDetect((w_cos + h_cos + x + w_half), (w_sin - h_cos + y + h_half));
        }
        if(collision_id == BARRIER_ID && !dead){ dead = true;countTime = stateTime; dead_sound.play();}
    }
}
