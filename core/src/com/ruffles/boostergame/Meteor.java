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
	
	public Meteor(int xPos, int yPos, int color, int speed){
		
		//Initialisieren mit Hero Sprite, weil das default Meteor-Sprite den falschen Bit-Channel hat
		super(new Texture(Gdx.files.internal("hero1.png")));
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		
		setBounds(xPos, yPos, (float) (64f * 1), (float) (64f * 1));
		
		atlas = new TextureAtlas(Gdx.files.internal("meteoriten.pack"));
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		
		/*
		 * color-chart:
		 * 0 := grau
		 * 1 := rot
		 * 2 := braun
		 */
		switch (color) {
		case 0:
			frames.add(atlas.findRegion("a10000"));
			frames.add(atlas.findRegion("a10001"));
			frames.add(atlas.findRegion("a10002"));
			frames.add(atlas.findRegion("a10003"));
			frames.add(atlas.findRegion("a10004"));
			frames.add(atlas.findRegion("a10005"));
			frames.add(atlas.findRegion("a10006"));
			frames.add(atlas.findRegion("a10007"));
			frames.add(atlas.findRegion("a10008"));
			frames.add(atlas.findRegion("a10009"));
			frames.add(atlas.findRegion("a10010"));
			frames.add(atlas.findRegion("a10011"));
			frames.add(atlas.findRegion("a10012"));
			frames.add(atlas.findRegion("a10013"));
			frames.add(atlas.findRegion("a10014"));
			frames.add(atlas.findRegion("a10015"));
			break;
			
		case 1:
			frames.add(atlas.findRegion("a30000"));
			frames.add(atlas.findRegion("a30001"));
			frames.add(atlas.findRegion("a30002"));
			frames.add(atlas.findRegion("a30003"));
			frames.add(atlas.findRegion("a30004"));
			frames.add(atlas.findRegion("a30005"));
			frames.add(atlas.findRegion("a30006"));
			frames.add(atlas.findRegion("a30007"));
			frames.add(atlas.findRegion("a30008"));
			frames.add(atlas.findRegion("a30009"));
			frames.add(atlas.findRegion("a30010"));
			frames.add(atlas.findRegion("a30011"));
			frames.add(atlas.findRegion("a30012"));
			frames.add(atlas.findRegion("a30013"));
			frames.add(atlas.findRegion("a30014"));
			frames.add(atlas.findRegion("a30015"));
			break;
			
		case 2:
			frames.add(atlas.findRegion("a40000"));
			frames.add(atlas.findRegion("a40001"));
			frames.add(atlas.findRegion("a40002"));
			frames.add(atlas.findRegion("a40003"));
			frames.add(atlas.findRegion("a40004"));
			frames.add(atlas.findRegion("a40005"));
			frames.add(atlas.findRegion("a40006"));
			frames.add(atlas.findRegion("a40007"));
			frames.add(atlas.findRegion("a40008"));
			frames.add(atlas.findRegion("a40009"));
			frames.add(atlas.findRegion("a40010"));
			frames.add(atlas.findRegion("a40011"));
			frames.add(atlas.findRegion("a40012"));
			frames.add(atlas.findRegion("a40013"));
			frames.add(atlas.findRegion("a40014"));
			frames.add(atlas.findRegion("a40015"));
			break;
			
		default:
			break;
		}
		
		/*
		 * Drehrichtung zufaellig
		 */
		rand = new Random();
		if(rand.nextBoolean()){
			flip = true;
		}
		
		meteorDefault = new Animation(0.2f, frames);
		
		rectangle = new Rectangle(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void update(float delta){
		setRegion(meteorDefault.getKeyFrame(stateTimer, true));
		setPosition(xPos, yPos);
		
		if(flip && !isFlipX()){
			this.flip(true, false);
		}
		
		//Hitbox an aktuelle Position anpassen
		rectangle.setPosition(xPos, yPos);
		
		stateTimer += delta;
		
		yPos -= speed;
		
		if(yPos < -64){
			setToDestroy = true;
		}
	}
}
