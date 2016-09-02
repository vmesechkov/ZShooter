package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;

import box2dLight.PointLight;
import box2dLight.RayHandler;


public class InGameScreen implements Screen {

	private static Screen inGameScreen;

	private Vector2 screenSize;

	private ZBatch batch;
	private Stage stage;
	private Game game;

	private Vector2 camera;

	World world;
	Player player;
	PlayerController playerController;
	TileMap map;

// Debugging info:
	FPSLogger fpsLogger = new FPSLogger();	// Outputs FPS in Log

	protected Label label;					// Outputs FPS on Screen
	protected BitmapFont font;				// A blank font (default?)
	protected StringBuilder stringBuilder;	// Text on screen

	private InGameScreen(Game game) {
		this.game = game;
		batch = (ZBatch)ZShooter.getBatch();

		screenSize = new Vector2();
		camera = new Vector2();

		//Create a Stage and add TouchPad
		stage = new Stage(ZShooter.getViewport(), batch);

		//Get viewport sizes
		screenSize.x = stage.getViewport().getWorldWidth();
		screenSize.y = stage.getViewport().getWorldHeight();

		// Init Physics
		world = new World(new Vector2(), true);


		// Create and init Animations for the player
		TextureRegion[] legsWalkFrames = arrangeFrames(new Texture(Gdx.files.internal("data/legs_run_sheet_128.png")), 16, 1);
		Animation legsRunAnimation = new Animation(0.03125f, legsWalkFrames);
		legsRunAnimation.setPlayMode(Animation.PlayMode.LOOP);

		TextureRegion[] torsoWalkFrames = arrangeFrames(new Texture(Gdx.files.internal("data/torso_run_sheet_128.png")), 16, 1);
		Animation torsoRunAnimation = new Animation(0.03125f, torsoWalkFrames);
		legsRunAnimation.setPlayMode(Animation.PlayMode.LOOP);

		// Create idle animations
		Animation legsIdleAnimation = new Animation(0.15f, legsWalkFrames);
		legsIdleAnimation.setPlayMode(Animation.PlayMode.LOOP);
		Animation torsoIdleAnimation = new Animation(0.15f, torsoWalkFrames);
		torsoIdleAnimation.setPlayMode(Animation.PlayMode.LOOP);

		PlayerAnimation playerAnimation = new PlayerAnimation();
		// Add run animation
		playerAnimation.addLegsAnimation("Run", legsRunAnimation);
		playerAnimation.addTorsoAnimation("Run", torsoRunAnimation);
		// Add idle animation
		playerAnimation.addLegsAnimation("Idle", legsIdleAnimation);
		playerAnimation.addTorsoAnimation("Idle", torsoIdleAnimation);

		// Create a player and assign him the animations we created
		player = new Player(world, ZShooter.WORLD_TILE_SIZE);
		player.setPosition(256, 256);
		player.setAnimation(playerAnimation);

		// Create a player controller (moves & updates the player through Touchpads)
		playerController = new PlayerController(player, stage);

		// Create the tile map
		map = new TileMap(100, 100, ZShooter.WORLD_TILE_SIZE, world);


// Debugging:
// Make a label showing the FPS
		stringBuilder = new StringBuilder();
		font = new BitmapFont();
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		label.setFontScale(3f);
		label.setPosition( 30 , 100);
		stage.addActor(label);


//// Create our body definition
//		BodyDef groundBodyDef = new BodyDef();
//// Set its world position
//		groundBodyDef.position.set(new Vector2(6.5f, 3.5f));
//
//// Create a body from the defintion and add it to the world
//		Body groundBody = world.createBody(groundBodyDef);
//
//// Create a polygon shape
//		PolygonShape groundBox = new PolygonShape();
//// Set the polygon shape as a box which is twice the size of our view port and 20 high
//// (setAsBox takes half-width and half-height as arguments)
//		groundBox.setAsBox(0.5f, 3.5f);
//// Create a fixture from our polygon shape and add it to our ground body
//		groundBody.createFixture(groundBox, 0.0f);
//// Clean up after ourselves
//		groundBox.dispose();

	}

	// Splits a sprite sheet into texture regions and returns a single array of texture regions
	private TextureRegion[] arrangeFrames(final Texture sheet, final int cols, final int rows) {
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
		TextureRegion[] finalSheet = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				finalSheet[index++] = tmp[i][j];
			}
		}
		return finalSheet;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		// Catch the back key event to prevent pausing our application
		Gdx.input.setCatchBackKey(true);

		GLProfiler.enable();	// Debugging - To show FPS in Log
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

		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		// Step the physics simulation
		world.step(1/60f, 6, 6);

		// Update the player's input forces
		playerController.update();

		// Camera coordinates to player coordinates
		camera.set(player.getPosition());
		batch.setCameraOffset(camera);

        batch.begin();

		// Draw map
		batch.draw(map, player);
		// Draw player
		batch.draw(player);

		batch.end();

// Debugging:
// Build the FPS label text to be shown this frame by the Stage...
		stringBuilder.setLength(0);
		stringBuilder.append("FPS: ").append(Gdx.app.getGraphics().getFramesPerSecond());
		stringBuilder.append("\n\nPlayer pos: " + (int)player.body.getPosition().x + "," + (int)player.body.getPosition().y + ".");
		label.setText(stringBuilder);
		//label.setPosition( 30, ZShooter.getScreenHeight() - 30 - ( 4 * (font.getCapHeight() + font.getLineHeight()) * label.getFontScaleY()) );
		label.setPosition( 30, 100);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();


		// Switch to Main Menu on Back Key press
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {

			GLProfiler.disable();

			Gdx.app.log("Back button pressed.", "Going to Main Menu.");
			game.setScreen(MainMenu.getMainMenu(game));
		}

// Debugging:
// Output in LogCat FPS, texture bindings and draw calls
//		fpsLogger.log();
//		Gdx.app.log("Texture Bindings: ", Integer.toString(GLProfiler.textureBindings));
//		Gdx.app.log("Draw Calls: ", Integer.toString(GLProfiler.drawCalls));
//		GLProfiler.reset();

	}


	@Override
	public void hide() {
		GLProfiler.disable();
	}

	@Override
	public void dispose() {
		map.dispose();
		playerController.dispose();

		// Singleton destroy
		inGameScreen = null;
	}

	public static Screen getInGameScreen(Game game) {
		if (inGameScreen == null) {
			inGameScreen = new InGameScreen(game);
		}
		return inGameScreen;
	}
}
