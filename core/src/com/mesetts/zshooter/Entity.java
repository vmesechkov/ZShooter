package com.mesetts.zshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Entity {

	protected Vector2 position;
	protected float pan;

	// Default constructor
	public Entity() {
		position = new Vector2();
	}

	public Entity(Vector2 position, float pan) {
		this();

		this.position.set(position);
		this.pan = pan;
	}

	public Entity(int x, int y, float pan) {
		this();

		this.position.set( x, y);
		this.pan = pan;
	}

// Position setters and getter
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}

	public void setPosition(int x, int y) {
		this.position.set(x, y);
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

// Pan setter and getter
	public void setPan(float pan) {
		this.pan = pan;
	}

	public float getPan() {
		return pan;
	}

}
