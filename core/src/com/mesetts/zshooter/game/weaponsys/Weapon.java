package com.mesetts.zshooter.game.weaponsys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mesetts.zshooter.game.entity.Player;

public abstract class Weapon {

	protected enum State {
		IDLE,
		FIRE,
		FIRE_IDLE,
		RELOADING,
		DROPPING,
		DROPPED
	}

	protected float damage;
	protected float fireRate;
	protected float pan;
	// Bullet velocity (indicating shoot direction
	protected float velX;
	protected float velY;
	protected Vector2 position;
	protected TextureRegion currentFrame;
	protected TextureRegion[] frames;
	protected TextureRegion[] dropFrames;
	protected Animation dropAnimation;
	protected World world;
	protected float distanceOffset;
	protected float angleOffset;
	protected State state = State.IDLE;

	// TODO initialize bullets and magazine size
	protected Weapon(TextureRegion[] frames, float damage, float recoil, int magazineSize, float fireRate, float reloadTime, float projectileSpeed, float distanceOffset, float angleOffset, World world) {
		this.frames = frames;

		this.currentFrame = frames[0];

		dropFrames = new TextureRegion[frames.length - 2];
		for (int i = 1, j = 0; i < frames.length - 1; i++, j++) {
			dropFrames[j] = frames[i];
		}
		dropAnimation = new Animation(0.05f, dropFrames);
		dropAnimation.setPlayMode(Animation.PlayMode.NORMAL);

		this.world = world;
		this.damage = damage;
		this.magazineSize = magazineSize;
		this.fireRate = fireRate;
		this.fireIdleTime = 1 / fireRate;
		this.projectileSpeed = projectileSpeed;
		this.reloadTime = reloadTime;
		this.position = new Vector2();
		this.bulletCount = magazineSize;
		this.distanceOffset = distanceOffset;
		this.angleOffset = angleOffset;
		this.recoil = recoil;
		this.recoilHalf = recoil / 2;
	}

	protected float stateTime;
	protected float reloadTime;
	protected float fireIdleTime;
	protected int bulletCount;
	protected int magazineSize;
	protected float projectileSpeed;
	protected float recoil;
	protected float recoilHalf;
	float x,y;

	public void drop(float delta) {
		stateTime = 0;
		state = State.DROPPING;
	}

	public void fire(float delta, Player player) {
		if (state == State.IDLE) {
			if (bulletCount > 0) {
				state = State.FIRE;
			}
			else {
				stateTime = 0;
				state = State.RELOADING;
			}
			update(delta, player);
		}
	}

	public void update(float delta, Player player) {

		if (state == State.IDLE) {
			currentFrame = frames[0];
			return;
		}

		if (state == State.FIRE) {
			x = position.x + velX;
			y = position.y + velY;
			ProjectileManager.getInstance(world).createBullet(x, y, this.getPan() + ((float)Math.random() * recoil - recoilHalf), 10, projectileSpeed);

			bulletCount -= 1;

			stateTime = 0;
			state = State.FIRE_IDLE;
		}

		if (state == State.FIRE_IDLE) {
			stateTime += delta;
			if (stateTime > fireIdleTime) {
				stateTime = 0;
				state = State.IDLE;
			}
		}

		if (state == State.RELOADING) {
			stateTime += delta;
			if (stateTime > reloadTime) {
				bulletCount = magazineSize;
				stateTime = 0;
				state = State.IDLE;
			}
		}

		if (state == State.DROPPING) {
			stateTime += delta;
			currentFrame = dropAnimation.getKeyFrame(stateTime);
			if (dropAnimation.isAnimationFinished(stateTime)) {
				stateTime = 0;
				state = State.DROPPED;
			}
		}

		if (state == State.DROPPED) {
			currentFrame = frames[frames.length - 1];
			if (position.dst(player.getPosition()) <= 0.5) {
				player.getWeapon().discard();
				player.setWeapon(this);
				state = State.IDLE;
			}
		}
	}

	private boolean discarded;
	public boolean isDiscarded() {
		return discarded;
	}
	public void discard() {
		discarded = true;
	}

	public void setPan(float pan) {
		this.pan = pan;
	}

	public float getPan() {
		return pan;
	}

	public void setPosition(Vector2 position) {
		this.position.set(position);
	}

	public void setPosition(float x, float y) {
		this.position.set(x,y);
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getDistanceOffset() {
		return distanceOffset;
	}

	public float getAngleOffset() {
		return angleOffset;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public State getState() {
		return state;
	}
}
