package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameEndScreen implements Screen{
    SpriteBatch batch;
    Main main;
    ImageButton menu_btn, restart_btn;
    Stage stage;
    Texture window, background;
    Music music;
    private static float btn_width, btn_height, window_width, width;

    TextureRegion currentFrame;

    private float stateTime;

    public Player winner;

    public GameEndScreen(final Main main) {
        this.main = main;

        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/victory.mp3"));

        btn_width = GameScreen.WIDTH / 5;
        btn_height = GameScreen.HEIGHT / 6.5f;
        float start_distance = GameScreen.HEIGHT / 20;
        window_width = GameScreen.WIDTH / 2;
        width = GameScreen.WIDTH / 4;

        Texture Image_up = new Texture("btns/menu_up.png");
        TextureRegion myTextureRegion_up = new TextureRegion(Image_up);
        TextureRegionDrawable myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Texture Image_down = new Texture("btns/menu_down.png");
        TextureRegion myTextureRegion_down = new TextureRegion(Image_down);
        TextureRegionDrawable myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        menu_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        menu_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - width - start_distance - btn_height * 3);
        menu_btn.setSize(btn_width, btn_height);

        Image_up = new Texture("btns/restart_up.png");
        myTextureRegion_up = new TextureRegion(Image_up);
        myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Image_down = new Texture("btns/restart_down.png");
        myTextureRegion_down = new TextureRegion(Image_down);
        myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        restart_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        restart_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - width - btn_height * 2 - start_distance);
        restart_btn.setSize(btn_width, btn_height);

        window = new Texture("end_game_window.png");
        background = new Texture("background_2.png");

        stage = new Stage();
        stage.addActor(menu_btn);
        stage.addActor(restart_btn);

        menu_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.setScreen(main.main_menu);
            }
        });

        restart_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.setScreen(main.game_screen);
            }
        });
    }

    @Override
    public void show() {
        music.play();
        if(music.isPlaying()){
            main.game_screen.music.pause();
        }
        else{
            main.game_screen.music.play();
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(window, GameScreen.WIDTH / 2 - window_width / 2, 0, window_width, GameScreen.HEIGHT);
        batch.draw(background, 0, 0, GameScreen.WIDTH, GameScreen.HEIGHT);

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) winner.happyAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, window_width - width / 2, GameScreen.HEIGHT - width - btn_height,  width,  width);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() {stage.dispose(); }
}
