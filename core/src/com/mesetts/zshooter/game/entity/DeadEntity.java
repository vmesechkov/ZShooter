package com.mesetts.zshooter.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DeadEntity {

	public TextureRegion texture;
	public float posX;
	public float posY;
	public float pan;


	public DeadEntity(TextureRegion texture, float posX, float posY, float pan) {
		this.texture = texture;
		this.posX = posX;
		this.posY = posY;
		this.pan = pan;
	}

}
