package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class PlayerAnimation {

	HashMap<String,Animation> legsAnimations;
	HashMap<String,Animation> torsoAnimations;

	public PlayerAnimation() {
		legsAnimations = new HashMap<String, Animation>();
		torsoAnimations = new HashMap<String, Animation>();
	}

	public void addLegsAnimation(final String name, Animation animation) {
		legsAnimations.put(name.toLowerCase(), animation);
	}

	public Animation getLegsAnimation(final String name) {
		return legsAnimations.get(name.toLowerCase());
	}

	public void addTorsoAnimation(final String name, Animation animation) {
		torsoAnimations.put(name.toLowerCase(), animation);
	}

	public Animation getTorsoAnimation(final String name) {
		return torsoAnimations.get(name.toLowerCase());
	}

}
