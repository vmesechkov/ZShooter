package com.mesetts.zshooter.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mesetts.zshooter.game.weaponsys.Handgun;
import com.mesetts.zshooter.game.weaponsys.Weapon;

public class Player extends com.mesetts.zshooter.game.entity.Entity {

	private float legsPan;
	private World world;

	private float runSpeed;
	private float walkSpeed;

	private Weapon weapon;

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
		this.world = world;
		this.health = 100;
		this.alive = true;

		Texture weaponsSheet = new Texture(Gdx.files.internal("data/weapons_sheet_128.png"));
		TextureRegion[][] weaponsFrames = TextureRegion.split(weaponsSheet, weaponsSheet.getWidth() / 14, weaponsSheet.getHeight() / 3);
		this.weapon = new Handgun(weaponsFrames[0], 25, 15, 12, 6, 1, 10, 0.4f, 13f, world);
	}

// Called when you want to change the current frames of the player...
// Must be the same as defined and registered in the PlayerAnimation object passed to this Player
// For example, ("Run", 0.17f) - Name of animation, Time passed since last frame drawn
	@Override
	public void animate(String animationName, float deltaTime) {
		if (lastAnimationName == null || !lastAnimationName.equals(animationName)) {
			lastAnimationName = animationName;
			stateTime = 0;
		}
		stateTime += deltaTime;
		currentFrame = animation.getAnimation(animationName).getKeyFrame(stateTime);
	}

	//TODO two separate StateTimes for Legs and Torso
	public void animateLegs(String animationName, float deltaTime) {
		currentLegsFrame = legsAnimation.getAnimation(animationName).getKeyFrame(stateTime);
	}

	public void animateTorso(String animationName, float deltaTime) {
		animate(animationName, deltaTime);
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

	public void fire(float delta) {
		// TODO reloading
		weapon.fire(delta, this);
	}

	float weapX, weapY;
	public void updateWeapon() {
		weapX = (float)(this.getX() + weapon.getDistanceOffset() * Math.cos(body.getAngle() - Math.toRadians(90f + weapon.getAngleOffset())));
		weapY = (float)(this.getY() + weapon.getDistanceOffset() * Math.sin(body.getAngle() - Math.toRadians(90f + weapon.getAngleOffset())));
		weapon.setPosition(weapX, weapY);
		weapon.setPan(pan);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	boolean isReloading;
	public void reload() {

	}

	public float getStateTime() {
		return stateTime;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	private boolean alive;

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
