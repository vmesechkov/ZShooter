package com.mesetts.zshooter;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

public class PlayerAnimation extends EntityAnimation {

	HashMap<String,Animation> legsAnimations;

	public PlayerAnimation() {
		legsAnimations = new HashMap<String, Animation>();
		animations = new HashMap<String, Animation>();
	}

	public void addLegsAnimation(final String name, Animation animation) {
		legsAnimations.put(name.toLowerCase(), animation);
	}

	public Animation getLegsAnimation(final String name) {
		return legsAnimations.get(name.toLowerCase());
	}

	public void addTorsoAnimation(final String name, Animation animation) {
		animations.put(name.toLowerCase(), animation);
	}

	public Animation getTorsoAnimation(final String name) {
		return animations.get(name.toLowerCase());
	}

}
