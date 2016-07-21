package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hero extends Sprite {
	
	public Hero(){
		setRegion(new Texture(Gdx.files.internal("")));
	}
	
	public void update(float delta){
		//TODO
		setPosition(0, 0);
	}
}
