package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 * Created by EpsiloN on 8/23/2016.
 */
public class LoadingScreen implements Screen {

	private static Screen loadingScreen;			// Singleton reference to loading screen object
	private Texture background;
	private Game game;
	private float progress;
	private Stage stage;
	private LoadingScreen(Game game) {

		background = new Texture("data/Textures/LoadingScreenFIXED2.jpg");
		this.game = game;
		stage = new Stage(ZShooter.getViewport(),ZShooter.getBatch());
		this.progress = 0f;
		queueAssets();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f,0f,0f,0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ZShooter.getBatch().begin();
		ZShooter.getBatch().draw(background,0,0);
		ZShooter.getBatch().end();
		update(delta);
		stage.act();
		stage.draw();
		if(ZShooter.assets.update()){
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
		stage.dispose();
	}

	public static Screen getLoadingScreen(ZShooter game) {
		if (loadingScreen == null) {
			loadingScreen = new LoadingScreen(game);
		}
		return loadingScreen;
	}

	private void update(float delta){
		progress = ZShooter.assets.getProgress();

	}


	private void queueAssets(){
		ZShooter.assets.load("gdx-skins-master/Styles + Skins + Fonts/skin/star-soldier-ui.atlas", TextureAtlas.class);
		ZShooter.assets.load("gdx-skins-master/Styles + Skins + Fonts/skin/font-export.fnt", BitmapFont.class);
		ZShooter.assets.load("gdx-skins-master/Styles + Skins + Fonts/skin/font-title-export.fnt",BitmapFont.class);
		ZShooter.assets.load("data/Textures/Walking zombies for the introscreens.jpg",Texture.class);
		ZShooter.assets.load("data/Textures/LoadingScreenZombies.jpg",Texture.class);
		ZShooter.assets.load("data/Textures/Options Screen.jpg",Texture.class);
		ZShooter.assets.load("data/Textures/Zombie head.jpg",Texture.class);
		ZShooter.assets.load("data/Textures/ZSHOOTER BACKGROUND.jpg",Texture.class);
		ZShooter.assets.load("data/bullet.png",Texture.class);
		ZShooter.assets.load("data/touchBackground.png",Texture.class);
		ZShooter.assets.load("data/touchKnob.png",Texture.class);
		ZShooter.assets.load("data/Textures/textureSheet2_128.png",Texture.class);
		ZShooter.assets.load("data/Textures/LoadingScreenFIXED2.jpg",Texture.class);
	}
}
