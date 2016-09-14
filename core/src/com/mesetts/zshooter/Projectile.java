package com.mesetts.zshooter;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;

public class Projectile {

	private Body body;
	private Vector2 initialPosition = new Vector2();
	private World world;
	Vector2 speed = new Vector2();

	boolean isFlaggedForRemoval;

	private float damage;

	Projectile(World world) {
		this.world = world;

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

	public void update(Player player, Iterator<Projectile> it) {
		if (initialPosition.dst2(body.getPosition()) > 900 || isFlaggedForRemoval) {
			world.destroyBody(this.body);
			it.remove();
		}
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getDamage() {
		return damage;
	}

	public Body getBody() {
		return body;
	}

	public Vector2 getInitialPosition() {
		return initialPosition;
	}
}
