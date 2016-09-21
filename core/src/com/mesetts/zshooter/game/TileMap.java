package com.mesetts.zshooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class TileMap {

	private TextureRegion[][] textures;
	private Texture textureSheet;

	private World world;
	private Body tileMapBody;

	private byte[][] content;
	private float[][][] positions;
    private byte[][] collidable;
	public byte[][] lightmap;
	public float[] floatFromByte;

	public byte[][] getContent() {
		return content;
	}

	public float[][][] getPositions() {
		return positions;
	}

	public byte[][] getCollidables() {
		return collidable;
	}

	public byte[][] getLightmap() { return lightmap; }

	private int tileSize;

	public int getTileSize() {
		return this.tileSize;
	}

	public TextureRegion getTileTexture(int x, int y) {
		return textures[x][y];
	}

	public TileMap(Texture textureSheet, final int width, final int height, final int tileSize, World world) {
		this.world = world;

		this.tileSize = tileSize;

		this.textureSheet = textureSheet;
		textures = TextureRegion.split(textureSheet, tileSize, tileSize);

		// Initialize our content, collidable tiles and positions
		content = new byte[width][height];
		positions = new float[width][height][2];
        collidable = new byte[width][height];
		lightmap = new byte[width][height];
		floatFromByte = new float[256];
	}

	public void generateBody() {
		// If a body exists, destroy it.
		if (tileMapBody != null) {
			world.destroyBody(tileMapBody);
		}

		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
// Set its world position
		groundBodyDef.position.set(new Vector2());

// Create a body from the defintion and add it to the world
		tileMapBody = world.createBody(groundBodyDef);
		tileMapBody.setUserData(this);

		Vector2 tilePosition = new Vector2();
		PolygonShape groundBox = new PolygonShape();

		Fixture fixture;

		Filter filter = new Filter();
		filter.categoryBits = 0x0001;
		filter.maskBits = 0x0001 | 0x0002;

// Update the street's collidable tiles
		for (int i = 0; i < content.length; i++) {
			for (int j = 0; j < content[i].length; j++) {
				if (content[i][j] == 0) {
					collidable[i][j] = 1;
					tilePosition.set(i + 0.5f,j + 0.5f);

					groundBox.setAsBox(0.5f, 0.5f, tilePosition, 0 );

					fixture = tileMapBody.createFixture(groundBox, 0.0f);
					fixture.setFilterData(filter);
					fixture.setUserData(this);
				}
				else {
					collidable[i][j] = 0;
				}
			}
		}
		groundBox.dispose();
	}

	public void generateRandomMap() {
		// Build a random map, setting each tile's content to a random texture from our atlas
		// and pre-calculating our tile positions to save the multiplication when rendering
		Random r = new Random();
		for(int i = 0; i < content.length; i++) {
			for (int j = 0; j < content[0].length; j++) {
				content[i][j] = (byte)r.nextInt(textures.length * textures[0].length);
				positions[i][j][0] = i * tileSize;
				positions[i][j][1] = j * tileSize;
			}
		}

		for (int i = floatFromByte.length - 1, j = 1; i > 0; i--, j++) {
			floatFromByte[j] = 1 / i;
		}
		floatFromByte[0] = 0;

// ------------------------------
// Manually create a small street
// ------------------------------
		content[0][0] = 0;
		content[1][0] = 1;
		content[2][0] = 2;
		content[3][0] = 3;
		content[4][0] = 2;
		content[5][0] = 4;
		content[6][0] = 0;

		content[0][1] = 0;
		content[1][1] = 1;
		content[2][1] = 2;
		content[3][1] = 3;
		content[4][1] = 2;
		content[5][1] = 4;
		content[6][1] = 0;

		content[0][2] = 0;
		content[1][2] = 1;
		content[2][2] = 2;
		content[3][2] = 3;
		content[4][2] = 2;
		content[5][2] = 4;
		content[6][2] = 0;

		content[0][3] = 0;
		content[1][3] = 1;
		content[2][3] = 2;
		content[3][3] = 3;
		content[4][3] = 2;
		content[5][3] = 4;
		content[6][3] = 0;

		content[0][4] = 0;
		content[1][4] = 1;
		content[2][4] = 2;
		content[3][4] = 3;
		content[4][4] = 2;
		content[5][4] = 4;
		content[6][4] = 0;

		content[0][5] = 0;
		content[1][5] = 1;
		content[2][5] = 2;
		content[3][5] = 3;
		content[4][5] = 2;
		content[5][5] = 4;
		content[6][5] = 0;

		content[0][6] = 0;
		content[1][6] = 1;
		content[2][6] = 2;
		content[3][6] = 3;
		content[4][6] = 2;
		content[5][6] = 4;
		content[6][6] = 0;

		content[0][7] = 6;
		content[1][7] = 2;
		content[2][7] = 2;
		content[3][7] = 2;
		content[4][7] = 2;
		content[5][7] = 2;
		content[6][7] = 6;

		content[0][8] = 2;
		content[1][8] = 2;
		content[2][8] = 2;
		content[3][8] = 2;
		content[4][8] = 2;
		content[5][8] = 2;
		content[6][8] = 2;
	}

	public void setTile(int tileX, int tileY, byte tileType) {
		content[tileX][tileY] = tileType;
	}

	public boolean nodeIsWalkable(int nodeIndex) {
		int nodeX = nodeIndex % content.length;
		int nodeY = nodeIndex / content.length;
		return collidable[nodeX][nodeY] == 0;
	}

	public void dispose() {
		textureSheet.dispose();
	}


}
