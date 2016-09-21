package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by NoLight on 18.9.2016 г..
 */
public class Database {
    public Preferences prefs;
    private static Database ourInstance;

    public float cordinateX;
    public float cordinateY;

    private Database(){
        prefs = Gdx.app.getPreferences("Cordinates");
        cordinateX = 1f;
        cordinateY = 1f;

    }

    public static Database getOurInstance() {
        if(ourInstance == null){
            ourInstance = new Database();
        }
        return ourInstance;
    }

    public void save(){
        prefs.putFloat("X",cordinateX);
        prefs.putFloat("Y",cordinateY);
        prefs.flush();
    }
    public void load(){
        cordinateX = prefs.getFloat("X");
        cordinateY = prefs.getFloat("Y");
    }

    public void setCordinateX(float cordinateX) {
        this.cordinateX = cordinateX;
    }

    public void setCordinateY(float cordinateY) {
        this.cordinateY = cordinateY;
    }

    public float getCordinateX() {
        return cordinateX;
    }

    public float getCordinateY() {
        return cordinateY;
    }
}
