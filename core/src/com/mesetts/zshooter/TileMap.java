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

    Texture currentTile;

    TileMap(final int width, final int height) {

        Texture textureSheet = new Texture(Gdx.files.internal("data/Textures/textureSheet1_64.png"));
        textures = TextureRegion.split(textureSheet, 64, 64);

        tilemap_content = new int[width][height];
        tilemap_tile_positions = new int[width][height][2];
        Random r = new Random();
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tilemap_content[i][j] = r.nextInt(2);
                tilemap_tile_positions[i][j][0] = i * 64;
                tilemap_tile_positions[i][j][1] = j * 64;
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
        int lowerBoundX = (cameraX - (int)InGameScreen.screenDrawOffset.x) / 64;
        int lowerBoundY = (cameraY - (int)InGameScreen.screenDrawOffset.y) / 64;
        int upperBoundX = (cameraX + (int)InGameScreen.screenDrawOffset.x) / 64 + 1;
        int upperBoundY = (cameraY + (int)InGameScreen.screenDrawOffset.y) / 64 + 1;
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
