package com.mesetts.zshooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mesetts.zshooter.game.entity.Entity;
import com.mesetts.zshooter.game.weaponsys.Projectile;
import com.mesetts.zshooter.game.weaponsys.ProjectileManager;

public class ZContactListener implements ContactListener {

	Fixture fixA, fixB;
	Object fixAData, fixBData;
	Entity e;
	Projectile p;
	World world;

	public ZContactListener(World world) {
		this.world = world;
	}

	@Override
	public void beginContact(Contact contact) {
		fixA = contact.getFixtureA();
		fixAData = fixA.getUserData();
		fixB = contact.getFixtureB();
		fixBData = fixB.getUserData();

		if (fixAData instanceof Projectile) {
			ProjectileManager.getInstance(world).removeBullet((Projectile)fixAData);
			if (fixBData instanceof Entity) {
				e = ((Entity) fixBData);
				e.setHealth(e.getHealth() - ((Projectile) fixAData).getDamage());
				Gdx.app.log("Contact ", "Enemy health: " + e.getHealth());
			}
			return;
		}

		if (fixBData instanceof Projectile) {
			ProjectileManager.getInstance(world).removeBullet((Projectile)fixBData);
			if (fixAData instanceof Entity) {
				e = ((Entity) fixAData);
				e.setHealth(e.getHealth() - ((Projectile) fixBData).getDamage());
				Gdx.app.log("Contact ", "Enemy health: " + e.getHealth());
			}
		}
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
