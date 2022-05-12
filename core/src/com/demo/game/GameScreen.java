package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class GameScreen implements Screen {
    public SpriteBatch batch;
    public static int WIDTH, HEIGHT;

    Joystick joy_1, joy_2;
    Player player_1, player_2;
    Main main;

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;

    Stage stage;
    ImageButton btn_pause;

    Music music;

    InputProcessor MainInput;
    InputMultiplexer multiplexer;

    public boolean resume = false;

    GameScreen(final Main main) {
        this.main = main;

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/main_theme.mp3"));
        music.setLooping(true);

        batch = new SpriteBatch();
        float pause_btn_width = WIDTH / 15;

        Texture Image = new Texture("btns/pause.png");
        TextureRegion myTextureRegion = new TextureRegion(Image);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        btn_pause = new ImageButton(myTexRegionDrawable);
        btn_pause.setSize(pause_btn_width, pause_btn_width);
        btn_pause.setPosition(WIDTH / 2 - pause_btn_width / 2, 0);
        stage = new Stage();
        stage.addActor(btn_pause);

        MainInput = (new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) { return false; }
            @Override
            public boolean keyUp(int keycode) { return false; }
            @Override
            public boolean keyTyped(char character) { return false; }
            @Override
            public boolean mouseMoved(int screenX, int screenY) { return false; }
            @Override
            public boolean scrolled(float amountX, float amountY) { return false; }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = HEIGHT - screenY;
                touch_adapt(screenX, screenY, true, pointer);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                screenY = HEIGHT - screenY;
                touch_adapt(screenX, screenY, false, pointer);
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                screenY = HEIGHT - screenY;
                touch_adapt(screenX, screenY, true, pointer);
                return false;
            }
        });
        btn_pause.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.setScreen(main.pause_screen);
            }
        });

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(MainInput);

    }

    @Override
    public void show() {
        if(!music.isPlaying()){
            main.main_menu.music.stop();
            music.play();
        }

        if(!resume){load("maps/" + main.CurrentMap);}
        else{resume = false;}
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        gameUpdate();

        renderer.setView(camera);
        renderer.render();

        batch.begin();
        gameRender(batch);
        batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        //map.dispose();
        //batch.dispose();
    }

    public void gameUpdate(){
        player_1.setDirection(joy_1.getDirection());
        player_1.update();

        if(player_1.speed != 0.0 && !player_1.isMove) { player_1.speed -= 0.02; }
        if(player_1.speed < 0.0){ player_1.speed = 0.0; }


        player_2.setDirection(joy_2.getDirection());
        player_2.update();

        if(player_2.speed != 0.0 && !player_2.isMove) { player_2.speed -= 0.02; }
        if(player_2.speed < 0.0){ player_2.speed = 0.0; }

        if(player_1.finished || player_2.finished){
            if(player_1.finished){
                main.game_end_screen.winner = player_1;
            }
            else if(player_2.finished){
                main.game_end_screen.winner = player_2;
            }
            player_1.reset();
            player_2.reset();
            main.setScreen(main.game_end_screen);
        }
    }

    public void gameRender(SpriteBatch batch){
        player_1.draw(batch);
        joy_1.draw(batch);

        player_2.draw(batch);
        joy_2.draw(batch);
    }

    public void load(String name){
        map = new TmxMapLoader().load(name);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,16 * 50, 16 * 40);
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map);

        player_1 = new Player(new Texture("pinguin_1.png"), new Texture("dead_1.png"), new Texture("happy_1.png"),
                new Point(25, 34), (TiledMapTileLayer) map.getLayers().get(0));
        player_2 = new Player(new Texture("pinguin_2.png"), new Texture("dead_2.png"), new Texture("happy_2.png"),
                new Point(25, 36), (TiledMapTileLayer) map.getLayers().get(0));
        joy_1 = new Joystick(new Texture("circle_1.png"), new Texture("stick_1.png"),
                new Point(WIDTH - ((HEIGHT / 3) / 2 + (HEIGHT / 3) / 4), (HEIGHT / 3) / 2 + (HEIGHT / 3) / 4), WIDTH / 6, player_1);
        joy_2 = new Joystick(new Texture("circle_2.png"), new Texture("stick_2.png"),
                new Point(((HEIGHT / 3) / 2 + (HEIGHT / 3) / 4), (HEIGHT / 3) / 2 + (HEIGHT / 3) / 4), WIDTH / 6, player_2);
    }

    public void touch_adapt(float x, float y, boolean isDownTouch, int pointer){
        for(int i = 0; i < 3; i++){
            joy_1.update(x, y, isDownTouch, pointer);
            joy_2.update(x, y, isDownTouch, pointer);
        }
    }
}
