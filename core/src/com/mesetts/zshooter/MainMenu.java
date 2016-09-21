package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;




public class MainMenu implements Screen {

	private static MainMenu menu;			// Singleton reference to Main menu object
    public static TextButton loadGame;
	private Texture background;
	private Batch batch;
	private static Game game;
	private static Stage stage;
	public static TextButton startGameButton;
	private TextButton optionsButton;
	private Label gameTitle;
	public static TextButton loadGameInvisible;
	public static boolean isGameStarted;
	public static boolean areYouPlaying;
	private Color colorPrim = new Color(1f,1f,1f,0.75f);
	private MainMenu(final Game game) {
		MainMenu.game = game;

		background = ZShooter.assets.get("data/Textures/LoadingScreenZombies.jpg");
		stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());
		batch = ZShooter.getBatch();
// ---------------
// Buttons section
// ---------------

		startGameButton = new TextButton("New Game", GUI.getGUI().getTextButtonStyle());
		startGameButton.setColor(colorPrim);

		startGameButton.setSize( ZShooter.getScreenWidth() / 3f, ZShooter.getScreenHeight() / 6f);
		startGameButton.setPosition(ZShooter.getScreenWidth() / ZShooter.getScreenWidth()*0.9f, ZShooter.getScreenHeight()/4f);
		startGameButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//


				// TODO Auto-generated method stub


				return true;

			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(areYouPlaying){
					InGameScreen.player.setPosition(1,1);
					game.setScreen(IntroScreen.getInstance(game));
				}
					else{
						game.setScreen(IntroScreen.getInstance(game));
				}
//				if(areYouPlaying){
//					startGameButton.setText("New Game");
//					game.setScreen(IntroScreen.getInstance(game));
//				}
				dispose();
			}
		});
		// Add button to screen stage
		stage.addActor(startGameButton);
		loadGame = new TextButton("Load",GUI.getGUI().getTextButtonStyle());
		loadGame.setColor(colorPrim);
		loadGame.setSize( ZShooter.getScreenWidth() / 3f, ZShooter.getScreenHeight() / 6f);
		loadGame.setPosition(ZShooter.getScreenWidth()-loadGame.getWidth(), ZShooter.getScreenHeight()-loadGame.getHeight());
		loadGame.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//


				// TODO Auto-generated method stub


				return true;

			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				if(areYouPlaying){
					game.setScreen(InGameScreen.getInGameScreen(game));
				}
				else{
					InGameScreen.getInGameScreen(game);
					Database.getOurInstance().load();
					float cordinateX = Database.getOurInstance().getCordinateX();
					float cordinateY = Database.getOurInstance().getCordinateY();
					InGameScreen.player.setPosition(cordinateX,cordinateY);
					game.setScreen(InGameScreen.getInGameScreen(game));
				}
				dispose();
			}
		});
		stage.addActor(loadGame);

		loadGameInvisible = new TextButton("Load",GUI.getGUI().getTextButtonStyle());
		loadGameInvisible.setColor(colorPrim);
		loadGameInvisible.setVisible(false);

		loadGameInvisible.setSize( ZShooter.getScreenWidth() / 3f, ZShooter.getScreenHeight() / 6f);
		loadGameInvisible.setPosition(loadGame.getX(), loadGame.getY() - loadGameInvisible.getHeight() - ZShooter.getScreenHeight() * 0.05f);
		loadGameInvisible.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//


				// TODO Auto-generated method stub


				return true;

			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {


					InGameScreen.getInGameScreen(game);
					Database.getOurInstance().load();
					float cordinateX = Database.getOurInstance().getCordinateX();
					float cordinateY = Database.getOurInstance().getCordinateY();
					InGameScreen.player.setPosition(cordinateX,cordinateY);
					game.setScreen(InGameScreen.getInGameScreen(game));

				dispose();
			}
		});
		stage.addActor(loadGameInvisible);

		optionsButton = new TextButton("Options", GUI.getGUI().getTextButtonStyle());
		optionsButton.setColor(colorPrim);
		optionsButton.setSize(ZShooter.getScreenWidth() / 3f, ZShooter.getScreenHeight() / 6f);
		optionsButton.setPosition(startGameButton.getX(), startGameButton.getY() - optionsButton.getHeight() - ZShooter.getScreenHeight() * 0.05f);
		optionsButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//


				// TODO Auto-generated method stub


				return true;

			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(OptionsScreen.getOptionsScreen(game));
				dispose();
			}

		});
		// Add button to screen stage
		stage.addActor(optionsButton);

		//gameTitle = new Label("New Game", GUI.getGUI().getLabelStyle());

		//gameTitle.setWidth(ZShooter.getScreenWidth()/3);
		//gameTitle.setPosition(ZShooter.getScreenWidth() / (ZShooter.getScreenWidth()*0.8f), ZShooter.getScreenHeight()/1.8f);
		//Add label to screen stage

		//stage.addActor(gameTitle);
	}

    @Override
    public void show() {
		Gdx.app.log("Screen = ","Main menu");
		Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
		stage.dispose();

		// Singleton destroy
		menu = null;
    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background,0,0);
		batch.end();
        stage.draw();

// On back button - Exit application
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
			Gdx.app.log("Back button pressed.", "Exiting application.");
			Gdx.app.exit();
		}
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

	public static MainMenu getMainMenu(Game game) {
		if (menu == null) {
			menu = new MainMenu(game);
		}
		return menu;
	}
}

