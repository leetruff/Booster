package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BigAssets {

	public static Texture spaceBackground;
	
	public static void load(){
		spaceBackground = new Texture(Gdx.files.internal("spacebackground.png"));
	}
}
