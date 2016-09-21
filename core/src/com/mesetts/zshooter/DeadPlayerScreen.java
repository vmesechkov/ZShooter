package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by NoLight on 20.9.2016 г..
 */
public class DeadPlayerScreen implements Screen {
    private static DeadPlayerScreen ourInstance; //Singleton
    private Texture background; //background
    private Batch batch;
    private Game game;
    private Stage stage;
    private TextButton loadGameButton;
    private TextButton mainMenuButton;
    public float textDrawLength = 0.0f;
    public static final  float TEXTSPEED = 0.3f;  //Text appearing on the screen.
    public String theText = "You are DEAD...";


    public static DeadPlayerScreen getInstance(Game game) {
        if(ourInstance == null){
            ourInstance = new DeadPlayerScreen(game);
        }
        return ourInstance;
    }

    private DeadPlayerScreen(final Game game) {
        background = ZShooter.assets.get("data/Textures/ZSHOOTER BACKGROUND.jpg");
        this.game = game;
        stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());
        batch = ZShooter.getBatch();

        //Load button
        loadGameButton = new TextButton("Load Game",GUI.getGUI().getTextButtonStyle());
        loadGameButton.setSize( ZShooter.getScreenWidth() / 2.8f, ZShooter.getScreenHeight() / 5f);
        loadGameButton.setPosition(ZShooter.getScreenWidth() / 2f - loadGameButton.getWidth()/2, ZShooter.getScreenHeight()/7f);
        loadGameButton.addListener(new InputListener(){
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
            }
        });
        stage.addActor(loadGameButton);

        //Back to main menu button
        mainMenuButton = new TextButton("Main Menu", GUI.getGUI().getTextButtonStyle());
        mainMenuButton.setHeight(ZShooter.getScreenHeight() / 5); //** Button Height **//
        mainMenuButton.setWidth(ZShooter.getScreenWidth() / 3); //** Button Width **//
        mainMenuButton.setPosition(ZShooter.getScreenWidth() / 2 - mainMenuButton.getWidth()/2,ZShooter.getScreenHeight()/2f);
        mainMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    MainMenu.startGameButton.setText("New Game");
                    game.setScreen(MainMenu.getMainMenu(game));
            }
        });
        stage.addActor(mainMenuButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if((int)textDrawLength < theText.length()){
            textDrawLength+=(0.5f*TEXTSPEED);
        }
        batch.draw(background,0,0);
        //rendering text after drawed background
        GUI.getGUI().getGlobalFont().draw(ZShooter.getBatch(),theText.substring(0,(int)textDrawLength),ZShooter.getScreenWidth()/3.5f,ZShooter.getScreenHeight()/1.1f);

        batch.end();
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

    }
}
