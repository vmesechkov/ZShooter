package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;



/**
 * Created by NoLight on 20.9.2016 г..
 */
public class PausedGameScreen implements Screen {
    private static PausedGameScreen pausedGame;			// Singleton reference to Main menu object
    public static boolean isItCalled;
    private TextButton saveAndPlay;
    private Texture background;
    private Batch batch;
    private static  Game game;
    private static Stage stage;
    private TextButton optionsButton;
    private TextButton backButton;
    private TextButton mainMenuButton;
    private Label pauseLabel;
    private Color colorPrim = new Color(1f,1f,1f,0.75f);


    private PausedGameScreen(final Game game){
        PausedGameScreen.game = game;
        background = ZShooter.assets.get("data/Textures/Zombie head.jpg");
        stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());
        batch = ZShooter.getBatch();
// ---------------
// Buttons section
// ---------------
        saveAndPlay = new TextButton("Save And Play", GUI.getGUI().getTextButtonStyle());
        saveAndPlay.setSize( ZShooter.getScreenWidth() / 2.1f, ZShooter.getScreenHeight() / 6f);
        saveAndPlay.setColor(colorPrim);
        saveAndPlay.setPosition(ZShooter.getScreenWidth() / 1.325f - saveAndPlay.getWidth()/2, ZShooter.getScreenHeight()/12f);

        saveAndPlay.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed");


                // TODO Auto-generated method stub


                return true;

            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Database.getOurInstance().setCordinateX(InGameScreen.player.getX());
                Database.getOurInstance().setCordinateY(InGameScreen.player.getY());
                Database.getOurInstance().save();
                game.setScreen(InGameScreen.getInGameScreen(game));

            }
        });
        // Add button to screen stage
        stage.addActor(saveAndPlay);

        backButton = new TextButton("Resume", GUI.getGUI().getTextButtonStyle());
        backButton.setColor(colorPrim);
        backButton.setHeight(ZShooter.getScreenHeight() / 5); //** Button Height **//
        backButton.setWidth(ZShooter.getScreenWidth() / 3); //** Button Width **//
        backButton.setPosition(ZShooter.getScreenWidth() / 1.3f - backButton.getWidth()/2, ZShooter.getScreenHeight()/1.3f);
        backButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(InGameScreen.getInGameScreen(game));
            }
        });
        mainMenuButton = new TextButton("MainMenu", GUI.getGUI().getTextButtonStyle());
        mainMenuButton.setColor(colorPrim);
        mainMenuButton.setHeight(ZShooter.getScreenHeight() / 5); //** Button Height **//
        mainMenuButton.setWidth(ZShooter.getScreenWidth() / 3); //** Button Width **//
        mainMenuButton.setPosition(ZShooter.getScreenWidth() / 1.3f - mainMenuButton.getWidth()/2, ZShooter.getScreenHeight()/2f);
        mainMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MainMenu.getMainMenu(game);
                MainMenu.areYouPlaying = true;
                MainMenu.loadGameInvisible.setVisible(true);
                MainMenu.loadGame.setText("Resume");
                game.setScreen(MainMenu.getMainMenu(game));

            }
        });
        stage.addActor(mainMenuButton);
        optionsButton = new TextButton("Options", GUI.getGUI().getTextButtonStyle());
        optionsButton.setColor(colorPrim);
        optionsButton.setSize(ZShooter.getScreenWidth() / 3f, ZShooter.getScreenHeight() / 5f);
        optionsButton.setPosition(backButton.getX(), mainMenuButton.getY() - optionsButton.getHeight() - ZShooter.getScreenHeight() * 0.03f);
        optionsButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//


                // TODO Auto-generated method stub


                return true;

            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PausedGameScreen.isItCalled = true;
                game.setScreen(OptionsScreen.getOptionsScreen(game));
                dispose();
            }
        });
        // Add button to screen stage
        stage.addActor(optionsButton);

        //pauseLabel = new Label("    Game \n    is \n Paused", GUI.getGUI().getTitleLabelStyle());
        //pauseLabel.setHeight(ZShooter.getScreenHeight()/3);
        //pauseLabel.setWidth(ZShooter.getScreenWidth()/3);
        //pauseLabel.setPosition(ZShooter.getScreenWidth() / 2 - pauseLabel.getWidth()/2,ZShooter.getScreenHeight()/1.7f);
        //Add label to screen stage

       // stage.addActor(pauseLabel);

        stage.addActor(backButton);
    }
    public static PausedGameScreen getPausedGame(Game game) {
        if (pausedGame == null) {
            pausedGame = new PausedGameScreen(game);
        }
        return pausedGame;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Catch the back key event to prevent pausing our application
        Gdx.input.setCatchBackKey(true);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {

            GLProfiler.disable();

            Gdx.app.log("Back button pressed.", "Going to Main Menu.");
            game.setScreen(InGameScreen.getInGameScreen(game));
        }
        stage.act();
        stage.draw();


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
        pausedGame = null;
        stage.dispose();

    }
}
