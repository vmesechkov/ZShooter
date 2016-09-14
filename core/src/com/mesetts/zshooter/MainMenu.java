package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;


public class MainMenu implements Screen {

	private static MainMenu menu;			// Singleton reference to Main menu object

	private static Game game;
	private static Stage stage;
	private TextButton startGameButton;
	private TextButton optionsButton;
	private Label gameTitle;
	private boolean isGameStarted;
	private MainMenu(final Game game) {
		MainMenu.game = game;

		stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());

// ---------------
// Buttons section
// ---------------
		startGameButton = new TextButton("Play", GUI.getGUI().getTextButtonStyle());
		startGameButton.setSize( ZShooter.getScreenWidth() / 3f, ZShooter.getScreenHeight() / 6f);
		startGameButton.setPosition(ZShooter.getScreenWidth() / 2 - startGameButton.getWidth()/2, ZShooter.getScreenHeight()/4.5f);
		startGameButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//


				// TODO Auto-generated method stub


				return true;

			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(!isGameStarted){
					game.setScreen(IntroScreen.getInstance(game));
				}
					else{
						game.setScreen(InGameScreen.getInGameScreen(game));
				}
			}
		});
		// Add button to screen stage
		stage.addActor(startGameButton);

		optionsButton = new TextButton("Options", GUI.getGUI().getTextButtonStyle());
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
			}
		});
		// Add button to screen stage
		stage.addActor(optionsButton);

		gameTitle = new Label("ZSHOOTER", GUI.getGUI().getTitleLabelStyle());
		gameTitle.setHeight(ZShooter.getScreenHeight()/3);
		//gameTitle.setWidth(ZShooter.getScreenWidth()/3);
		gameTitle.setPosition(ZShooter.getScreenWidth() / 2 - gameTitle.getWidth()/2,ZShooter.getScreenHeight()/3);
		//Add label to screen stage

		stage.addActor(gameTitle);
	}

    @Override
    public void show() {
		if(InGameScreen.exists()) {
			startGameButton.setText("Resume");
			isGameStarted = true;
		}
		else{
			startGameButton.setText("Play");
		}
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
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

