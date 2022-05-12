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

public class MainMenuScreen implements Screen{
    SpriteBatch batch;
    Main main;
    ImageButton play_btn, exit_btn, settings_btn;
    Stage stage;
    Texture background;
    public Music music;
    public static float btn_width, btn_height, distance;

    public MainMenuScreen(final Main main) {
        this.main = main;

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/movingrightalong.wav"));
        music.setLooping(true);

        batch = new SpriteBatch();
        background = new Texture("menu_background.png");

        btn_width = GameScreen.WIDTH / 5;
        btn_height = GameScreen.HEIGHT / 6.5f;
        distance = GameScreen.HEIGHT / 30;

        Texture Image_up = new Texture("btns/play_up.png");
        TextureRegion myTextureRegion_up = new TextureRegion(Image_up);
        TextureRegionDrawable myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Texture Image_down = new Texture("btns/play_down.png");
        TextureRegion myTextureRegion_down = new TextureRegion(Image_down);
        TextureRegionDrawable myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);

        play_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        play_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - btn_height - distance * 2);
        play_btn.setSize(btn_width, btn_height);

        Image_up = new Texture("btns/settings_up.png");
        myTextureRegion_up = new TextureRegion(Image_up);
        myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Image_down = new Texture("btns/settings_down.png");
        myTextureRegion_down = new TextureRegion(Image_down);
        myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        settings_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        settings_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - btn_height * 2 - distance * 2);
        settings_btn.setSize(btn_width, btn_height);

        Image_up = new Texture("btns/exit_up.png");
        myTextureRegion_up = new TextureRegion(Image_up);
        myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Image_down = new Texture("btns/exit_down.png");
        myTextureRegion_down = new TextureRegion(Image_down);
        myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        exit_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        exit_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - btn_height * 3 - distance * 2);
        exit_btn.setSize(btn_width, btn_height);

        stage = new Stage();
        stage.addActor(play_btn);
        stage.addActor(settings_btn);
        stage.addActor(exit_btn);

        play_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.setScreen(main.map_screen);
            }
        });

        exit_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {
        main.game_screen.music.stop();

        music.play();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, GameScreen.WIDTH, GameScreen.HEIGHT);
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
    public void dispose() { }
}
