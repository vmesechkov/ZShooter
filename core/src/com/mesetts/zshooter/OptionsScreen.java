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

/**
 * Created by NoLight on 21.8.2016 г..
 */
public class OptionsScreen implements Screen {
    SpriteBatch batch;
    Game game;
    private Stage stage;
    private Slider soundSlider;
    private Slider musicSlider;
    private Skin sliderSkin;
    private TextureAtlas buttonsAtlas; //** image of buttons **//
    private BitmapFont font;
    private Skin buttonSkin; //** images are used as skins of the button **//
    private TextButton button;
    private Label labelSound;
    private Label.LabelStyle labelStyle;
    private Skin labelSkin;
    private Label labelMusic;
    OptionsScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {

        buttonsAtlas = new TextureAtlas("gdx-skins-master/Styles + Skins + Fonts/skin/star-soldier-ui.atlas"); //**button atlas image **//
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        font = new BitmapFont(Gdx.files.internal("all/new.fnt"), false); //** font **//

        TextureAtlas textureAtlas = new TextureAtlas("gdx-skins-master/Styles + Skins + Fonts/skin/star-soldier-ui.atlas");
        sliderSkin = new Skin();
        sliderSkin.addRegions(textureAtlas);

       // labelSkin.addRegions(textureAtlas);

        this.batch = new SpriteBatch();
        this.stage = new Stage(new FitViewport(1920, 1080), this.batch);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        font = new BitmapFont(Gdx.files.internal("gdx-skins-master/Styles + Skins + Fonts/skin/font-export.fnt"), false); //** font **//
        labelStyle = new Label.LabelStyle(font,font.getColor());

        TextButton.TextButtonStyle styleButton = new TextButton.TextButtonStyle(); //** Button properties **//
        styleButton.up = buttonSkin.getDrawable("button");
        styleButton.down = buttonSkin.getDrawable("button-selected");

        styleButton.font = font;
        font.getData().setScale(4);

        Slider.SliderStyle style = new Slider.SliderStyle();
        //style.knobDown = sliderSkin.getDrawable("slider-knob-pressed-c");
        style.knob = sliderSkin.getDrawable("slider-knob");
        //style.knobOver = sliderSkin.getDrawable("slider-knob-pressed");
        style.background = sliderSkin.getDrawable("slider-back");
        style.knobBefore = sliderSkin.getDrawable("slider");

        float screenWidth = stage.getViewport().getWorldWidth();
        float  screenHeight = stage.getViewport().getWorldHeight();

        button = new TextButton("About", styleButton);
        soundSlider = new Slider(1, 3, 0.05f, false, style);
        musicSlider = new Slider(1, 3, 0.05f, false, style);

        labelSound = new Label("Sound", labelStyle);
        labelMusic = new Label("Music", labelStyle);

        labelSound.setHeight(screenHeight/3);
        labelSound.setWidth(screenWidth/3);
        labelMusic.setHeight(screenHeight/3);
        labelMusic.setWidth(screenWidth/3);

        labelSound.setPosition(screenWidth/20,screenHeight/3);
        labelMusic.setPosition(screenWidth/20,screenHeight/25);

        musicSlider.setAnimateDuration(0.1f);
        soundSlider.setAnimateDuration(0.1f);

        soundSlider.setWidth(screenWidth/2);
        musicSlider.setWidth(screenWidth/2);

        soundSlider.setPosition(screenWidth / 2 - soundSlider.getWidth()/2, screenHeight/2);
        musicSlider.setPosition(screenWidth / 2 - musicSlider.getWidth()/2, screenHeight/5);

        //soundSlider.setHeight(screenHeight/2);
      //  musicSlider.setHeight(screenHeight/2);
        button.setHeight(screenHeight / 3); //** Button Height **//
        button.setWidth(screenWidth / 3); //** Button Width **//

        button.setPosition(screenWidth / 2 - button.getWidth()/2,screenHeight/1.6f);


        soundSlider.getStyle().knob.setMinHeight(80);
        soundSlider.getStyle().knob.setMinWidth(80);
        soundSlider.getStyle().knob.setBottomHeight(120);
        soundSlider.getStyle().knobBefore.setMinHeight(100);
        soundSlider.getStyle().knobBefore.setMinWidth(0);
        //soundSlider.getStyle().knobDown.setMinHeight(150);
       // soundSlider.getStyle().knobDown.setMinWidth(150);
      //  soundSlider.getStyle().knobOver.setMinHeight(150);
      //  soundSlider.getStyle().knobOver.setMinWidth(150);


        soundSlider.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        stage.addActor(soundSlider);
        stage.addActor(musicSlider);
        stage.addActor(button);
        stage.addActor(labelSound);
        stage.addActor(labelMusic);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenu(game));
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
