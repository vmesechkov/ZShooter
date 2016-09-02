package com.mesetts.zshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {

	private float legsPan;

// PlayerAnimation is a custom class representing two maps to different
// Animation objects containing references to TextureRegions
// stored as Key/Value pairs by String/Animation
// where String is the name of the animation, eg. "Run", "Idle"...

// Current animation time and frames
	private EntityAnimation legsAnimation;
	private EntityAnimation animation;
	private TextureRegion currentLegsFrame;

	public Player(World world, int tileSize) {
		super(world, tileSize, CollisionShape.CIRCLE);
	}

// Called when you want to change the current frames of the player...
// Must be the same as defined and registered in the PlayerAnimation object passed to this Player
// For example, ("Run", 0.17f) - Name of animation, Time passed since last frame drawn
	@Override
	public void animate(String animationName, float deltaTime) {
		stateTime += deltaTime;
		currentLegsFrame = legsAnimation.getAnimation(animationName).getKeyFrame(stateTime);
		currentFrame = animation.getAnimation(animationName).getKeyFrame(stateTime);
	}

// -------------------
// Setters and getters
// -------------------

// Animation
	public void setTorsoAnimation(EntityAnimation animation) {
		this.animation = animation;
	}

	public EntityAnimation getTorsoAnimation() { return this.animation; }

	public void setLegsAnimation(EntityAnimation animation) { this.legsAnimation = animation; }

	public EntityAnimation getLegsAnimation() { return legsAnimation; }

// Animation Frames references
	public TextureRegion getCurrentLegsFrame() {
		return currentLegsFrame;
	}

	public TextureRegion getCurrentTorsoFrame() {
		return currentFrame;
	}

// Legs orientation setter and getter
//
// The movement and pan setters and getters are inherited from Entity
	public void setLegsPan(final float legsPan) {
		this.legsPan = legsPan;
		if (this.legsPan > 360) {
			this.legsPan -= 360;
		}
		if (this.legsPan < 0) {
			this.legsPan += 360;
		}
	}

	public float getLegsPan() {	return legsPan; }

}
