package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by EpsiloN on 8/18/2016.
 */
public class Tile {
    int x;
    int y;

    Texture tileTex;

    Tile(String tex) {
        tileTex = new Texture(Gdx.files.internal(tex));
    }

}
