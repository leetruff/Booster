package com.ruffles.boostergame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	
	static int V_WIDTH = 740;
	static int V_HEIGHT = 360;
	
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
