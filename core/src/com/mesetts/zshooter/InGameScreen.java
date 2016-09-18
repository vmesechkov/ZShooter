package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.Random;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
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

	Enemy zombie;

	RayHandler rayHandler;
	PointLight pointLight;

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
		world.setContactListener(new ZContactListener());

// Torso Frames and Texture
		Texture torsoSheet = new Texture(Gdx.files.internal("data/torso_sheet_128.png"));
		TextureRegion[][] torsoFrames = TextureRegion.split(torsoSheet, torsoSheet.getWidth() / 16, torsoSheet.getHeight() / 8);

		Animation torsoIdleAnimation = new Animation(0.15f, torsoFrames[0]);
		torsoIdleAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation torsoRunAnimation = new Animation(0.05f, torsoFrames[1]);
		torsoRunAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation torsoShootAnimation = new Animation(0.05f, torsoFrames[0]);
		torsoShootAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation torsoRunShootAnimation = new Animation(0.05f, torsoFrames[2]);
		torsoRunShootAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation torsoReloadAnimation = new Animation(0.125f, torsoFrames[4]);
		torsoReloadAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation torsoWalkAnimation = new Animation(0.125f, torsoFrames[6]);
		torsoWalkAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation torsoWalkShootAnimation = new Animation(0.125f, torsoFrames[7]);
		torsoWalkShootAnimation.setPlayMode(Animation.PlayMode.LOOP);

// Legs Frames and Texture
		Texture legsSheet = new Texture(Gdx.files.internal("data/legs_sheet_128.png"));
		TextureRegion[][] legsFrames = TextureRegion.split(legsSheet, legsSheet.getWidth() / 16, legsSheet.getHeight() / 7);

		Animation legsIdleAnimation = new Animation(0.15f, legsFrames[0]);
		legsIdleAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation legsRunAnimation = new Animation(0.05f, legsFrames[1]);
		legsRunAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation legsBackRunAnimation = new Animation(0.05f, legsFrames[4]);
		legsBackRunAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation legsWalkAnimation = new Animation(0.0625f, legsFrames[5]);
		legsWalkAnimation.setPlayMode(Animation.PlayMode.LOOP);

		Animation legsWalkBackAnimation = new Animation(0.0625f, legsFrames[6]);
		legsWalkBackAnimation.setPlayMode(Animation.PlayMode.LOOP);

/////////////////////////////
		EntityAnimation playerLegsAnimation = new EntityAnimation();
		playerLegsAnimation.addAnimation("Idle", legsIdleAnimation);		// Add idle animation to collection
		playerLegsAnimation.addAnimation("Run", legsRunAnimation);			// Add run animation to collection
		playerLegsAnimation.addAnimation("BackRun", legsBackRunAnimation);	// Add backwards running animation to collection
		playerLegsAnimation.addAnimation("Walk", legsWalkAnimation);			// Add walk animation to collection
		playerLegsAnimation.addAnimation("BackWalk", legsWalkBackAnimation);	// Add backwards walking animation to collection

		EntityAnimation playerTorsoAnimation = new EntityAnimation();
		playerTorsoAnimation.addAnimation("Idle", torsoIdleAnimation);		// Add idle animation to collection
		playerTorsoAnimation.addAnimation("Run", torsoRunAnimation);		// Add run animation to collection
		playerTorsoAnimation.addAnimation("Shoot", torsoShootAnimation);		// Add shoot animation to collection
		playerTorsoAnimation.addAnimation("RunShoot", torsoRunShootAnimation);		// Add running shooting animation to collection
		playerTorsoAnimation.addAnimation("Reload", torsoReloadAnimation);		// Add reload animation to collection
		playerTorsoAnimation.addAnimation("Walk", torsoWalkAnimation);		// Add walk animation to collection
		playerTorsoAnimation.addAnimation("WalkShoot", torsoWalkShootAnimation);		// Add shooting walk animation to collection


		// Create a player and assign him the animations we created
		player = new Player(world, ZShooter.WORLD_TILE_SIZE);
		player.setPosition(1, 1);
		player.setLegsAnimation(playerLegsAnimation);						// Add the animation collection for the legs
		player.setTorsoAnimation(playerTorsoAnimation);						// Add the animation collection for the torso

		// Create a player controller (moves & updates the player through Touchpads)
		playerController = new PlayerController(player, stage);

		// Create the tile map
		Texture tileMapTexture = new Texture(Gdx.files.internal("data/Textures/textureSheet2_128.png"));
		map = new TileMap(tileMapTexture, 100, 100, ZShooter.WORLD_TILE_SIZE, world);
		map.generateRandomMap();
		map.generateBody();


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

		// Get frames in an array from the texture
		Texture zombieSheet = new Texture(Gdx.files.internal("data/zombie_sheet_128.png"));
		TextureRegion[][] zombieFrames = TextureRegion.split(zombieSheet, zombieSheet.getWidth() / 16, zombieSheet.getHeight() / 6);

		// Create and init animations for zombie run
		Animation zombieRunAnimation = new Animation(0.0625f, zombieFrames[3]);
		zombieRunAnimation.setPlayMode(Animation.PlayMode.LOOP);

		// Create idle animations
		Animation zombieIdle1Animation = new Animation(0.15f, zombieFrames[0]);
		zombieIdle1Animation.setPlayMode(Animation.PlayMode.LOOP);

		Animation zombieIdle2Animation = new Animation(0.093f, zombieFrames[1]);
		zombieIdle2Animation.setPlayMode(Animation.PlayMode.LOOP);

		Animation zombieIdle3Animation = new Animation(0.15f, zombieFrames[2]);
		zombieIdle3Animation.setPlayMode(Animation.PlayMode.LOOP);

		// Create attack animation
		Animation zombieAttackAnimation = new Animation(0.0375f, zombieFrames[4]);
		zombieAttackAnimation.setPlayMode(Animation.PlayMode.LOOP);

		// Create die animation
		Animation zombieDieAnimation = new Animation(0.04f, zombieFrames[5]);
		zombieDieAnimation.setPlayMode(Animation.PlayMode.NORMAL);

		EntityAnimation zombieAnimation = new EntityAnimation();
		zombieAnimation.addAnimation("Idle1", zombieIdle1Animation);		// Add idle animation to collection
		zombieAnimation.addAnimation("Idle2", zombieIdle2Animation);
		zombieAnimation.addAnimation("Idle3", zombieIdle3Animation);
		zombieAnimation.addAnimation("Run", zombieRunAnimation);			// Add run animation to collection
		zombieAnimation.addAnimation("Attack", zombieAttackAnimation);
		zombieAnimation.addAnimation("Die", zombieDieAnimation);

		// Pass the zombie tile coordinates (3,3) to the constructor
//		zombie = new Enemy(world, ZShooter.WORLD_TILE_SIZE, map, player, 3, 5);
//		zombie.setHealth(100);
//		zombie.setAnimation(zombieAnimation);

		zombies = new ArrayList<Enemy>();

		Random r = new Random();
		for (int i = 0; i < 50; i++) {
			// Pass the zombie tile coordinates (3,3) to the constructor
			zombie = new Enemy(world, ZShooter.WORLD_TILE_SIZE, map, player, r.nextInt(50), r.nextInt(50));
			zombie.setHealth(100);
			zombie.setAnimation(zombieAnimation);
			zombies.add(zombie);
		}

		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.02f, 0.03f, 0.24f, 0.12f);

		// Use STRONG Colors with low alpha, so stacking lights does'nt blind the user...
		light = new ConeLight(rayHandler, 128, new Color(1,1,1,0.65f), 20, 0, 0, 0, 45);
		light.attachToBody(player.body, 0, 0f, -90f);
		light.setIgnoreAttachedBody(true);
		light.setSoftnessLength(1.5f);
		light.setContactFilter((short)0x0001,(short)0x0, (short)0x0001);

		// Use STRONG Colors with low alpha, so stacking lights does'nt blind the user...
		PointLight light2 = new PointLight(rayHandler, 256, new Color(1,0.7f,0.3f,0.65f), 10, 25.5f, 25.5f);
		light2.setContactFilter((short)0x0001,(short)0x0, (short)0x0001);
	}

	ConeLight light;
	OrthographicCamera cam = new OrthographicCamera(ZShooter.getViewport().getWorldWidth() / ZShooter.WORLD_TILE_SIZE, ZShooter.getViewport().getWorldHeight() / ZShooter.WORLD_TILE_SIZE);

	ArrayList<Enemy> zombies;

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

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		// Step the physics simulation
		world.step(1/60f, 6, 6);

		// Update the player's input forces
		playerController.update();

		for (Enemy z: zombies) {
			z.update(delta);
		}
		//zombie.update(delta);

		// Camera coordinates to player coordinates
		camera.set(player.getPosition());
		camera.scl(ZShooter.WORLD_TILE_SIZE);
		batch.setCameraOffset(camera);

        batch.begin();

		// Draw map
		batch.draw(map);

		// Draw a zombie
		for (Enemy z: zombies) {
			batch.draw(z);
		}
		batch.draw(zombie);

		batch.end();

		//light.setDirection(player.getPan() - 90f);
		//light.update();

		cam.position.set(player.getX(), player.getY(), 0);
		//cam.position.scl(ZShooter.WORLD_TILE_SIZE);
		cam.update();
		rayHandler.setCombinedMatrix(cam.combined, 0, 0, ZShooter.getScreenWidth(), ZShooter.getScreenHeight());
		rayHandler.updateAndRender();

		batch.begin();
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

		// Update stage and draw it
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

		rayHandler.dispose();

		// Singleton destroy
		inGameScreen = null;
	}

	public static Screen getInGameScreen(Game game) {
		if (inGameScreen == null) {
			inGameScreen = new InGameScreen(game);
		}
		return inGameScreen;
	}
	public static boolean exists(){
		return inGameScreen != null;
	}
}
