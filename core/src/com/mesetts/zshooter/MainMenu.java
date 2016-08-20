package com.mesetts.zshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.*;


public class MainMenu implements Screen {
    SpriteBatch batch;
    Game game;
    private Stage stage;
    private TextureAtlas buttonsAtlas; //** image of buttons **//
    private BitmapFont font;
    private Skin buttonSkin; //** images are used as skins of the button **//
    private TextButton button;
    private TextButton button1;


    MainMenu(Game game){
        this.game = game;
    }
    @Override
    public void show() {


        buttonsAtlas = new TextureAtlas("all/button.pack"); //**button atlas image **//
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        font = new BitmapFont(Gdx.files.internal("all/new.fnt"), false); //** font **//
        this.batch = new SpriteBatch();
        this.stage = new Stage(new FitViewport(1920, 1080), this.batch);
        Gdx.input.setInputProcessor(stage);


        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("buttonOff");
        style.down = buttonSkin.getDrawable("buttonOn");

        style.font = font;
        font.getData().setScale(5);
        float screenWidth = stage.getViewport().getWorldWidth();
        float  screenHeight = stage.getViewport().getWorldHeight();

        button = new TextButton("Rjhghjgj", style);
        button1 = new TextButton("asdasd", style);
        //** Button text and style **//
        button.setHeight(screenHeight / 2); //** Button Height **//
        button.setWidth(screenWidth / 2); //** Button Width **//

        button1.setHeight(screenHeight / 2); //** Button Height **//
        button1.setWidth(screenWidth / 2); //** Button Width **//

        button.setPosition(screenWidth / 2 - button.getWidth()/2, screenHeight / 7 );
        button1.setPosition(screenWidth / 2 - button.getWidth()/2, screenHeight/ 2);


        button.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//


                // TODO Auto-generated method stub


                return true;

            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new InGameScreen(game));
                    dispose();
                 }
        });

//    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//        Gdx.app.log("my app", "Rggggggeleased");
//
//        ///and level
//        ZShooter.setScreen(new MyNextScreen(game));
//
//        dispose();
//
//    }

//
//    MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
//    moveAction.setPosition(0,0);
//    moveAction.setDuration(.5f);
//    button.addAction(moveAction);
        stage.addActor(button1);
        stage.addActor(button);

    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        buttonSkin.dispose();
        buttonsAtlas.dispose();
        batch.dispose();
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        //this.batch.begin();

        // this.batch.end();
        stage.draw();

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
}

