package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;

public class GUI implements Disposable {

	private static GUI gui;			// Singleton reference to GUI object
	private AssetManager assets;
	private TextButton.TextButtonStyle textButtonStyle;
	private BitmapFont titleFont;
	private Slider.SliderStyle sliderStyle;
	private Label.LabelStyle labelStyle;
	private Label.LabelStyle titleLabelStyle;
	private BitmapFont globalFont;
	private TextureAtlas skinsAtlas;
	private Skin skins; //** images are used as skins of the button **//

	private GUI() {
		assets = ZShooter.assets;
		skins = new Skin();
		skinsAtlas = assets.get("gdx-skins-master/Styles + Skins + Fonts/skin/star-soldier-ui.atlas", TextureAtlas.class);
		skins.addRegions(skinsAtlas); //** skins for on and off **//
		globalFont = assets.get("gdx-skins-master/Styles + Skins + Fonts/skin/font-export.fnt", BitmapFont.class);


		textButtonStyle = new TextButton.TextButtonStyle(); //** Button properties **//
		textButtonStyle.up = skins.getDrawable("button");
		textButtonStyle.down = skins.getDrawable("button-selected");

		textButtonStyle.font = globalFont;
		textButtonStyle.font.getData().setScale(4);

		titleFont = assets.get("gdx-skins-master/Styles + Skins + Fonts/skin/font-title-export.fnt",BitmapFont.class);
		titleFont.getData().setScale(1.5f);

		sliderStyle = new Slider.SliderStyle();
		sliderStyle.knob = skins.getDrawable("slider-knob");
		sliderStyle.background = skins.getDrawable("slider-back");
		sliderStyle.knobBefore = skins.getDrawable("slider");

		labelStyle = new Label.LabelStyle(globalFont,globalFont.getColor());
		titleLabelStyle = new Label.LabelStyle(titleFont,titleFont.getColor());
	}

	@Override
	public void dispose() {
		skins.getAtlas().dispose();
		skins.dispose();

		// Singleton destroy
		gui = null;
	}

	public static GUI getGUI() {
		if (gui == null) {
			gui = new GUI();
		}
		return gui;
	}

	public BitmapFont getTitleFont() {
		return titleFont;
	}

	public Slider.SliderStyle getSliderStyle() {
		return sliderStyle;
	}

	public TextButton.TextButtonStyle getTextButtonStyle() {
		return textButtonStyle;
	}

	public Label.LabelStyle getLabelStyle() {
		return labelStyle;
	}

	public Label.LabelStyle getTitleLabelStyle() {
		return titleLabelStyle;
	}

	public BitmapFont getGlobalFont() {
		return globalFont;
	}
}
