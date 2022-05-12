package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PauseScreen implements Screen{
    SpriteBatch batch;
    Main main;
    ImageButton resume_btn, restart_btn, menu_btn, maps_btn;
    Stage stage;
    Texture window, background;

    private static float btn_width, btn_height, distance, window_width, window_height;

    public PauseScreen(final Main main) {
        this.main = main;

        batch = new SpriteBatch();
        window = new Texture("end_game_window.png");
        background = new Texture("background_2.png");

        btn_width = GameScreen.WIDTH / 5;
        btn_height = GameScreen.HEIGHT / 6.5f;
        distance = GameScreen.HEIGHT / 30;
        float start_distance = GameScreen.HEIGHT / 25;
        window_width = GameScreen.WIDTH / 2;


        Texture Image_up = new Texture("btns/resume_up.png");
        TextureRegion myTextureRegion_up = new TextureRegion(Image_up);
        TextureRegionDrawable myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Texture Image_down = new Texture("btns/resume_down.png");
        TextureRegion myTextureRegion_down = new TextureRegion(Image_down);
        TextureRegionDrawable myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        resume_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        resume_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - btn_height - start_distance);
        resume_btn.setSize(btn_width, btn_height);

        Image_up = new Texture("btns/restart_up.png");
        myTextureRegion_up = new TextureRegion(Image_up);
        myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Image_down = new Texture("btns/restart_down.png");
        myTextureRegion_down = new TextureRegion(Image_down);
        myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        restart_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        restart_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - btn_height * 2 - start_distance - distance);
        restart_btn.setSize(btn_width, btn_height);

        Image_up = new Texture("btns/maps_up.png");
        myTextureRegion_up = new TextureRegion(Image_up);
        myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Image_down = new Texture("btns/maps_down.png");
        myTextureRegion_down = new TextureRegion(Image_down);
        myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        maps_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        maps_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - btn_height * 3 - start_distance - distance * 2);
        maps_btn.setSize(btn_width, btn_height);

        Image_up = new Texture("btns/menu_up.png");
        myTextureRegion_up = new TextureRegion(Image_up);
        myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Image_down = new Texture("btns/menu_down.png");
        myTextureRegion_down = new TextureRegion(Image_down);
        myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        menu_btn = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        menu_btn.setPosition(GameScreen.WIDTH / 2 - btn_width / 2, GameScreen.HEIGHT - btn_height * 4 - start_distance - distance * 3);
        menu_btn.setSize(btn_width, btn_height);

        stage = new Stage();
        stage.addActor(resume_btn);
        stage.addActor(restart_btn);
        stage.addActor(menu_btn);
        stage.addActor(maps_btn);

        resume_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.game_screen.resume = true;
                main.setScreen(main.game_screen);
            }
        });

        restart_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.setScreen(main.game_screen);
            }
        });

        menu_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.setScreen(main.main_menu);
            }
        });

        maps_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.setScreen(main.map_screen);
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(window, GameScreen.WIDTH / 2 - window_width / 2, 0, window_width, GameScreen.HEIGHT);
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
