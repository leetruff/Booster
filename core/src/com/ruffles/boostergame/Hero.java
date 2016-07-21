package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Hero extends Sprite {
	
	Animation heroDefault;
	TextureAtlas atlas;
	private float stateTimer = 0;
	
	
	public Hero(){
		super(new Texture(Gdx.files.internal("hero1.png")));
		setBounds(0, 0, (float) (66f * 1), (float) (146f * 1));
		
		atlas = new TextureAtlas(Gdx.files.internal("heroFrames.pack"));
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		frames.add(atlas.findRegion("hero1"));
		frames.add(atlas.findRegion("hero2"));
		frames.add(atlas.findRegion("hero3"));
		frames.add(atlas.findRegion("hero4"));
		
		heroDefault = new Animation(0.15f, frames);
	}
	
	public void update(float delta){
		setRegion(heroDefault.getKeyFrame(stateTimer, true));
		setPosition(MyGdxGame.V_WIDTH / 2 - this.getWidth() / 2, 10);
		stateTimer += delta;
	}
}
