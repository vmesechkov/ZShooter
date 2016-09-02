package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;


public class MainMenu implements Screen {

	private static MainMenu menu;			// Singleton reference to Main menu object

	private static Game game;
	private static Stage stage;

	private MainMenu(final Game game) {
		MainMenu.game = game;

		stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());

// ---------------
// Buttons section
// ---------------
		TextButton button = new TextButton("Rjhghjgj", GUI.getGUI().style);
		button.setSize( ZShooter.getScreenWidth() / 4 , ZShooter.getScreenHeight() / 4 );
		button.setPosition(ZShooter.getScreenWidth() / 2 - button.getWidth()/2, ZShooter.getScreenHeight() / 7 );
		button.addListener(new InputListener() {

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				game.setScreen(LoadingScreen.getLoadingScreen(game));
				game.setScreen(InGameScreen.getInGameScreen(game));
			}
		});
		// Add button to screen stage
		stage.addActor(button);

		TextButton button1 = new TextButton("asdasd", GUI.getGUI().style);
		button1.setSize( ZShooter.getScreenWidth() / 4 , ZShooter.getScreenHeight() / 4 );
		button1.setPosition(ZShooter.getScreenWidth() / 2 - button.getWidth()/2, ZShooter.getScreenHeight()/ 3);
		// Add button to screen stage
		stage.addActor(button1);

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

