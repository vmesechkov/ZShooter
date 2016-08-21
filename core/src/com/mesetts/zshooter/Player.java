package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private static final int        FRAME_COLS = 16;
	private static final int        FRAME_ROWS = 1;

	private int x = 0;
	private int y = 0;
	private float pan;
	private float legsPan;

	Animation legsWalkAnimation;
	Animation torsoWalkAnimation;
	Texture legsWalkSheet;
	Texture torsoWalkSheet;
	TextureRegion[] legsWalkFrames;
	TextureRegion[] torsoWalkFrames;
	// Will be used to center the player at x,y, instead of his bottom-left corner.
	int playerImageSizeX;
	int playerImageSizeY;
	int playerImageHalfSizeX;
	int playerImageHalfSizeY;
	int screenSizeX = Gdx.graphics.getWidth();
	int screenSizeY = Gdx.graphics.getHeight();
	int screenCenterX = screenSizeX / 2;
	int screenCenterY = screenSizeY / 2;

	private int movementSpeed;

	float stateTime;
	private TextureRegion currentLegsFrame;
	private TextureRegion currentTorsoFrame;

	int collisionType = 1;  // 0 - circle
	int collisionRadius = 32;   // 32 pixels radius


	// Splits a sprite sheet into texture regions and returns a single array of texture regions
	private TextureRegion[] arrangeFrames(final Texture sheet, final int cols, final int rows) {
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
		TextureRegion[] finalSheet = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				finalSheet[index++] = tmp[i][j];
			}
		}
		return finalSheet;
	}

	public Player(final String legsSheetFilename, final String torsoSheetFilename) {

		// Initialize the legs sprites and animation
		legsWalkSheet = new Texture(Gdx.files.internal(legsSheetFilename));

		// Set player dimentions
		playerImageSizeX = legsWalkSheet.getHeight();
		playerImageSizeY = legsWalkSheet.getHeight();
		playerImageHalfSizeX = playerImageSizeX / 2;
		playerImageHalfSizeY = playerImageSizeY / 2;

		// Set collision radius of circle
		collisionRadius = playerImageHalfSizeX;

		legsWalkFrames = arrangeFrames(legsWalkSheet, FRAME_COLS, FRAME_ROWS);
		legsWalkAnimation = new Animation(0.03125f, legsWalkFrames);
		currentLegsFrame = legsWalkFrames[0];

		// Initialize the torso sprites and animation
		torsoWalkSheet = new Texture(Gdx.files.internal(torsoSheetFilename));
		torsoWalkFrames = arrangeFrames(torsoWalkSheet, FRAME_COLS, FRAME_ROWS);
		torsoWalkAnimation = new Animation(0.03125f, torsoWalkFrames);
		currentTorsoFrame = torsoWalkFrames[0];

		stateTime = 0f;

		movementSpeed = 10;
	}

	public void draw(SpriteBatch batch, int cameraOffsetX, int cameraOffsetY) {

		batch.draw(currentLegsFrame,
				x - playerImageHalfSizeX - cameraOffsetX + InGameScreen.screenDrawOffset.x,   // Player image coordinates
				y - playerImageHalfSizeX - cameraOffsetY + InGameScreen.screenDrawOffset.y,   // (Bottom left corner...)
				playerImageHalfSizeX, playerImageHalfSizeY, // Rotation origin within the player image
				playerImageSizeX, playerImageSizeY,     // Player image dimentions
				1, 1,   // Scale X/Y = 1
				legsPan);

		batch.draw(currentTorsoFrame,
				x - playerImageHalfSizeX - cameraOffsetX + InGameScreen.screenDrawOffset.x,   // Player image coordinates
				y - playerImageHalfSizeX - cameraOffsetY + InGameScreen.screenDrawOffset.y,   // (Bottom left corner...)
				playerImageHalfSizeX, playerImageHalfSizeY, // Rotation origin within the player image
				playerImageSizeX, playerImageSizeY,     // Player image dimentions
				1, 1,   // Scale X/Y = 1
				pan);
	}

	enum PlayerAnimation {
		IDLE,
		RUN
	}

	public void animate(PlayerAnimation anim) {
		switch (anim) {
			case RUN:
				stateTime += Gdx.graphics.getDeltaTime();
				currentLegsFrame = legsWalkAnimation.getKeyFrame(stateTime, true);
				currentTorsoFrame = torsoWalkAnimation.getKeyFrame(stateTime, true);
				break;
			case IDLE:
			default:
				stateTime += Gdx.graphics.getDeltaTime() * 0.2;
				currentLegsFrame = legsWalkAnimation.getKeyFrames()[5];
				currentTorsoFrame = torsoWalkAnimation.getKeyFrame(stateTime, true);
				break;
		}
	}

	public void move(TileMap currentGround, float angle, float x, float y) {
		Vector2 moveVec = new Vector2(x, y);
		moveVec.nor();
		moveVec.scl(this.movementSpeed);

		// Perform collision detection here...
		//

		int pX, pY;

		if (moveVec.x > 0) {
			// Check point right of player
			pX = (this.x + this.collisionRadius) / 128;
			pY = this.y / 128;
			if (pX >= 0 && pX < currentGround.tilemap_collidableTiles.length && pY >= 0 && pY < currentGround.tilemap_collidableTiles[pX].length && currentGround.tilemap_collidableTiles[pX][pY] == 1 ) {
				moveVec.x = 0;
			}
		}
		else {
			// Check point left of player
			pX = (this.x - this.collisionRadius) / 128;
			pY = this.y / 128;
			if (pX >= 0 && pX < currentGround.tilemap_collidableTiles.length && pY >= 0 && pY < currentGround.tilemap_collidableTiles[pX].length && currentGround.tilemap_collidableTiles[pX][pY] == 1 ) {
				moveVec.x = 0;
			}
		}
		if (moveVec.y > 0) {
			// Check point up of player
			pX = this.x / 128;
			pY = (this.y + this.collisionRadius) / 128;
			if (pX >= 0 && pX < currentGround.tilemap_collidableTiles.length && pY >= 0 && pY < currentGround.tilemap_collidableTiles[pX].length && currentGround.tilemap_collidableTiles[pX][pY] == 1 ) {
				moveVec.y = 0;
			}
		}
		else {
			// Check point down of player
			pX = this.x / 128;
			pY = (this.y - this.collisionRadius) / 128;
			if (pX >= 0 && pX < currentGround.tilemap_collidableTiles.length && pY >= 0 && pY < currentGround.tilemap_collidableTiles[pX].length && currentGround.tilemap_collidableTiles[pX][pY] == 1 ) {
				moveVec.y = 0;
			}
		}

		this.x += moveVec.x;
		this.y += moveVec.y;

	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setPan(final float pan) {
		this.pan = pan;
		if (this.pan > 360) {
			this.pan -= 360;
		}
		if (this.pan < 0) {
			this.pan += 360;
		}
	}

	public void setLegsPan(final float legsPan) {
		this.legsPan = legsPan;
		if (this.legsPan > 360) {
			this.legsPan -= 360;
		}
		if (this.legsPan < 0) {
			this.legsPan += 360;
		}
	}

	public void dispose() {
		legsWalkSheet.dispose();
		torsoWalkSheet.dispose();
	}
}
