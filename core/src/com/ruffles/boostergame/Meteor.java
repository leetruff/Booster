package com.ruffles.boostergame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Meteor extends Sprite {

	Animation meteorDefault;
	TextureAtlas atlas;
	
	int xPos, yPos;
	
	Rectangle rectangle;
	private float stateTimer;
	
	int speed;
	
	Random rand;
	private boolean flip;
	boolean setToDestroy = false;
	
	public Meteor(int xPos, int yPos, int speed, Animation animation){
		
		//Initialisieren mit Hero Sprite, weil das default Meteor-Sprite den falschen Bit-Channel hat
		super(new Texture(Gdx.files.internal("hero1.png")));
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		
		setBounds(xPos, yPos, (float) (64f * 1), (float) (64f * 1));
		
		/*
		 * Drehrichtung zufaellig
		 */
		rand = new Random();
		if(rand.nextBoolean()){
			flip = true;
		}
		
		meteorDefault = animation;
		
		rectangle = new Rectangle(0, 0, 25, 25);
	}
	
	public void update(float delta){
		setRegion(meteorDefault.getKeyFrame(stateTimer, true));
		setPosition(xPos, yPos);
		
		if(flip && !isFlipX()){
			this.flip(true, false);
		}
		
		//Hitbox an aktuelle Position anpassen
		rectangle.setPosition(xPos + 20, yPos + 20);
		
		stateTimer += delta;
		
		yPos -= speed;
		
		if(yPos < -64){
			setToDestroy = true;
		}
	}
}
