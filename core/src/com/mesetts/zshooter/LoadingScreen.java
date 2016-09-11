package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;



/**
 * Created by EpsiloN on 8/23/2016.
 */
public class LoadingScreen extends ScreenAdapter {

	private static final float PROGRESS_BAR_WIDTH = ZShooter.getScreenWidth()/2f;
	private static final float PROGRESS_BAR_HEIGHT = ZShooter.getScreenHeight()/10f;
	private static Screen loadingScreen;			// Singleton reference to loading screen object

	private ZShooter game;
	private ShapeRenderer shapeRenderer;
	private Label titleLoadingScreen;
	private float progress;

	private LoadingScreen(ZShooter game) {
		shapeRenderer = new ShapeRenderer();

		this.game = game;

		this.progress = 0f;
		queueAssets();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(0f,0f,0f,0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);

		if(ZShooter.assets.update()){
			game.setScreen(MainMenu.getMainMenu(game));
		}


	}

	@Override
	public void resize(int width, int height) {
		ZShooter.getViewport().update(width,height);
		ZShooter.getBatch().setProjectionMatrix(ZShooter.getViewport().getCamera().combined);
		shapeRenderer.setProjectionMatrix(ZShooter.getViewport().getCamera().combined);
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

	public static Screen getLoadingScreen(ZShooter game) {
		if (loadingScreen == null) {
			loadingScreen = new LoadingScreen(game);
		}
		return loadingScreen;
	}

	private void update(float delta){
		progress = ZShooter.assets.getProgress();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect((ZShooter.getScreenWidth() - PROGRESS_BAR_WIDTH)/2f,
							(ZShooter.getScreenHeight() - PROGRESS_BAR_HEIGHT)/2f,
								PROGRESS_BAR_WIDTH * progress,
								PROGRESS_BAR_HEIGHT);
		shapeRenderer.end();
	}


	private void queueAssets(){
		ZShooter.assets.load("gdx-skins-master/Styles + Skins + Fonts/skin/star-soldier-ui.atlas", TextureAtlas.class);
		ZShooter.assets.load("gdx-skins-master/Styles + Skins + Fonts/skin/font-export.fnt", BitmapFont.class);
		ZShooter.assets.load("gdx-skins-master/Styles + Skins + Fonts/skin/font-title-export.fnt",BitmapFont.class);
	}
}
