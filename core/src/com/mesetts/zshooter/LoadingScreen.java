package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by EpsiloN on 8/23/2016.
 */
public class LoadingScreen implements Screen {

	private static Screen loadingScreen;			// Singleton reference to loading screen object

	private Game game;

	private LoadingScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		// Catch the back key event to prevent pausing our application
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			Gdx.app.log("Back button pressed.", "Switching to Main Menu screen");
			game.setScreen(MainMenu.getMainMenu(game));
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		// Singleton destroy
		loadingScreen = null;
	}

	public static Screen getLoadingScreen(Game game) {
		if (loadingScreen == null) {
			loadingScreen = new LoadingScreen(game);
		}
		return loadingScreen;
	}
}
