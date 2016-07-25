package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Hero extends Sprite {
	
	Animation heroDefault;
	TextureAtlas atlas;
	private float stateTimer = 0;
	
	float xPos;
	
	Rectangle rectangle;
	
	public enum State {FLYINGLEFT, FLYINGRIGHT, IDLE};
	State currentState;
	
	boolean lookingRight;
	
	float rotation;
	
	Animation explosionAnimation;
	TextureAtlas atlas2;
	public boolean isHit;
	
	private float explosionTimer = 0;
	
	public boolean gameOver = false;
	
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
		frames.clear();
		currentState = State.IDLE;
		
		xPos = (int) (MyGdxGame.V_WIDTH / 2 - this.getWidth() / 2);
		
		rectangle = new Rectangle(0, 40, this.getWidth() - 30, this.getHeight() - 80);
		
		
		atlas2 = new TextureAtlas(Gdx.files.internal("explosion/bossExplosion.pack"));
		
		frames.add(atlas2.findRegion("explosion01"));
		frames.add(atlas2.findRegion("explosion02"));
		frames.add(atlas2.findRegion("explosion03"));
		frames.add(atlas2.findRegion("explosion04"));
		frames.add(atlas2.findRegion("explosion05"));
		
		explosionAnimation = new Animation(0.15f, frames);
	}
	
	public void update(float delta){
		setRegion(heroDefault.getKeyFrame(stateTimer, true));
		setPosition(xPos, 10);
		
		//Hitbox an aktuelle Position anpassen
		rectangle.setPosition(xPos + 15, 85);
		
		stateTimer += delta;
		
		super.setRotation(6f * rotation);
		
		if(currentState == State.FLYINGLEFT){
			lookingRight = false;
		}
		
		else if(currentState == State.FLYINGRIGHT){
			lookingRight = true;
		}
		
		else if(currentState == State.IDLE){
			setRotation(0f);
		}
		
		if (lookingRight && !isFlipX()) {
			flip(true, false);
		}
		
		else if (!lookingRight && isFlipX()) {
			flip(true, false);
		}
		
		if(isHit){
			setRegion(explosionAnimation.getKeyFrame(explosionTimer));
			explosionTimer += delta;
			
			if(explosionAnimation.isAnimationFinished(explosionTimer)){
				gameOver = true;
			}
		}
		
	}

	public void setHit(boolean b){
		isHit = b;
	}
	
	public float getXpos() {
		return xPos;
	}

	public void setXpos(float f) {
		xPos = f;
		
		if(xPos < 0)
			xPos = 0;
		
		if(xPos > 490 - this.getWidth())
			xPos = (int) (490 - this.getWidth());
	}
	
	public void setState(State state){
		currentState = state;
	}
	
	public State getState(){
		return currentState;
	}
	
	public void setRotation(float rotation){
		this.rotation = rotation;
	}
}
