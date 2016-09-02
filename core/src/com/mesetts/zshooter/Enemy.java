package com.mesetts.zshooter;

import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Entity {

	public Enemy(World world, int tileSize) {
		super(world, tileSize, CollisionShape.CIRCLE);

		body.setLinearVelocity(1f, 0);
	}

}
