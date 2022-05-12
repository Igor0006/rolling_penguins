package com.demo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Main extends Game {
	public String CurrentMap;
	public GameScreen game_screen;
	public MainMenuScreen main_menu;
	public GameEndScreen game_end_screen;
	public MapScreen map_screen;
	public PauseScreen pause_screen;
	public Sound btn_sound;
	
	@Override
	public void create () {
		btn_sound = Gdx.audio.newSound(Gdx.files.internal("sound/btn.mp3"));
		game_screen = new GameScreen(this);
		main_menu = new MainMenuScreen(this);
		game_end_screen = new GameEndScreen(this);
		map_screen = new MapScreen(this);
		pause_screen = new PauseScreen(this);

		setScreen(main_menu);
	}
}
