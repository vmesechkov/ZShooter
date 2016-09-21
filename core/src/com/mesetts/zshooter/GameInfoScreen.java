package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by NoLight on 11.9.2016 г..
 */
public class GameInfoScreen implements Screen {
    private static GameInfoScreen ourInstance;
    private Game game;
    private Stage stage;
    private Texture background;
    private TextButton backButton;
    private Color colorPrim = new Color(1f,1f,1f,0.75f);
    private String gameInfo = "Game creators:" +
            "\nVencislav Mesechkov, \n" +
            "Teodor Dimitrov,..\n\n\"" +
            "Game info: \n ZShooter 1.0,\n Created for 30 days,\n Some statistics coming..\n Zombies,shooters,backgrounds.. \n Best ZShooter you have seen in a while";

    public static GameInfoScreen getInstance(final Game game) {
        if(ourInstance == null){
            ourInstance = new GameInfoScreen(game);
        }
        return ourInstance;
    }

    private GameInfoScreen(final Game game) {
        background = ZShooter.assets.get("data/Textures/LoadingScreenFIXED2.jpg");
        this.game = game;
        stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());
        Gdx.input.setInputProcessor(stage);
        backButton = new TextButton("Back", GUI.getGUI().getTextButtonStyle());
        backButton.setColor(colorPrim);
        backButton.setHeight(ZShooter.getScreenHeight() / 5.5f); //** Button Height **//
        backButton.setWidth(ZShooter.getScreenWidth() / 3.5f); //** Button Width **//
        backButton.setPosition(ZShooter.getScreenWidth()/1.3f - backButton.getWidth()/2,ZShooter.getScreenHeight()/1.2f - backButton.getHeight());
        backButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(OptionsScreen.getOptionsScreen(game));

            }
        });
        stage.addActor(backButton);


    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(OptionsScreen.getOptionsScreen(game));
        }

        ZShooter.getBatch().begin();
        ZShooter.getBatch().draw(background,0,0);
        GUI.getGUI().getGlobalFont().draw(ZShooter.getBatch(),gameInfo,ZShooter.getScreenWidth()/14f,ZShooter.getScreenHeight()/1.4f);
        ZShooter.getBatch().end();
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

    }
}
