package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenu implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	Texture background;
	int backgroundYpos = 0;
	
	Texture boosterTexture;
	
	Stage stage;
	
	TextButtonStyle style;
	
	TextButton playButton;
	TextButton highscoreButton;
	TextButton optionsButton;
	
	Skin skin;
	private TextureAtlas atlas;
	
	FreeTypeFontGenerator generator;
	
	
	public MainMenu(MyGdxGame game, int backgroundYpos) {
		this.game = game;
		this.backgroundYpos = backgroundYpos;
	}
	
	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		
		batch = new SpriteBatch();
		
		background = BigAssets.spaceBackground;
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		boosterTexture = new Texture(Gdx.files.internal("boosterbackground2.png"));
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		atlas = new TextureAtlas(Gdx.files.internal("ui/ui-orange.atlas"));
		skin.addRegions(atlas);
		
		style = new TextButtonStyle();
		style.over = skin.getDrawable("button_01");
		style.up = skin.getDrawable("button_03");
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ocraextended.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 25;
		style.font = generator.generateFont(parameter);
		
		
		playButton = new TextButton("Play", style);
		playButton.setSize(300, 75);
		playButton.setPosition(490/2 - playButton.getWidth() / 2, 320);
		stage.addActor(playButton);
		
		highscoreButton = new TextButton("Highscore", style);
		highscoreButton.setSize(300, 75);
		highscoreButton.setPosition(490/2 - highscoreButton.getWidth() / 2, 220);
		stage.addActor(highscoreButton);
		
		optionsButton = new TextButton("Options", style);
		optionsButton.setSize(300, 75);
		optionsButton.setPosition(490/2 - optionsButton.getWidth() / 2, 120);
		stage.addActor(optionsButton);
		
		playButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	Gdx.input.setInputProcessor(null);
	            game.setScreen(new GameScreen(game, backgroundYpos));
	        }
	    });
		
		highscoreButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.setScreen(new HighscoreScreen(game, backgroundYpos));
	        }
	    });
		
		optionsButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.setScreen(new OptionsScreen(game, backgroundYpos));
	        }
	    });
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 490, 2048, 0, backgroundYpos, 490, 2048, false, false);
		batch.draw(boosterTexture, 46, 550);
		batch.end();
		
		stage.act();
		stage.draw();
		
		Gdx.graphics.setTitle("Booster | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		
		/*
		 * Background loop
		 */
		if(backgroundYpos > Integer.MIN_VALUE){
			backgroundYpos -= 1;
		}
		else{
			backgroundYpos = 0;
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
