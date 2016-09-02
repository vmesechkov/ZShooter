package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by EpsiloN on 8/23/2016.
 */
public class GUI implements Disposable {

	private static GUI gui;			// Singleton reference to GUI object

	public TextButton.TextButtonStyle style;
	private Skin buttonSkin; //** images are used as skins of the button **//

	private GUI() {
		buttonSkin = new Skin();
		buttonSkin.addRegions(new TextureAtlas("all/button.pack")); //** skins for on and off **//

		style = new TextButton.TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable("buttonOff");
		style.down = buttonSkin.getDrawable("buttonOn");
		style.font = new BitmapFont(Gdx.files.internal("all/new.fnt"), false);
		style.font.getData().setScale(5);
	}

	@Override
	public void dispose() {
		buttonSkin.getAtlas().dispose();
		buttonSkin.dispose();

		// Singleton destroy
		gui = null;
	}

	public static GUI getGUI() {
		if (gui == null) {
			gui = new GUI();
		}
		return gui;
	}
}
