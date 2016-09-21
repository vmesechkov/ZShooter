package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.naming.Context;

public class ZShooter extends Game {

	private static ZBatch batch;
	private static Viewport viewport;
	public static float soundVolume;
	public static float musicVolume = 0.5f;
	private static Vector2 screenSize;
	public static AssetManager assets;
	private static Game game;
	public static int WORLD_TILE_SIZE = 128;

	private static Music music;

	public ZShooter() {
		game = this;
	}

	@Override
	public void create() {
		assets = new AssetManager();
		batch = new ZBatch();
		viewport = new FitViewport(1920, 1080);
		screenSize = new Vector2( viewport.getWorldWidth(), viewport.getWorldHeight());
		batch.setScreenSize(screenSize);
		Database.getOurInstance();

//		AudioManager audio = (AudioManager) getSystemService(context.AUDIO_SERVICE);
//		musicVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
		music = Gdx.audio.newMusic(Gdx.files.internal("data/testMusic.mp3"));
		music.play();
		music.setVolume(musicVolume);


		setScreen(LoadingScreen.getLoadingScreen(this));
	}

	public static int getScreenWidth() {
		return (int)screenSize.x;
	}

	public static int getScreenHeight() {
		return (int)screenSize.y;
	}

	public static Viewport getViewport() {
		return viewport;
	}

	public static ZBatch getBatch() {
		return batch;
	}

	public static Music getMusic() { return music; }

	public static float getMusicVolume() { return musicVolume; }

	public static float setMusicVolume(float volume) { return musicVolume = volume; }

	public static float getSoundVolume() { return soundVolume; }

	public static float setSoundVolume(float volume) { return soundVolume = volume; }

}
