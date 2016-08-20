package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class PlayerController {

    Player player;

    private Touchpad movementTouchpad;
    private Touchpad rotationTouchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    float movementKnobX;
    float movementKnobY;
    float movementKnobAngle;

    float rotationKnobX;
    float rotationKnobY;
    float rotationKnobAngle;

    PlayerController(final Player player, final Stage stage) {
        this.player = player;

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        movementTouchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        movementTouchpad.setBounds(15, 15, 400, 400);

        rotationTouchpad = new Touchpad(10, touchpadStyle);
        rotationTouchpad.setBounds(ZShooter.screenWidth - 415, 15, 400, 400);

        stage.addActor(movementTouchpad);
        stage.addActor(rotationTouchpad);
    }

    float convToDegreesMultiplier = (float)(180 / Math.PI);

    public float calculateAngleOfKnob(final float valueX, final float valueY) {
        return (float) ((Math.atan2(valueY , valueX)) * convToDegreesMultiplier);
    }

    public boolean update() {
        movementKnobX = movementTouchpad.getKnobPercentX();
        movementKnobY = movementTouchpad.getKnobPercentY();

        rotationKnobX = rotationTouchpad.getKnobPercentX();
        rotationKnobY = rotationTouchpad.getKnobPercentY();

        // Move the player
        player.move(movementKnobX, movementKnobY);

        // Rotate the legs
        if (movementKnobX != 0) {
            movementKnobAngle = 90.0f + calculateAngleOfKnob(movementKnobX, movementKnobY);
        }
        else {
            if (movementKnobY == 0) {
                movementKnobAngle = rotationKnobAngle;
            }
        }
        player.setLegsPan(movementKnobAngle);

        // Rotate the torso
        if (rotationKnobX != 0) {
            rotationKnobAngle = 90.0f + calculateAngleOfKnob(rotationKnobX, rotationKnobY);
        }
        else {
            if (rotationKnobY == 0) {
                rotationKnobAngle = movementKnobAngle;
            }
        }
        player.setPan(rotationKnobAngle);

        if (movementKnobX == 0 && movementKnobY == 0) {
            player.animate(Player.PlayerAnimation.IDLE);
            return false;
        }
        player.animate(Player.PlayerAnimation.RUN);
        return true;
    }

    public void dispose() {
        touchpadSkin.dispose();
    }
}
