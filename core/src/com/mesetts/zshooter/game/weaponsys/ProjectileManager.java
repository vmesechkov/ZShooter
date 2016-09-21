package com.mesetts.zshooter.game.weaponsys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ProjectileManager {
	private static ProjectileManager manager;

	private World world;

	private final Array<Projectile>	activeProjectiles	= new Array<Projectile>();
	private final Pool<Projectile> 	projectilePool		= new Pool<Projectile>() {
		@Override
		protected Projectile newObject() {
			return new Projectile(world);
		}
	};

	Texture projectileSheet = new Texture(Gdx.files.internal("data/bullet.png"));
	public TextureRegion bulletRegion;

	private ProjectileManager(World world) {
		this.world = world;

		bulletRegion = TextureRegion.split(projectileSheet, 4, 4)[0][0];
	}

	float velX;
	float velY;
	public void createBullet(float posX, float posY, float pan, float damage, float speed) {
		velX = speed * MathUtils.cosDeg(pan - 90f);
		velY = speed * MathUtils.sinDeg(pan - 90f);
		// if you want to spawn a new bullet:
		Projectile projectile = projectilePool.obtain();
		projectile.init(bulletRegion, posX, posY, pan, velX, velY, damage);
		activeProjectiles.add(projectile);
	}

	public void removeBullet(Projectile projectile) {
		projectile.remove();
	}

	public void update() {
		Projectile projectile;
		int len = activeProjectiles.size;
		for (int i = len; --i >= 0;) {
			projectile = activeProjectiles.get(i);
			projectile.update();
			if (!projectile.isAlive()) {
				activeProjectiles.removeIndex(i);
				projectilePool.free(projectile);
			}
		}
	}

	public Array<Projectile> getBullets() {
		return activeProjectiles;
	}

	public static ProjectileManager getInstance(World world) {
		if (manager == null) {
			manager = new ProjectileManager(world);
		}
		return manager;
	}
}
