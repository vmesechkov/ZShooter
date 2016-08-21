package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class TileMap {

	TextureRegion[][] textures;

	int[][] tilemap_content;
	int[][][] tilemap_tile_positions;
    int[][] tilemap_collidableTiles;

    int tileSize = 128;
    int tileHalfSize = tileSize / 2;

	Texture currentTile;

	TileMap(final int width, final int height) {

		Texture textureSheet = new Texture(Gdx.files.internal("data/Textures/textureSheet2_128.png"));
		textures = TextureRegion.split(textureSheet, tileSize, tileSize);

		tilemap_content = new int[width][height];
		tilemap_tile_positions = new int[width][height][2];
        tilemap_collidableTiles = new int[width][height];
		Random r = new Random();
		for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tilemap_content[i][j] = r.nextInt(6);
                tilemap_tile_positions[i][j][0] = i * tileSize;
                tilemap_tile_positions[i][j][1] = j * tileSize;
            }
        }

		tilemap_content[0][0] = 0;
		tilemap_content[1][0] = 1;
		tilemap_content[2][0] = 2;
		tilemap_content[3][0] = 3;
		tilemap_content[4][0] = 2;
		tilemap_content[5][0] = 4;
		tilemap_content[6][0] = 0;

		tilemap_content[0][1] = 0;
		tilemap_content[1][1] = 1;
		tilemap_content[2][1] = 2;
		tilemap_content[3][1] = 3;
		tilemap_content[4][1] = 2;
		tilemap_content[5][1] = 4;
		tilemap_content[6][1] = 0;

		tilemap_content[0][2] = 0;
		tilemap_content[1][2] = 1;
		tilemap_content[2][2] = 2;
		tilemap_content[3][2] = 3;
		tilemap_content[4][2] = 2;
		tilemap_content[5][2] = 4;
		tilemap_content[6][2] = 0;

		tilemap_content[0][3] = 0;
		tilemap_content[1][3] = 1;
		tilemap_content[2][3] = 2;
		tilemap_content[3][3] = 3;
		tilemap_content[4][3] = 2;
		tilemap_content[5][3] = 4;
		tilemap_content[6][3] = 0;

		tilemap_content[0][4] = 0;
		tilemap_content[1][4] = 1;
		tilemap_content[2][4] = 2;
		tilemap_content[3][4] = 3;
		tilemap_content[4][4] = 2;
		tilemap_content[5][4] = 4;
		tilemap_content[6][4] = 0;

		tilemap_content[0][5] = 0;
		tilemap_content[1][5] = 1;
		tilemap_content[2][5] = 2;
		tilemap_content[3][5] = 3;
		tilemap_content[4][5] = 2;
		tilemap_content[5][5] = 4;
		tilemap_content[6][5] = 0;

		tilemap_content[0][6] = 0;
		tilemap_content[1][6] = 1;
		tilemap_content[2][6] = 2;
		tilemap_content[3][6] = 3;
		tilemap_content[4][6] = 2;
		tilemap_content[5][6] = 4;
		tilemap_content[6][6] = 0;

		tilemap_content[0][7] = 6;
		tilemap_content[1][7] = 2;
		tilemap_content[2][7] = 2;
		tilemap_content[3][7] = 2;
		tilemap_content[4][7] = 2;
		tilemap_content[5][7] = 2;
		tilemap_content[6][7] = 6;

		tilemap_content[0][8] = 2;
		tilemap_content[1][8] = 2;
		tilemap_content[2][8] = 2;
		tilemap_content[3][8] = 2;
		tilemap_content[4][8] = 2;
		tilemap_content[5][8] = 2;
		tilemap_content[6][8] = 2;

		for (int i = 0; i < tilemap_content.length; i++) {
			for (int j = 0; j < tilemap_content[i].length; j++) {
				if (tilemap_content[i][j] == 0) {
					tilemap_collidableTiles[i][j] = 1;
				}
				else {
					tilemap_collidableTiles[i][j] = 0;
				}
			}
		}
	}

	int tileScreenPosX;
	int tileScreenPosY;
	int lowerBoundX;
	int lowerBoundY;
	int upperBoundX;
	int upperBoundY;
	int drawOffsetX;
	int drawOffsetY;

	public void draw(final SpriteBatch batch, final int cameraX, final int cameraY) {
		int lowerBoundX = (cameraX - (int)InGameScreen.screenDrawOffset.x) / tileSize;
		int lowerBoundY = (cameraY - (int)InGameScreen.screenDrawOffset.y) / tileSize;
		int upperBoundX = (cameraX + (int)InGameScreen.screenDrawOffset.x) / tileSize + 1;
		int upperBoundY = (cameraY + (int)InGameScreen.screenDrawOffset.y) / tileSize + 1;
		// Restrict lower bound to > than 0
		lowerBoundX = (lowerBoundX < 0) ? 0 : lowerBoundX;
		lowerBoundY = (lowerBoundY < 0) ? 0 : lowerBoundY;
		// Restrict lower bound to < than tilemap size
		lowerBoundX = (lowerBoundX < tilemap_content.length - 1) ? lowerBoundX : tilemap_content.length - 1;
		lowerBoundY = (lowerBoundY < tilemap_content[lowerBoundX].length - 1) ? lowerBoundY : tilemap_content[lowerBoundX].length - 1;

		// Restrict upper bound to > than 0
		upperBoundX = (upperBoundX < 0) ? 0 : upperBoundX;
		upperBoundY = (upperBoundY < 0) ? 0 : upperBoundY;
		// Restrict upper bound to < than tilemap size
		upperBoundX = (upperBoundX < tilemap_content.length - 1) ? upperBoundX : tilemap_content.length - 1;
		upperBoundY = (upperBoundY < tilemap_content[upperBoundX].length - 1) ? upperBoundY : tilemap_content[upperBoundX].length - 1;

		drawOffsetX = cameraX - (int)InGameScreen.screenDrawOffset.x;
		drawOffsetY = cameraY - (int)InGameScreen.screenDrawOffset.y;

		for (int i = lowerBoundX; i < upperBoundX; i++) {
			for (int j = lowerBoundY; j < upperBoundY; j++) {
				tileScreenPosX = tilemap_tile_positions[i][j][0] - drawOffsetX;
				tileScreenPosY = tilemap_tile_positions[i][j][1] - drawOffsetY;

//                switch (tilemap_content[i][j]) {
//                    case 1:
//                        currentTile = concreteTex;
//                        break;
//                    case 0:
//                    default:
//                        currentTile = asphaltTex;
//                        break;
//                }
				batch.draw(textures[0][tilemap_content[i][j]],
						tileScreenPosX,
						tileScreenPosY);
			}
		}
	}

}
