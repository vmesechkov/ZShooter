package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TileMapEditorScreen implements Screen {

	private int[][] tileMap;
	private static TileMapEditorScreen editor;

	private Vector2 screenSize;

	private ZBatch batch;
	private Stage stage;
	private ZShooter game;

	Button backButton;

	class Tile extends Image {
		int type;
		Tile(TextureRegion region, int tileType) {
			super(region);
			this.type = tileType;
		}
	}

	Tile currentTile;
	Tile tile;
	TileMap map;

	MyListener listener;

	Vector2 camera = new Vector2();

	public class MyGestureDetector extends GestureDetector {
		private Stage stage;
		public MyGestureDetector(GestureListener listener,Stage stage) {
			super(listener);
			this.stage = stage;
		}


		@Override
		public boolean keyDown(int keycode) {
			stage.keyDown(keycode);
			super.keyDown(keycode);
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			stage.keyUp(keycode);
			super.keyUp(keycode);
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			stage.keyTyped(character);
			super.keyTyped(character);
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			if (!stage.touchDown(screenX, screenY, pointer, button)) {
				super.touchDown(screenX, screenY, pointer, button);
			}
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			if (!stage.touchUp(screenX, screenY, pointer, button)) {
				super.touchUp(screenX, screenY, pointer, button);
			}
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			if (!stage.touchDragged(screenX, screenY, pointer)) {
				super.touchDragged(screenX, screenY, pointer);
			}

			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			stage.mouseMoved(screenX, screenY);
			super.mouseMoved(screenX, screenY);
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			stage.scrolled(amount);
			super.scrolled(amount);
			// TODO Auto-generated method stub
			return false;
		}
	}







	private TileMapEditorScreen(Game gameInstance) {
		tileMap = new int[100][100];

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {

				tileMap[i][j] = 0;

			}
		}
			this.game = (ZShooter)gameInstance;
		batch = (ZBatch)ZShooter.getBatch();

		stage = new Stage(ZShooter.getViewport(), batch);

		backButton = new TextButton("Back", GUI.getGUI().getTextButtonStyle());
		backButton.setSize( ZShooter.getScreenWidth() / 5f, ZShooter.getScreenHeight() / 5f);
		backButton.setPosition(ZShooter.getScreenWidth() - backButton.getWidth(), ZShooter.getScreenHeight() - backButton.getHeight());
		backButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(MainMenu.getMainMenu(game));
			}
		});
		stage.addActor(backButton);


		// Create the tile map
		Texture tileMapTexture = new Texture(Gdx.files.internal("data/Textures/textureSheet2_128.png"));
		map = new TileMap(tileMapTexture, 100, 100, ZShooter.WORLD_TILE_SIZE, null);
		map.generateRandomMap();

		final Texture textureSheet = new Texture(Gdx.files.internal("data/Textures/textureSheet2_128.png"));
		final TextureRegion[][] textures = TextureRegion.split(textureSheet, 128, 128);

		currentTile = new Tile(textures[0][0], 0);

		Table scrollTable = new Table();

		InputListener listener;
		for (int i = 0; i < textures[0].length - 1; i++) {
			tile = new Tile(textures[0][i], i);
			listener = new InputListener() {
				int type = tile.type;
				Drawable drawable = tile.getDrawable();
				@Override
				public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
//					currentTile.addAction(Actions.removeActor());
//					currentTile = new Tile(textures[0][1],1);
//					stage.addActor(currentTile);
//					Gdx.app.log("TileMap ", "Stage size: " + stage.getActors().size);
					currentTile.setDrawable(drawable);
					currentTile.type = type;
					Gdx.app.log("TileMap ", "CurrentTile type: " + currentTile.type);
				}
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }
			};
			tile.addListener(listener);
			scrollTable.add(tile);
			if (i < textures[0].length - 2) {
				scrollTable.row();
			}
		}

		final ScrollPane scroller = new ScrollPane(scrollTable);
		scroller.setSize(128, stage.getHeight());
		scroller.setPosition(0, 0);

//		final Table table = new Table();
//		//table.setFillParent(true);
//		table.add(scroller).fill().expand();

		stage.addActor(scroller);
		stage.addActor(currentTile);
	}

	class MyListener implements GestureDetector.GestureListener {

		float velX, velY;
		boolean flinging = false;

		@Override
		public boolean touchDown ( float x, float y, int pointer, int button){
			if (velX + velY != 0) {
				velX = 0;
				velY = 0;
			}
			return true;
		}

		Vector2 screenCoords = new Vector2();
		@Override
		public boolean tap ( float x, float y, int count, int button){
			screenCoords.set(x, y);
			screenCoords = ZShooter.getViewport().unproject(screenCoords);
			float posX = ((screenCoords.x - ZShooter.getScreenWidth() / 2) + camOffset.x) / 128;
			float posY = ((screenCoords.y - ZShooter.getScreenHeight() / 2) + camOffset.y) / 128;
			//int tileIndex = (int)(posY * map.getContent().length + posX);
			if (posX >= 0 && posX < map.getContent().length && posY >= 0 && posY < map.getContent()[0].length) {
				map.setTile((int)posX, (int)posY, (byte) currentTile.type);
				Gdx.app.log("TileEditor ", "Tile Set: " + posX + "," + posY);
			}
			return false;
		}

		@Override
		public boolean longPress ( float x, float y){
			return false;
		}

		@Override
		public boolean fling ( float velocityX, float velocityY, int button){
			Gdx.app.log("GestureDetectorTest", "fling " + velocityX + ", " + velocityY);
			flinging = true;
			velX = velocityX * 0.5f;
			velY = velocityY * 0.5f;
			return false;
		}

		@Override
		public boolean pan ( float x, float y, float deltaX, float deltaY){
			camera.add(-deltaX, deltaY);
			return false;
		}

		@Override
		public boolean panStop ( float x, float y, int pointer, int button){
			return false;
		}

		@Override
		public boolean zoom ( float initialDistance, float distance){
			return false;
		}

		@Override
		public boolean pinch (Vector2 initialPointer1, Vector2 initialPointer2, Vector2
			pointer1, Vector2 pointer2){
			return false;
		}

		@Override
		public void pinchStop () {

		}

		public void update() {
			if (flinging) {
				velX *= 0.98f;
				velY *= 0.98f;
				camera.add(-velX * Gdx.graphics.getDeltaTime(), velY * Gdx.graphics.getDeltaTime());
				if (Math.abs(velX) < 0.01f) velX = 0;
				if (Math.abs(velY) < 0.01f) velY = 0;
			}
		}

	}

	@Override
	public void show() {
		//Gdx.input.setInputProcessor(stage);
		//GestureDetector gestureDetector = new GestureDetector(20, 0.5f, 2, 0.15f, new CameraController());
		listener = new MyListener();

		MyGestureDetector gestureDetector1 = new MyGestureDetector(listener, stage);
		Gdx.input.setInputProcessor(gestureDetector1);
		// Catch the back key event to prevent pausing our application
		Gdx.input.setCatchBackKey(true);
	}

	Vector2 camOffset = new Vector2();
	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		listener.update();

		camOffset.set(camera);
		//camOffset.add(ZShooter.getScreenWidth(), ZShooter.getScreenHeight());
		batch.setCameraOffset(camOffset);

		batch.begin();
		batch.draw(map);
		batch.end();

		currentTile.setPosition(stage.getWidth() - currentTile.getWidth(), 0);

		// Update stage and draw it
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		// Switch to Main Menu on Back Key press
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
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

	}

	public static TileMapEditorScreen getTileMapEditor(Game game) {
		if (editor == null) {
			editor = new TileMapEditorScreen(game);
		}
		return editor;
	}
}
