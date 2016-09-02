package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.Gdx.files;

public class OptionsScreen implements Screen {

    private static OptionsScreen optionsScreen;

    private Game game;
    private Stage stage;

    private Slider soundSlider;
    private Slider musicSlider;
    private TextButton aboutButton;
    private Label labelSound;
    private Label labelMusic;

    private OptionsScreen(Game game){
        this.game = game;
        stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());

        aboutButton = new TextButton("About", GUI.getGUI().getTextButtonStyle());
        aboutButton.setHeight(ZShooter.getScreenHeight() / 8); //** Button Height **//
        aboutButton.setWidth(ZShooter.getScreenWidth() / 3); //** Button Width **//
        aboutButton.setPosition(ZShooter.getScreenWidth() / 2 - aboutButton.getWidth()/2,ZShooter.getScreenHeight() - aboutButton.getHeight() - ZShooter.getScreenHeight() * 0.05f);
        stage.addActor(aboutButton);

        soundSlider = new Slider(0, 1, 0.01f, false, GUI.getGUI().getSliderStyle());
        soundSlider.setAnimateDuration(0.0f);
        soundSlider.setWidth(ZShooter.getScreenWidth()/2);
		soundSlider.setHeight(ZShooter.getScreenHeight() / 20);
		soundSlider.setOrigin(0, soundSlider.getHeight() / 2);
        soundSlider.setPosition(ZShooter.getScreenWidth() / 2 - soundSlider.getWidth()/2, ZShooter.getScreenHeight()/6);
        soundSlider.getStyle().knob.setMinHeight(ZShooter.getScreenHeight() / 15);
		soundSlider.getStyle().knob.setMinWidth(soundSlider.getStyle().knob.getMinHeight());
		soundSlider.getStyle().knobBefore.setMinHeight(ZShooter.getScreenHeight() / 15);
		soundSlider.getStyle().background.setMinHeight(ZShooter.getScreenHeight() / 20);
//        soundSlider.getStyle().knob.setMinWidth(80);
//        soundSlider.getStyle().knob.setBottomHeight(120);
//        soundSlider.getStyle().knobBefore.setMinHeight(100);
//        soundSlider.getStyle().knobBefore.setMinWidth(0);
        stage.addActor(soundSlider);

        musicSlider = new Slider(0, 1, 0.05f, false, GUI.getGUI().getSliderStyle());
        musicSlider.setAnimateDuration(0.0f);
        musicSlider.setWidth(ZShooter.getScreenWidth()/2);
		musicSlider.setOrigin(0, musicSlider.getHeight() / 2);
        musicSlider.setPosition(ZShooter.getScreenWidth() / 2 - musicSlider.getWidth()/2, ZShooter.getScreenHeight()/25);

        stage.addActor(musicSlider);

        labelSound = new Label("Sound", GUI.getGUI().getLabelStyle());
      //labelSound.setHeight(ZShooter.getScreenHeight()/3);
        //labelSound.setWidth(ZShooter.getScreenWidth()/3);
        labelSound.setPosition(ZShooter.getScreenWidth()/20, soundSlider.getY());
        stage.addActor(labelSound);

        labelMusic = new Label("Music", GUI.getGUI().getLabelStyle());
        //labelMusic.setHeight(ZShooter.getScreenHeight()/3);
       // labelMusic.setWidth(ZShooter.getScreenWidth()/3);
        labelMusic.setPosition(ZShooter.getScreenWidth()/20, musicSlider.getY());
        stage.addActor(labelMusic);


    }

    public static OptionsScreen getOptionsScreen(Game game){
        if(optionsScreen == null){
            optionsScreen = new OptionsScreen(game);
        }
        return optionsScreen;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

		Gdx.app.log("Slider height: ", "Sound: " + soundSlider.getHeight() + ", Music: " + musicSlider.getHeight());
    }


    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Gdx.app.log("Changed", "" + ZShooter.soundVolume);
        ZShooter.setSoundVolume(soundSlider.getValue());
		ZShooter.setMusicVolume(musicSlider.getValue());
		if (ZShooter.getMusic() != null) {
			ZShooter.getMusic().setVolume(ZShooter.getMusicVolume());
		}

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(MainMenu.getMainMenu(game));
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

    }




}
