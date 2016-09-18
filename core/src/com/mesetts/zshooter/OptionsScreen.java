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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.Gdx.files;

/**
 * Created by NoLight on 21.8.2016 г..
 */
public class OptionsScreen implements Screen {

    private static OptionsScreen optionsScreen;

    private Game game;
    private Stage stage;
    private Slider soundSlider;
    private Slider musicSlider;
    private TextButton aboutButton;
    private Label labelSound;
    private Label labelMusic;
	private TextButton tileMapEditorButton;

    private OptionsScreen(final Game game){
        this.game = game;
        stage = new Stage(ZShooter.getViewport(), ZShooter.getBatch());

        aboutButton = new TextButton("About", GUI.getGUI().getTextButtonStyle());
        aboutButton.setHeight(ZShooter.getScreenHeight() / 5); //** Button Height **//
        aboutButton.setWidth(ZShooter.getScreenWidth() / 3); //** Button Width **//
        aboutButton.setPosition(ZShooter.getScreenWidth() / 2 - aboutButton.getWidth()/2,ZShooter.getScreenHeight()/3.8f);
        aboutButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(GameInfoScreen.getInstance(game));
            }
        });
        stage.addActor(aboutButton);

		tileMapEditorButton = new TextButton("Editor", GUI.getGUI().getTextButtonStyle());
		tileMapEditorButton.setHeight(ZShooter.getScreenHeight() / 5); //** Button Height **//
		tileMapEditorButton.setWidth(ZShooter.getScreenWidth() / 3); //** Button Width **//
		tileMapEditorButton.setPosition(ZShooter.getScreenWidth() - tileMapEditorButton.getWidth(),ZShooter.getScreenHeight() - tileMapEditorButton.getHeight());
		tileMapEditorButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(TileMapEditorScreen.getTileMapEditor(game));
			}
		});
		stage.addActor(tileMapEditorButton);

        soundSlider = new Slider(0f, 1f, 0.05f, false, GUI.getGUI().getSliderStyle());
        soundSlider.setAnimateDuration(0.01f);
        soundSlider.setWidth(ZShooter.getScreenWidth()/2);
        soundSlider.setPosition(ZShooter.getScreenWidth() / 2 - soundSlider.getWidth()/2, ZShooter.getScreenHeight()/6);
        soundSlider.getStyle().knob.setMinHeight(80);
        soundSlider.getStyle().knob.setMinWidth(80);
        soundSlider.getStyle().knob.setBottomHeight(120);
        soundSlider.getStyle().knobBefore.setMinHeight(100);
        soundSlider.getStyle().knobBefore.setMinWidth(0);
        stage.addActor(soundSlider);

        musicSlider = new Slider(0f, 1f, 0.025f, false, GUI.getGUI().getSliderStyle());
        musicSlider.setAnimateDuration(0.01f);
        musicSlider.setWidth(ZShooter.getScreenWidth()/2);
        musicSlider.setPosition(ZShooter.getScreenWidth() / 2 - musicSlider.getWidth()/2, ZShooter.getScreenHeight()/25);

        stage.addActor(musicSlider);

        labelSound = new Label("Sound", GUI.getGUI().getLabelStyle());
      //labelSound.setHeight(ZShooter.getScreenHeight()/3);
        //labelSound.setWidth(ZShooter.getScreenWidth()/3);
        labelSound.setPosition(ZShooter.getScreenWidth()/20,ZShooter.getScreenHeight()/6);
        stage.addActor(labelSound);

        labelMusic = new Label("Music", GUI.getGUI().getLabelStyle());
        //labelMusic.setHeight(ZShooter.getScreenHeight()/3);
       // labelMusic.setWidth(ZShooter.getScreenWidth()/3);
        labelMusic.setPosition(ZShooter.getScreenWidth()/20,ZShooter.getScreenHeight()/25);
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
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
