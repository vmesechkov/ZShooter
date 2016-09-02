package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ZShooter extends Game {

	private static ZBatch batch;
	private static Viewport viewport;
	private static Vector2 screenSize;
	static Game game;
	public static int WORLD_TILE_SIZE = 128;

	ZShooter() {
		game = this;
	}

	@Override
	public void create() {
		batch = new ZBatch();
		viewport = new FitViewport(1920, 1080);
		screenSize = new Vector2( viewport.getWorldWidth(), viewport.getWorldHeight());
		batch.setScreenSize(screenSize);

		setScreen(MainMenu.getMainMenu(this));
	}

	static int getScreenWidth() {
		return (int)screenSize.x;
	}

	static int getScreenHeight() {
		return (int)screenSize.x;
	}

	static Viewport getViewport() {
		return viewport;
	}

	static Batch getBatch() {
		return batch;
	}
}
