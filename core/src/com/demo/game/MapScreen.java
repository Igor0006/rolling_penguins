package com.demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MapScreen implements Screen{
    SpriteBatch batch;
    Main main;
    ImageButton btn_left, btn_right, btn_start;
    Stage stage;
    Texture preview, background;
    int map_index = 0;
    static float preview_width, preview_height, array_size;
    final String[] map_pool = new String[] {"preview/map1_preview.png", "preview/map2_preview.png"};

    public MapScreen(final Main main) {
        this.main = main;

        batch = new SpriteBatch();
        background = new Texture("background_2.png");

        array_size = GameScreen.WIDTH / 20;
        preview_width = GameScreen.WIDTH - array_size * 2 - MainMenuScreen.distance * 2;
        preview_height = GameScreen.HEIGHT - MainMenuScreen.btn_height * 2;

        Texture Image_up = new Texture("btns/array_left_up.png");
        TextureRegion myTextureRegion_up = new TextureRegion(Image_up);
        TextureRegionDrawable myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Texture Image_down = new Texture("btns/array_left_down.png");
        TextureRegion myTextureRegion_down = new TextureRegion(Image_down);
        TextureRegionDrawable myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        btn_left = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        btn_left.setSize(array_size, array_size);
        btn_left.setPosition(0, GameScreen.HEIGHT / 2 - array_size / 2);

        Image_up = new Texture("btns/array_right_up.png");
        myTextureRegion_up = new TextureRegion(Image_up);
        myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Image_down = new Texture("btns/array_right_down.png");
        myTextureRegion_down = new TextureRegion(Image_down);
        myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        btn_right = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        btn_right.setSize(array_size, array_size);
        btn_right.setPosition(GameScreen.WIDTH - array_size, GameScreen.HEIGHT / 2 - array_size / 2);

        Image_up = new Texture("btns/start_up.png");
        myTextureRegion_up = new TextureRegion(Image_up);
        myTexRegionDrawable_up = new TextureRegionDrawable(myTextureRegion_up);
        Image_down = new Texture("btns/start_down.png");
        myTextureRegion_down = new TextureRegion(Image_down);
        myTexRegionDrawable_down = new TextureRegionDrawable(myTextureRegion_down);
        btn_start = new ImageButton(myTexRegionDrawable_up, myTexRegionDrawable_down);
        btn_start.setSize(MainMenuScreen.btn_width, MainMenuScreen.btn_height);
        btn_start.setPosition(GameScreen.WIDTH / 2 - MainMenuScreen.btn_width / 2, MainMenuScreen.distance);


        stage = new Stage();
        stage.addActor(btn_left);
        stage.addActor(btn_right);
        stage.addActor(btn_start);

        btn_right.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                map_index -= 1;
                if(map_index < 0){
                    map_index = map_pool.length - 1 - map_index;
                }
                if (map_index == map_pool.length){
                   map_index = map_pool.length - 1;
                }
                preview = new Texture(map_pool[map_index]);
            }
        });

        btn_left.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                map_index += 1;
                if (map_index == map_pool.length){
                    map_index = 0;
                }
                preview = new Texture(map_pool[map_index]);
            }
        });

        btn_start.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                main.btn_sound.play();
                main.CurrentMap = map_pool[map_index].substring(8, 12) + ".tmx";
                main.setScreen(main.game_screen);
            }
        });
    }

    @Override
    public void show() {
        preview = new Texture(map_pool[map_index]);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, GameScreen.WIDTH, GameScreen.HEIGHT);
        batch.draw(preview, array_size + MainMenuScreen.distance, MainMenuScreen.btn_height + MainMenuScreen.distance, preview_width, preview_height);
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
