package com.mesetts.zshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by EpsiloN on 8/23/2016.
 */
public class GUI implements Disposable {

	private static GUI gui;			// Singleton reference to GUI object

	private TextButton.TextButtonStyle textButtonStyle;
	private BitmapFont titleFont;
	private Slider.SliderStyle sliderStyle;
	private Label.LabelStyle labelStyle;
	private Label.LabelStyle titleLabelStyle;
	private BitmapFont globalFont;
	private Skin skins; //** images are used as skins of the button **//

	private GUI() {
		skins = new Skin();
		skins.addRegions(new TextureAtlas("gdx-skins-master/Styles + Skins + Fonts/skin/star-soldier-ui.atlas")); //** skins for on and off **//
		globalFont = new BitmapFont(Gdx.files.internal("gdx-skins-master/Styles + Skins + Fonts/skin/font-export.fnt"), false);

		textButtonStyle = new TextButton.TextButtonStyle(); //** Button properties **//
		textButtonStyle.up = skins.getDrawable("button");
		textButtonStyle.down = skins.getDrawable("button-selected");

		textButtonStyle.font = globalFont;
		textButtonStyle.font.getData().setScale(4);

		titleFont = new BitmapFont(Gdx.files.internal("gdx-skins-master/Styles + Skins + Fonts/skin/font-title-export.fnt"));
		titleFont.getData().setScale(3f);

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
}
