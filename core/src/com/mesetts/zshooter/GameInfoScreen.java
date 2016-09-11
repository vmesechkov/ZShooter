package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by NoLight on 11.9.2016 г..
 */
public class GameInfoScreen implements Screen {
    private static GameInfoScreen ourInstance;
    private Game game;
    private Stage stage;
    private String gameInfo = "Game creators:" +
            "\nVencislav Mesechkov, \n" +
            "Teodor Dimitrov,..\n\n\"" +
            "Game info: \n Some facts,\n Some background story,\n Some statistics..\n Skins,Graphis,Textures.. \n All in all all kinds of info";

    public static GameInfoScreen getInstance(Game game) {
        if(ourInstance == null){
            ourInstance = new GameInfoScreen(game);
        }
        return ourInstance;
    }

    private GameInfoScreen(Game game) {
        this.game = game;
        stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(MainMenu.getMainMenu(game));
        }

        ZShooter.getBatch().begin();
        GUI.getGUI().getGlobalFont().draw(ZShooter.getBatch(),gameInfo,ZShooter.getScreenWidth()/14f,ZShooter.getScreenHeight()/2);
        ZShooter.getBatch().end();
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
