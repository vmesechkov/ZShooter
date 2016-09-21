package com.mesetts.zshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Entity {

	enum CollisionShape {
		CIRCLE, BOX
	}

	protected Vector2 position;
	protected float pan;

	protected float health;

	// Physics body object of this entity registered in the physics world
	protected Body body;
	protected World world;

// EntityAnimation is a custom class representing a map of
// Animation objects containing references to TextureRegions
// stored as Key/Value pairs by String/Animation
// where String is the name of the animation, eg. "Run", "Idle"...

	// Current animation time and frames
	protected EntityAnimation animation;
	protected float stateTime;
	protected TextureRegion currentFrame;

	// We must provide the tile size to accurately set enemy's physical body position
	protected int tileSize;

	// Default constructor
	public Entity(final World world, final int tileSize, CollisionShape collisionShape) {
		this.world = world;

		this.tileSize = tileSize;

		position = new Vector2();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		body = world.createBody(bodyDef);			// Create a body by the definition above

		FixtureDef fixtureDef = new FixtureDef();	// Create a fixture definition
		Shape shape;								// Fill with desired shape in the switch below

		switch (collisionShape) {
			case CIRCLE:
				shape = new CircleShape();			// Fill the shape with a circle
				shape.setRadius(0.25f);				// Set its radius to 0.25 of a tile
				break;
			case BOX:
			default:
				shape = new PolygonShape();			// Fill the shape with a box
				((PolygonShape)shape).setAsBox(0.25f, 0.25f);	// Set its size to 0.25 of a tile
				break;
		}

		fixtureDef.shape = shape;
		fixtureDef.density = 1062f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.filter.categoryBits = 0x0001;
		fixtureDef.filter.maskBits = 0x0001 | 0x0002;

		Fixture fixture = body.createFixture(fixtureDef);				// Create the fixture to the body from the fixture definition
		fixture.setUserData(this);
		body.setUserData(this);						// Set a reference back to this object from the physical body

		shape.dispose();							// Get rid of the shape
	}

// Called when you want to change the current frame of the enemy...
// Must be the same as defined and registered in the EnemyAnimation object passed to this Enemy
// For example, ("Run", 0.17f) - Name of animation, Time passed since last frame drawn
	public void animate(String animationName, float deltaTime) {
		stateTime += deltaTime;
		currentFrame = animation.getAnimation(animationName).getKeyFrame(stateTime);
	}

	// Animation
	public void setAnimation(EntityAnimation animation) {
		this.animation = animation;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}


// Position setters and getter
	public void setPosition(Vector2 position) {
		if (body != null) {
			body.setTransform(position.x, position.y, body.getAngle());
		}
		this.position.set(position);
	}

	public void setPosition(float x, float y) {
		if (body != null) {
			body.setTransform(x, y, body.getAngle());
		}
		position.set(x, y);
	}

	public Vector2 getPosition() {
		if (body != null) {
			return body.getPosition();
		}
		else {
			return position;
		}
	}

	public float getX() {
		if (body != null) {
			return body.getPosition().x;
		}
		else {
			return position.x;
		}
	}

	public float getY() {
		if (body != null) {
			return body.getPosition().y;
		}
		else {
			return position.y;
		}
	}

// Pan setter and getter
	public void setPan(float pan) {
		pan %= 360;
		body.setTransform(body.getPosition(), (float)Math.toRadians(pan));
		this.pan = pan;
	}

	public float getPan() {
		if (body != null) {
			pan = (float) Math.toDegrees(body.getAngle());
		}
		return pan;
	}

	public void setBodyToBox(float width, float height) {
		Array<Fixture> fixtures = body.getFixtureList();
		for (int i = 0; i < fixtures.size; i++) {
			body.destroyFixture(fixtures.get(i));
		}

		PolygonShape box = new PolygonShape();
		box.setAsBox(width, height);
		// Its possible this wont work if body.createFixture expects a rotated fixture
		// ...in this case, just add center Vector2 and pan angle to the above statement...

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		body.createFixture(fixtureDef);				// Create the fixture to the body from the fixture definition
		box.dispose();
	}

	public void setBodyToCircle(float radius) {
		Array<Fixture> fixtures = body.getFixtureList();
		for (int i = 0; i < fixtures.size; i++) {
			body.destroyFixture(fixtures.get(i));
		}

		CircleShape circle = new CircleShape();
		circle.setRadius(radius);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;

		body.createFixture(fixtureDef);				// Create the fixture to the body from the fixture definition
		circle.dispose();
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getHealth() {
		return health;
	}
}
