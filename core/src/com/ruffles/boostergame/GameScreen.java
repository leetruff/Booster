package com.ruffles.boostergame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.ruffles.boostergame.Hero.State;

public class GameScreen implements Screen {
	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	Hero hero;
	
	Texture background;
	int backgroundYpos = 0;
	
	ArrayList<Meteor> meteoritenList;
	
	Random rand;
	
	Animation meteorGrau;
	Animation meteorRot;
	Animation meteorBraun;
	
	TextureAtlas atlas;
	private boolean accleAvailable;
	FreeTypeFontGenerator generator;
	private BitmapFont scoreFont;

	int score = 0;

	public GameScreen(MyGdxGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		batch = new SpriteBatch();
		
		hero = new Hero();
		
		background = new Texture(Gdx.files.internal("spacebackground.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		meteoritenList = new ArrayList<Meteor>();
		
		//meteoritenList.add(new Meteor(100, 800, 2, 3));
		
		rand = new Random();
		
		
		atlas = new TextureAtlas(Gdx.files.internal("meteoriten.pack"));
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
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
			
			meteorGrau = new Animation(0.2f, frames);
			frames.clear();
			
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
			
			meteorRot = new Animation(0.2f, frames);
			frames.clear();

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
			
			meteorBraun = new Animation(0.2f, frames);
			frames.clear();
			
			accleAvailable = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
			
			
			generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ocraextended.ttf"));
			FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
			parameter2.size = 20;
			scoreFont = generator.generateFont(parameter2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		handleInput(delta);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 490, 2048, 0, backgroundYpos, 490, 2048, false, false);
		
		for(int i = 0; i < meteoritenList.size(); i++){
			meteoritenList.get(i).draw(batch);
		}
		
		hero.draw(batch);
		
		scoreFont.draw(batch, "Score: " + score, 320, 880);
		
		batch.end();
		
		
		Gdx.graphics.setTitle("Booster | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}

	private void update(float delta) {
		cam.update();
		hero.update(delta);
		
		/*
		 * Neue Meteoriten spawnen
		 */
		if(meteoritenList.size() < 8){
			
			switch (rand.nextInt(3)) {
			case 0:
				meteoritenList.add(new Meteor(rand.nextInt(435), 950, rand.nextInt(3), rand.nextInt(4) + 2, meteorGrau));
				break;
			case 1:
				meteoritenList.add(new Meteor(rand.nextInt(435), 950, rand.nextInt(3), rand.nextInt(4) + 2, meteorRot));
				break;
			case 2:
				meteoritenList.add(new Meteor(rand.nextInt(435), 950, rand.nextInt(3), rand.nextInt(4) + 2, meteorBraun));
				break;

			default:
				break;
			}
			
		}
		
		for(int i = 0; i < meteoritenList.size(); i++){
			meteoritenList.get(i).update(delta);
		}
		
		/*
		 * Meteoriten welche nicht mehr auf dem Bildschirm sind entfernen, damit
		 * neue gespawned werden koennen
		 */
		for(int i = 0; i < meteoritenList.size(); i++){
			if(meteoritenList.get(i).setToDestroy){
				meteoritenList.remove(i);
			}
		}
		
		/*
		 * Kollision von Meteoriten und Hero pruefen
		 */
		for(int i = 0; i < meteoritenList.size(); i++){
			if(Intersector.overlaps(meteoritenList.get(i).rectangle, hero.rectangle)){
				//TODO
			}
		}
		
		/*
		 * Background loop
		 */
		if(backgroundYpos > Integer.MIN_VALUE){
			backgroundYpos -= 1;
		}
		else{
			backgroundYpos = 0;
		}
		
		score += 1;

	}
	
	private void handleInput(float delta){
		
		if(Gdx.app.getType() == ApplicationType.Desktop){
			if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)){
				hero.setXpos(hero.getXpos() - 8);
				hero.setRotation(2.5f);
				hero.setState(State.FLYINGLEFT);
			}
			
			if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)){
				hero.setXpos(hero.getXpos() + 8);
				hero.setRotation(-2.5f);
				hero.setState(State.FLYINGRIGHT);
			}
			
			if(!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.RIGHT)){
				hero.setState(State.IDLE);
			}
		}
		
		else if(Gdx.app.getType() == ApplicationType.Android && accleAvailable){
		    
		    float accelX = Gdx.input.getAccelerometerX();
		    
			if(accelX > 0.1f){
				hero.setXpos((hero.getXpos() - 7.5f * accelX));
				hero.setRotation(accelX);
				hero.setState(State.FLYINGLEFT);
			}
			
			else if(accelX < -0.1f){
				hero.setXpos((hero.getXpos() + 7.5f * -accelX));
				hero.setRotation(accelX);
				hero.setState(State.FLYINGRIGHT);
			}
			
			else{
				hero.setState(State.IDLE);
				hero.setRotation(0);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		port.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
