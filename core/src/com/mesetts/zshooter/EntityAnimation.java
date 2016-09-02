package com.mesetts.zshooter;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

public class EntityAnimation {

	protected HashMap<String,Animation> animations;

	public EntityAnimation() {
		animations = new HashMap<String, Animation>();
	}

	public void addAnimation(final String name, Animation animation) {
		animations.put(name.toLowerCase(), animation);
	}

	public Animation getAnimation(final String name) {
		return animations.get(name.toLowerCase());
	}


}
