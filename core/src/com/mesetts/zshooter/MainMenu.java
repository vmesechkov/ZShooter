package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

	private MainMenu(final Game game) {
		MainMenu.game = game;

		stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());

// ---------------
// Buttons section
// ---------------
		startGameButton = new TextButton("Play", GUI.getGUI().getTextButtonStyle());
		startGameButton.setSize( ZShooter.getScreenWidth() / 3f, ZShooter.getScreenHeight() / 5.5f);
		startGameButton.setPosition(ZShooter.getScreenWidth() / 2 - startGameButton.getWidth()/2, ZShooter.getScreenHeight()/4.5f);
		startGameButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//


				// TODO Auto-generated method stub


				return true;

			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				game.setScreen(InGameScreen.getInGameScreen(game));
			}
		});
		// Add button to screen stage
		stage.addActor(startGameButton);

		optionsButton = new TextButton("Options", GUI.getGUI().getTextButtonStyle());
		optionsButton.setSize(ZShooter.getScreenWidth() / 3f, ZShooter.getScreenHeight() / 5.5f);
		optionsButton.setPosition(ZShooter.getScreenWidth() / 2 - optionsButton.getWidth()/2,0);
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

