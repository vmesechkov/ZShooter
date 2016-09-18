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
	private static float soundVolume = 0f;
	private static float musicVolume = 0f;
	private static Vector2 screenSize;
	public static AssetManager assets;
	static Game game;
	public static int WORLD_TILE_SIZE = 128;

	private static Music music;

	ZShooter() {
		game = this;
	}

	@Override
	public void create() {
		assets = new AssetManager();
		batch = new ZBatch();
		viewport = new FitViewport(1920, 1080);
		screenSize = new Vector2( viewport.getWorldWidth(), viewport.getWorldHeight());
		batch.setScreenSize(screenSize);

//		AudioManager audio = (AudioManager) getSystemService(context.AUDIO_SERVICE);
//		musicVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
		music = Gdx.audio.newMusic(Gdx.files.internal("data/testMusic.mp3"));
		music.play();
		music.setVolume(musicVolume);


		setScreen(LoadingScreen.getLoadingScreen(this));
	}

	static int getScreenWidth() {
		return (int)screenSize.x;
	}

	static int getScreenHeight() {
		return (int)screenSize.y;
	}

	static Viewport getViewport() {
		return viewport;
	}

	static Batch getBatch() {
		return batch;
	}

	static Music getMusic() { return music; }

	static float getMusicVolume() { return musicVolume; }

	static float setMusicVolume(float volume) { return musicVolume = volume; }

	static float getSoundVolume() { return soundVolume; }

	static float setSoundVolume(float volume) { return soundVolume = volume; }

}
