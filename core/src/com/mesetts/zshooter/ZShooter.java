package com.mesetts.zshooter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.Point;

import static com.badlogic.gdx.Gdx.app;

public class ZShooter implements ApplicationListener {
	SpriteBatch batch;

	private OrthographicCamera camera;
	private Stage stage;

	Player player;
	PlayerController playerController;

	static Vector2 screenDrawOffset;
	int cameraX;
	int cameraY;
	static float screenWidth;
	static float screenHeight;


	TileMap map;
	FPSLogger fpsLogger = new FPSLogger();

	@Override
	public void create () {
		batch = new SpriteBatch();

		//Create camera
		float aspectRatio = (float) Gdx.app.getGraphics().getWidth() / (float) Gdx.app.getGraphics().getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10f,  10f*aspectRatio);

		//Create a Stage and add TouchPad
		stage = new Stage(new FitViewport(1920,1080, camera), batch);
		Gdx.input.setInputProcessor(stage);

		//Get viewport sizes
		screenWidth = stage.getViewport().getWorldWidth();
		screenHeight = stage.getViewport().getWorldHeight();


		screenDrawOffset = new Vector2(screenWidth / 2, screenHeight / 2);

		player = new Player("data/legs_run_sheet_128.png", "data/torso_run_sheet_128.png");

		map = new TileMap(2000,2000);

		playerController = new PlayerController(player, stage);

		GLProfiler.enable();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		boolean hasPlayerMoved = playerController.update();
		// Camera coordinates
		cameraX = player.getX();
		cameraY = player.getY();

		//camera.update();

		//batch.enableBlending();
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();

		map.draw(batch, cameraX, cameraY);

		player.draw(batch, cameraX, cameraY);

		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		fpsLogger.log();
		Gdx.app.log("Texture Bindings: ", Integer.toString(GLProfiler.textureBindings));
		Gdx.app.log("Draw Calls: ", Integer.toString(GLProfiler.drawCalls));
		GLProfiler.reset();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		playerController.dispose();
	}
}
