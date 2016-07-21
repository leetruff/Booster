package com.ruffles.boostergame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
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
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		handleInput(delta);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 490, 1980, 0, backgroundYpos, 490, 1980, false, false);
		hero.draw(batch);
		
		for(int i = 0; i < meteoritenList.size(); i++){
			meteoritenList.get(i).draw(batch);
		}
		
		batch.end();
		
		
		Gdx.graphics.setTitle("Booster | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}

	private void update(float delta) {
		cam.update();
		hero.update(delta);
		
		for(int i = 0; i < meteoritenList.size(); i++){
			meteoritenList.get(i).update(delta);
			
			/*
			 * Meteoriten welche nicht mehr auf dem Bildschirm sind entfernen, damit
			 * neue gespawned werden koennen
			 */
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
		if(backgroundYpos >= -1980){
			backgroundYpos -= 1;
		}
		else{
			backgroundYpos = 0;
		}
		
		/*
		 * Neue Meteoriten spawnen
		 */
		if(meteoritenList.size() < 8){
			meteoritenList.add(new Meteor(rand.nextInt(435), 950, rand.nextInt(3), rand.nextInt(4) + 2));
		}
	}
	
	private void handleInput(float delta){
		if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)){
			hero.setXpos(hero.getXpos() - 8);
			hero.setState(State.FLYINGLEFT);
		}
		
		if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)){
			hero.setXpos(hero.getXpos() + 8);
			hero.setState(State.FLYINGRIGHT);
		}
		
		if(!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.RIGHT)){
			hero.setState(State.IDLE);
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
