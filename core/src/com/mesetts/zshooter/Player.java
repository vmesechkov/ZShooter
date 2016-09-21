package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity {

	private float legsPan;
	private World world;

	private float runSpeed;
	private float walkSpeed;

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


		bulletRegion = TextureRegion.split(bulletTexture, 4, 4)[0][0];
	}

// Called when you want to change the current frames of the player...
// Must be the same as defined and registered in the PlayerAnimation object passed to this Player
// For example, ("Run", 0.17f) - Name of animation, Time passed since last frame drawn
	@Override
	public void animate(String animationName, float deltaTime) {
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


	public ArrayList<Projectile> bullets = new ArrayList<Projectile>();
	Projectile bullet;

	Texture bulletTexture = ZShooter.assets.get("data/bullet.png");
	TextureRegion bulletRegion;

	float x,y;
	float fireStateTime;
	int bulletsLeft = 30;

	public void fire(float velX, float velY, float delta) {
		fireStateTime += delta;
		if (bulletsLeft <= 0) {
			if (fireStateTime < 2) {
				animateTorso("Reload", delta);
				return;
			}
			fireStateTime = 0;
			bulletsLeft = 30;
			return;
		}

		if (fireStateTime > 0.033f) {
			bullet = new Projectile(world);
			x = (float) (body.getPosition().x + 0.4 * MathUtils.cosDeg(this.getPan() - 90f + ((float)Math.random() * 30f - 15f)));
			y = (float) (body.getPosition().y + 0.4 * MathUtils.sinDeg(this.getPan() - 90f + ((float)Math.random() * 30f - 15f)));
			bullet.getBody().setTransform(x, y, (float) Math.toRadians(this.getPan()));
			bullet.speed.set(velX, velY);
			bullet.speed.nor();
			bullet.speed.scl(10);
			bullet.getBody().setLinearVelocity(bullet.speed);
			bullet.getInitialPosition().set(body.getPosition());
			bullet.setDamage(10);
			bullets.add(bullet);
			fireStateTime = 0;

			bulletsLeft -= 1;
			//Gdx.app.log("Player: ", "This pan: " + this.getPan());
		}
		Gdx.app.log("Player: ", "Bullet count: " + bullets.size());
	}


	boolean isReloading;
	public void reload() {

	}
}
