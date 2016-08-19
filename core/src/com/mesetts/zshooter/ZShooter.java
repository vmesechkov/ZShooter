package com.mesetts.zshooter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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


	Tile tile1;
	Tile tile2;
	Tile tile3;
	Tile tile4;

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

		tile1 = new Tile("data/Textures/asphalt1.png");
		tile1.x = 0;
		tile1.y = 0;
		tile2 = new Tile("data/Textures/asphalt1.png");
		tile2.x = 64;
		tile2.y = 0;
		tile3 = new Tile("data/Textures/asphalt1.png");
		tile3.x = 0;
		tile3.y = 64;
		tile4 = new Tile("data/Textures/asphalt1.png");
		tile4.x = 64;
		tile4.y = 64;

		playerController = new PlayerController(player, stage);
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

		batch.draw(tile1.tileTex, tile1.x - cameraX + (int)screenDrawOffset.x, tile1.y - cameraY + (int)screenDrawOffset.y);
		batch.draw(tile2.tileTex, tile2.x - cameraX + (int)screenDrawOffset.x, tile2.y - cameraY + (int)screenDrawOffset.y);
		batch.draw(tile3.tileTex, tile3.x - cameraX + (int)screenDrawOffset.x, tile3.y - cameraY + (int)screenDrawOffset.y);
		batch.draw(tile4.tileTex, tile4.x - cameraX + (int)screenDrawOffset.x, tile4.y - cameraY + (int)screenDrawOffset.y);

		player.draw(batch, cameraX, cameraY);

		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
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
