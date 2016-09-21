package com.mesetts.zshooter.game.weaponsys;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

public class Projectile implements Pool.Poolable {

	private Body body;
	private Vector2 initialPosition;
	private Vector2 velocity;
	private boolean alive;
	private float damage;
	private TextureRegion texture;

	Projectile(World world) {
		this.alive = false;
		this.initialPosition = new Vector2();
		this.velocity = new Vector2();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		body = world.createBody(bodyDef);			// Create a body by the definition above
		body.setBullet(true);						// Activate continuous collision detection for this body

		FixtureDef fixtureDef = new FixtureDef();
		Shape shape = new CircleShape();
		shape.setRadius(0.03125f);

		fixtureDef.shape = shape;
		fixtureDef.density = 1062f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.3f;
		fixtureDef.filter.categoryBits = 0x0002;
		fixtureDef.filter.maskBits = 0x0001;

		Fixture fixture = body.createFixture(fixtureDef);				// Create the fixture to the body from the fixture definition
		fixture.setUserData(this);
		body.setUserData(this);						// Set a reference back to this object from the physical body

		shape.dispose();							// Get rid of the shape
	}

	public void init(TextureRegion texture, float posX, float posY, float pan, float velX, float velY, float damage) {
		this.texture = texture;
		this.damage = damage;
		body.setTransform(posX, posY, (float) Math.toRadians(pan));
		initialPosition.set(body.getPosition());
		velocity.set(velX, velY);
		alive = true;
	}

	@Override
	public void reset() {
		velocity.set(0,0);
		body.setLinearVelocity(velocity);
		body.getPosition().set(-999, -999);
		alive = false;
	}

	public void update() {
		body.setLinearVelocity(velocity);
		if (initialPosition.dst2(body.getPosition()) > 900) {
			alive = false;
		}
	}

	public void remove() {
		alive = false;
	}

	public boolean isAlive() { return alive; }

	public float getDamage() {
		return damage;
	}

	public Body getBody() {
		return body;
	}

	public TextureRegion getTexture() { return texture; }
}
