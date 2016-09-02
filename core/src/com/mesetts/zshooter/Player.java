package com.mesetts.zshooter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.PointLight;

public class Player extends Entity {

	private float legsPan;

	// Physics body object of this player registered in the physics world
	Body body;

// PlayerAnimation is a custom class representing two maps to different
// Animation objects containing references to TextureRegions
// stored as Key/Value pairs by String/Animation
// where String is the name of the animation, eg. "Run", "Idle"...

// Current animation time and frames
	PlayerAnimation animation;
	float stateTime;
	private TextureRegion currentLegsFrame;
	private TextureRegion currentTorsoFrame;

	// We must provide the tile size to accurately set player's physical body position
	private int tileSize;

	public Player(World world, int tileSize) {
		this.tileSize = tileSize;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(position.x / tileSize, position.y / tileSize);

		body = world.createBody(bodyDef);

		CircleShape circle = new CircleShape();
		circle.setRadius(0.25f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		body.createFixture(fixtureDef);
		body.setUserData(this);

		circle.dispose();
	}

// Called when you want to change the current frames of the player...
// Must be the same as defined and registered in the PlayerAnimation object passed to this Player
// For example, ("Run", 0.17f) - Name of animation, Time passed since last frame drawn
	public void animate(String animationName, float deltaTime) {
		stateTime += deltaTime;
		currentLegsFrame = animation.getLegsAnimation(animationName).getKeyFrame(stateTime);
		currentTorsoFrame = animation.getTorsoAnimation(animationName).getKeyFrame(stateTime);
	}

// -------------------
// Setters and getters
// -------------------

// Animation
	public void setAnimation(PlayerAnimation animation) {
		this.animation = animation;
	}

	public PlayerAnimation getAnimation() {
		return animation;
	}

// Animation Frames references
	public TextureRegion getCurrentLegsFrame() {
		return currentLegsFrame;
	}

	public TextureRegion getCurrentTorsoFrame() {
		return currentTorsoFrame;
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

	public float getLegsPan() {
		return legsPan;
	}

	@Override
	public void setPosition(Vector2 position) {
		super.setPosition(position);
		body.setTransform(position.x / tileSize, position.y / tileSize, body.getAngle());
	}

	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		body.setTransform(x / tileSize, y / tileSize, body.getAngle());
	}
}
