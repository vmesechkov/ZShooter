package com.mesetts.zshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mesetts.zshooter.game.entity.DeadEntity;
import com.mesetts.zshooter.game.entity.Player;
import com.mesetts.zshooter.game.entity.Entity;
import com.mesetts.zshooter.game.weaponsys.Projectile;
import com.mesetts.zshooter.game.weaponsys.ProjectileManager;
import com.mesetts.zshooter.game.TileMap;
import com.mesetts.zshooter.game.weaponsys.Weapon;

public class ZBatch extends SpriteBatch {

	private TextureRegion tmpRegion;

	private Vector2 drawOffset;
	private Vector2 cameraOffset;
	private Vector2 screenSize;
	private Vector2 screenCenterOffset;

	public void setCameraOffset(Vector2 cameraOffset) {
		this.cameraOffset.set(cameraOffset);
	}

	public void setScreenSize(Vector2 screenSize) {
		this.screenSize.set(screenSize);

		screenCenterOffset.set(screenSize);
		screenCenterOffset.x = screenCenterOffset.x / 2;
		screenCenterOffset.y = screenCenterOffset.y / 2;
	}

	public ZBatch() {
		drawOffset = new Vector2();
		cameraOffset = new Vector2();
		screenSize = new Vector2();
		screenCenterOffset = new Vector2();

		lowerBounds = new Vector2();
		upperBounds = new Vector2();
		tileScreenPos = new Vector2();
	}

	// Temp holders for faster rendering
	private int entityImageWidth;
	private int entityImageHeight;
	private int entityImageHalfWidth;
	private int entityImageHalfHeight;
	private Weapon weapon;
	public void draw(Player player) {
		// Draw the legs
		tmpRegion = player.getCurrentLegsFrame();

		// Set image size
		entityImageWidth = tmpRegion.getRegionWidth();
		entityImageHeight = tmpRegion.getRegionHeight();
		entityImageHalfWidth = entityImageWidth / 2;
		entityImageHalfHeight = entityImageHeight / 2;

		super.draw(		tmpRegion,
						player.getX() * ZShooter.WORLD_TILE_SIZE - entityImageHalfWidth - cameraOffset.x + screenCenterOffset.x, player.getY() * ZShooter.WORLD_TILE_SIZE - entityImageHalfHeight - cameraOffset.y + screenCenterOffset.y,
						entityImageHalfWidth, entityImageHalfHeight,
						tmpRegion.getRegionWidth(), tmpRegion.getRegionHeight(),
						1, 1,
						player.getLegsPan());

//		if (player.getWeapon() != null) {
//			weapon = player.getWeapon();
//
//			tmpRegion = weapon.getCurrentFrame();
//
//			entityImageWidth = tmpRegion.getRegionWidth();
//			entityImageHeight = tmpRegion.getRegionHeight();
//			entityImageHalfWidth = entityImageWidth / 2;
//			entityImageHalfHeight = entityImageHeight / 2;
//
//			super.draw(		tmpRegion,
//					weapon.getX() * ZShooter.WORLD_TILE_SIZE - entityImageHalfWidth - cameraOffset.x + screenCenterOffset.x, weapon.getY() * ZShooter.WORLD_TILE_SIZE - entityImageHalfHeight - cameraOffset.y + screenCenterOffset.y,
//					entityImageHalfWidth, entityImageHalfHeight,
//					entityImageWidth, entityImageHeight,
//					1, 1,
//					weapon.getPan());
//		}

		// Draw the torso
		tmpRegion = player.getCurrentTorsoFrame();
		entityImageWidth = tmpRegion.getRegionWidth();
		entityImageHeight = tmpRegion.getRegionHeight();
		entityImageHalfWidth = entityImageWidth / 2;
		entityImageHalfHeight = entityImageHeight / 2;
		super.draw(		tmpRegion,
						player.getX() * ZShooter.WORLD_TILE_SIZE - entityImageHalfWidth - cameraOffset.x + screenCenterOffset.x, player.getY() * ZShooter.WORLD_TILE_SIZE - entityImageHalfHeight - cameraOffset.y + screenCenterOffset.y,
						entityImageHalfWidth, entityImageHalfHeight,
						entityImageWidth, entityImageHeight,
						1, 1,
						player.getPan());

//		tmpRegion = player.bulletRegion;
//		com.mesetts.zshooter.game.Projectile p;
//		for (Iterator<Projectile> it = player.bullets.iterator(); it.hasNext(); ) {
//			p = it.next();
//			if (p != null) {
//				super.draw(tmpRegion,
//						p.getBody().getPosition().x * 128 - 4 - cameraOffset.x + screenCenterOffset.x, p.getBody().getPosition().y * 128 - 4 - cameraOffset.y + screenCenterOffset.y,
//						4, 4,
//						8, 8,
//						1, 1,
//						p.getBody().getAngle());
//				p.update(player, it);
//			}
//		}
	}

	public void draw(Weapon weapon) {
		tmpRegion = weapon.getCurrentFrame();

		entityImageWidth = tmpRegion.getRegionWidth();
		entityImageHeight = tmpRegion.getRegionHeight();
		entityImageHalfWidth = entityImageWidth / 2;
		entityImageHalfHeight = entityImageHeight / 2;

		super.draw(		tmpRegion,
				weapon.getX() * ZShooter.WORLD_TILE_SIZE - entityImageHalfWidth - cameraOffset.x + screenCenterOffset.x, weapon.getY() * ZShooter.WORLD_TILE_SIZE - entityImageHalfHeight - cameraOffset.y + screenCenterOffset.y,
				entityImageHalfWidth, entityImageHalfHeight,
				entityImageWidth, entityImageHeight,
				1, 1,
				weapon.getPan());
	}

	Projectile p;
	public void draw(ProjectileManager manager) {

		for (Projectile projectile : manager.getBullets()) {
			p = projectile;
			super.draw(p.getTexture(),
					p.getBody().getPosition().x * ZShooter.WORLD_TILE_SIZE - 4 - cameraOffset.x + screenCenterOffset.x, p.getBody().getPosition().y * ZShooter.WORLD_TILE_SIZE - 4 - cameraOffset.y + screenCenterOffset.y,
					4, 4,
					8, 8,
					1, 1,
					p.getBody().getAngle());
		}
	}

	// Entity rendering
	public void draw(Entity entity) {
		// Draw the legs
		tmpRegion = entity.getCurrentFrame();

		if (tmpRegion != null) {
			// Set image size
			entityImageWidth = tmpRegion.getRegionWidth();
			entityImageHeight = tmpRegion.getRegionHeight();
			entityImageHalfWidth = entityImageWidth / 2;
			entityImageHalfHeight = entityImageHeight / 2;

			super.draw(tmpRegion,
					entity.getX() * ZShooter.WORLD_TILE_SIZE - entityImageHalfWidth - cameraOffset.x + screenCenterOffset.x, entity.getY() * ZShooter.WORLD_TILE_SIZE - entityImageHalfHeight - cameraOffset.y + screenCenterOffset.y,
					entityImageHalfWidth, entityImageHalfHeight,
					tmpRegion.getRegionWidth(), tmpRegion.getRegionHeight(),
					1, 1,
					entity.getPan());
		}
	}

	public void draw(DeadEntity entity) {

		tmpRegion = entity.texture;
		entityImageWidth = tmpRegion.getRegionWidth();
		entityImageHeight = tmpRegion.getRegionHeight();
		entityImageHalfWidth = entityImageWidth / 2;
		entityImageHalfHeight = entityImageHeight / 2;

		super.draw(tmpRegion,
				entity.posX * ZShooter.WORLD_TILE_SIZE - entityImageHalfWidth - cameraOffset.x + screenCenterOffset.x, entity.posY * ZShooter.WORLD_TILE_SIZE - entityImageHalfHeight - cameraOffset.y + screenCenterOffset.y,
				entityImageHalfWidth, entityImageHalfHeight,
				tmpRegion.getRegionWidth(), tmpRegion.getRegionHeight(),
				1, 1,
				entity.pan);
	}

/**
 * 	TileMap Rendering Section
 */
	// Drawing bounds (screen lower left corner to upper right corner in camera pos. in pixels)
	private Vector2 lowerBounds;
	private Vector2 upperBounds;

	// Current tile's final screen coordinates in pixels
	private Vector2 tileScreenPos;
	private int mapTileSize;

	public float clamp(float val, float a, float b) {
		return Math.max(a, Math.min(val, b));
	}

	private byte[][] mapContent;
	private float[][][] mapPositions;

	// Line-Segment intersection
//	Vector2 p = new Vector2();
//	float dymcy;
//	float dxmcx;
//	float aymcy;
//	float axmcx;
//	float bxmax;
//	float bymay;
//	float r;
//	public Vector2 lineSegmentIntersection(Vector2 a, Vector2 b, Vector2 c, Vector2 d) {
//
//		dxmcx = d.x - c.x;
//		dymcy = d.y - c.y;
//		aymcy = a.y - c.y;
//		axmcx = a.x - c.x;
//		bxmax = b.x - a.x;
//		bymay = b.y - a.y;
//		r = 	(aymcy * dxmcx - axmcx * dymcy) /
//				(bxmax * dymcy - bymay * dxmcx);
////		s = 	(aymcy * bxmax - axmcx * bymay) /
////				(bxmax * dymcy - bymay * dxmcx);
//
//		if (r < 0) {
//			p.set(a);
//		}
//		else if (r < 1) {
//			p.x = a.x + r * bxmax;
//			p.y = a.y + r * bymay;
//		}
//		else {
//			p.set(b);
//		}
//		return p;
//	}


	//TODO implement good 2D lighting and re-factor code and clean up the mess...
	// https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
	// http://www.redblobgames.com/articles/visibility/
	// http://ncase.me/sight-and-light/
	// https://github.com/Silverwolf90/2d-visibility/blob/master/src/segmentInFrontOf.js
	// https://legends2k.github.io/2d-fov/design.html#basic-algorithm
	public void draw(TileMap map) {

		mapTileSize = map.getTileSize();
		mapContent = map.getContent();
		mapPositions = map.getPositions();

		drawOffset.set(cameraOffset);
		drawOffset.sub(screenCenterOffset);

		lowerBounds.set( drawOffset.x / mapTileSize, drawOffset.y / mapTileSize );
		upperBounds.set( (drawOffset.x + screenSize.x) / mapTileSize + 1, (drawOffset.y + screenSize.y) / mapTileSize + 1);

		lowerBounds.x = clamp(lowerBounds.x, 0, mapContent.length - 1);
		lowerBounds.y = clamp(lowerBounds.y, 0, mapContent[(int)lowerBounds.x].length - 1);

		upperBounds.x = clamp(upperBounds.x, 0, mapContent.length - 1);
		upperBounds.y = clamp(upperBounds.y, 0, mapContent[(int)upperBounds.x].length - 1);

//		int centerSquareX = (int)(((upperBounds.x - lowerBounds.x) / 2) + lowerBounds.x);
//		int centerSquareY = (int)(((upperBounds.y - lowerBounds.y) / 2) + lowerBounds.y);

		// Go from lower bounds in screen coordinates to upper bounds drawing each available
		// tile on the screen.
		for (int i = (int)lowerBounds.x; i < upperBounds.x; i++) {
			for (int j = (int)lowerBounds.y; j < upperBounds.y; j++) {
				tileScreenPos.set( mapPositions[i][j][0] - drawOffset.x, mapPositions[i][j][1] - drawOffset.y);

				draw(map.getTileTexture( 0, mapContent[i][j]),
						tileScreenPos.x,
						tileScreenPos.y);
			}
		}
	}

}
