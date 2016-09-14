package com.mesetts.zshooter;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ZContactListener implements ContactListener {

	Fixture fixA, fixB;
	Object fixAData, fixBData;

	@Override
	public void beginContact(Contact contact) {
		fixA = contact.getFixtureA();
		fixAData = fixA.getUserData();

		if (fixAData instanceof Projectile) {
			((Projectile) fixAData).isFlaggedForRemoval = true;
			if (fixBData instanceof Entity) {
				((Entity) fixBData).health -= ((Projectile) fixAData).getDamage();
			}
			return;
		}

		fixB = contact.getFixtureB();
		fixBData = fixB.getUserData();
		if (fixBData instanceof Projectile) {
			((Projectile) fixBData).isFlaggedForRemoval = true;
			if (fixAData instanceof Entity) {
				((Entity) fixAData).health -= ((Projectile) fixBData).getDamage();
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
