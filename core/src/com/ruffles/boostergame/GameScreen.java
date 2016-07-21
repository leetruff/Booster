package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.ruffles.boostergame.Hero.State;

public class GameScreen implements Screen {
	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	Hero hero;

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
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		handleInput(delta);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		hero.draw(batch);
		batch.end();
	}

	private void update(float delta) {
		cam.update();
		hero.update(delta);
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
