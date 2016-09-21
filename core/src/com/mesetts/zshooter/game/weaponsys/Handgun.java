package com.mesetts.zshooter.game.weaponsys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Handgun extends Weapon {

	public Handgun(TextureRegion[] frames, float damage, float recoil, int magazineSize, float fireRate, float reloadTime, float projectileSpeed, float distanceOffset, float angleOffset, World world) {
		super(frames, damage, recoil, magazineSize, fireRate, reloadTime, projectileSpeed, distanceOffset, angleOffset, world);
	}

}
