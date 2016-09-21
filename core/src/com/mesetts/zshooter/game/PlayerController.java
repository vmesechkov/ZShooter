package com.mesetts.zshooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mesetts.zshooter.*;

public class PlayerController {

	private com.mesetts.zshooter.game.entity.Player player;

	// Touchpads
	private Touchpad movementTouchpad;
	private Touchpad rotationTouchpad;
	private Touchpad.TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	private Drawable touchBackground;
	private Drawable touchKnob;

	// Movement vector from touchpads
	private Vector2 controllerMoveVec;

	private Vector2 movementKnobPos;
	private float movementKnobAngle;

	private Vector2 rotationKnobPos;
	private float rotationKnobAngle;

	public PlayerController(final com.mesetts.zshooter.game.entity.Player player, final Stage stage) {
		this.player = player;

		controllerMoveVec = new Vector2();
		movementKnobPos = new Vector2();
		rotationKnobPos = new Vector2();

		// Initialize touch pad style
		touchpadSkin = new Skin();														//Create a touchpad skin
		touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));	//Set background image
		touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));				//Set knob image

		touchBackground = touchpadSkin.getDrawable("touchBackground");					//Create Drawable's from TouchPad skin
		touchKnob = touchpadSkin.getDrawable("touchKnob");

		touchpadStyle = new Touchpad.TouchpadStyle();									//Create TouchPad Style
		touchpadStyle.background = touchBackground;										//Apply the Drawables to the TouchPad Style
		touchpadStyle.knob = touchKnob;

		// Create touch pads
		movementTouchpad = new Touchpad(10, touchpadStyle);								//Create new TouchPad with the created style
		movementTouchpad.setBounds(15, 15, 400, 400);									//setBounds(x,y,width,height)

		rotationTouchpad = new Touchpad(10, touchpadStyle);
		rotationTouchpad.setBounds(ZShooter.getScreenWidth() - 415, 15, 400, 400);

		// Add our touch pads to the stage on screen
		stage.addActor(movementTouchpad);
		stage.addActor(rotationTouchpad);
	}

	public float calculateAngleOfKnob(Vector2 knobPos) {
		return (float) (Math.toDegrees(Math.atan2(knobPos.y , knobPos.x)));
	}

	float knobAngleDifference;
	public boolean update() {

		if (player.getHealth() <= 0) {
			if (player.isAlive()) {
				player.setAlive(false);
				player.setStateTime(0f);
			}
			player.getBody().setLinearVelocity(0f,0f);
			player.getBody().setAngularVelocity(0f);
			if (!player.getTorsoAnimation().getAnimation("Death").isAnimationFinished(player.getStateTime())) {
				player.animateLegs("Death", Gdx.app.getGraphics().getDeltaTime());
				player.animateTorso("Death", Gdx.app.getGraphics().getDeltaTime());
			}
			else {
				return false;
			}
			return true;
		}

		movementKnobPos.x = movementTouchpad.getKnobPercentX();
		movementKnobPos.y = movementTouchpad.getKnobPercentY();

		rotationKnobPos.x = rotationTouchpad.getKnobPercentX();
		rotationKnobPos.y = rotationTouchpad.getKnobPercentY();

		// Rotate the legs
		if (movementKnobPos.x != 0 || movementKnobPos.y != 0) {
			movementKnobAngle = 90.0f + calculateAngleOfKnob(movementKnobPos);

			// If we're here, the player isnt standing, so animate him in Run animation
			if (Math.abs(movementKnobPos.x) >= 0.4 || Math.abs(movementKnobPos.y) >= 0.4) {
				player.animateLegs("Run", Gdx.app.getGraphics().getDeltaTime());

				// Move the player
				controllerMoveVec.set(movementKnobPos);
				controllerMoveVec.nor();
				controllerMoveVec.scl(2.5f);
				player.getBody().setLinearVelocity(controllerMoveVec);
			}
			else {
				player.animateLegs("Walk", Gdx.app.getGraphics().getDeltaTime());

				// Move the player
				controllerMoveVec.set(movementKnobPos);
				controllerMoveVec.nor();
				controllerMoveVec.scl(1.25f);
				player.getBody().setLinearVelocity(controllerMoveVec);
			}
		}
		else {
			movementKnobAngle = rotationKnobAngle;
			player.getBody().setLinearVelocity(0,0);

			player.animateLegs("Idle", Gdx.app.getGraphics().getDeltaTime());
		}
		player.setLegsPan(movementKnobAngle);

		// Rotate the torso
		if (rotationKnobPos.x != 0 || rotationKnobPos.y != 0) {
			rotationKnobAngle = 90.0f + calculateAngleOfKnob(rotationKnobPos);

			if (movementKnobPos.x != 0 || movementKnobPos.y != 0) {
				knobAngleDifference = rotationKnobAngle - movementKnobAngle;
				if (knobAngleDifference > 90) {
					movementKnobAngle += 180;
				}
				if (knobAngleDifference < -90) {
					movementKnobAngle -= 180;
				}
				movementKnobAngle %= 360;
				if (Math.abs(movementKnobPos.x) >= 0.4 || Math.abs(movementKnobPos.y) >= 0.4) {
					player.animateLegs("BackRun", Gdx.app.getGraphics().getDeltaTime());
					player.animateTorso("RunShoot", Gdx.app.getGraphics().getDeltaTime());
				}
				else {
					player.animateLegs("BackWalk", Gdx.app.getGraphics().getDeltaTime());
					player.animateTorso("WalkShoot", Gdx.app.getGraphics().getDeltaTime());
				}
			}
			else {
				player.animateTorso("Shoot", Gdx.app.getGraphics().getDeltaTime());
			}

			//player.fire(rotationKnobPos.x, rotationKnobPos.y, Gdx.app.getGraphics().getDeltaTime());
			player.fire(Gdx.app.getGraphics().getDeltaTime());
		}
		else {
			rotationKnobAngle = movementKnobAngle;
			if (movementKnobPos.x == 0 && movementKnobPos.y == 0) {
				player.animateTorso("Idle", Gdx.app.getGraphics().getDeltaTime());
			}
			else {
				if (Math.abs(movementKnobPos.x) >= 0.4 || Math.abs(movementKnobPos.y) >= 0.4) {
					player.animateTorso("Run", Gdx.app.getGraphics().getDeltaTime());
				}
				else {
					player.animateTorso("Walk", Gdx.app.getGraphics().getDeltaTime());
				}
			}
		}
		// Update player's pan
		player.setLegsPan(movementKnobAngle);
		player.setPan(rotationKnobAngle);

		// Update the weapon position
		player.updateWeapon();

		// TODO update all weapons in a list in InGameScreen
		player.getWeapon().update(Gdx.app.getGraphics().getDeltaTime(), player);

		return true;
	}

	public void dispose() {
		touchpadSkin.dispose();
	}
}
