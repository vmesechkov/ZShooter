package com.mesetts.zshooter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class InGameScreen implements Screen {
	SpriteBatch batch;

	private Stage stage;
	Game game;

	Player player;
	PlayerController playerController;

	static Vector2 screenDrawOffset;
	int cameraX;
	int cameraY;
	static float screenWidth;
	static float screenHeight;

	TileMap map;
	FPSLogger fpsLogger = new FPSLogger();

	InGameScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();


		//Create a Stage and add TouchPad
		stage = new Stage(new FitViewport(1920, 1080), batch);
		Gdx.input.setInputProcessor(stage);

		//Get viewport sizes
		screenWidth = stage.getViewport().getWorldWidth();
		screenHeight = stage.getViewport().getWorldHeight();

		screenDrawOffset = new Vector2(screenWidth / 2, screenHeight / 2);

		player = new Player("data/legs_run_sheet_128.png", "data/torso_run_sheet_128.png");
        player.setX(192);
        player.setY(192);

		map = new TileMap(1000, 1000);

		playerController = new PlayerController(player, stage);

		GLProfiler.enable();
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
	public void render(float delta) {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        playerController.update(map);

		// Camera coordinates
		cameraX = player.getX();
		cameraY = player.getY();

        //batch.enableBlending();
        //batch.setProjectionMatrix(camera.combined);
        batch.begin();

		map.draw(batch, cameraX, cameraY);

		player.draw(batch, cameraX, cameraY);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		fpsLogger.log();
//		Gdx.app.log("Texture Bindings: ", Integer.toString(GLProfiler.textureBindings));
//		Gdx.app.log("Draw Calls: ", Integer.toString(GLProfiler.drawCalls));
		GLProfiler.reset();
	}


	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		player.dispose();
		playerController.dispose();
	}
}
