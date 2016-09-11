package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by NoLight on 11.9.2016 г..
 */
public class IntroScreen implements Screen {
    private Game game;
    private Stage stage;
    public float textDrawLength = 0.0f;
    public static final  float TEXTSPEED = 0.4f;
    public String theText = "      Year: 3001\n\nthe world is dying\n" +
            "everyone is infected\n\n find your way out...\n\n alive now..";
    private TextButton continueButton;
    private static IntroScreen ourInstance;

    public static IntroScreen getInstance(Game game) {
        if(ourInstance == null){
            ourInstance = new IntroScreen(game);

        }
        return ourInstance;
    }

    private IntroScreen(final Game game) {
        this.game = game;
        stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());
        Gdx.input.setInputProcessor(stage);
        continueButton = new TextButton("Continue", GUI.getGUI().getTextButtonStyle());
        continueButton.setHeight(ZShooter.getScreenHeight() / 6); //** Button Height **//
        continueButton.setWidth(ZShooter.getScreenWidth() / 3); //** Button Width **//
        continueButton.setPosition(ZShooter.getScreenWidth()/1.5f,ZShooter.getScreenHeight()/ZShooter.getScreenHeight());
                continueButton.addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.log("my app123", "Pressed"); //** Usually used to start Game, etc. **//


                        // TODO Auto-generated method stub


                        return true;

                    }
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                        game.setScreen(IntroScreen2.getInstance(game));
                    }
                });
        stage.addActor(continueButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if((int)textDrawLength < theText.length()){
            textDrawLength+=(0.5f*TEXTSPEED);
        }
        ZShooter.getBatch().begin();
        GUI.getGUI().getTitleFont().draw(ZShooter.getBatch(),theText.substring(0,(int)textDrawLength),ZShooter.getScreenWidth()/14f,ZShooter.getScreenHeight()/2);
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
        stage.dispose();

    }
}
