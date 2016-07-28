package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class AddHighscoreNameScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	Texture background;
	int backgroundYpos = 0;
	
	
	Stage stage;
	
	TextButtonStyle style;
	
	TextButton confirmButton;
	
	Skin skin;
	private TextureAtlas atlas;
	
	FreeTypeFontGenerator generator;
	
	BitmapFont scoreFont;
	int score;
	private BitmapFont scoreFont2;
	
	TextField nameField;
	private BitmapFont scoreFont3;
	
	public AddHighscoreNameScreen(MyGdxGame game, int backgroundYpos, int score) {
		this.game = game;
		this.backgroundYpos = backgroundYpos;
		this.score = score;
	}
	
	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		batch = new SpriteBatch();
		
		background = new Texture(Gdx.files.internal("spacebackground.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		
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
		
		
		confirmButton = new TextButton("Confirm", style);
		confirmButton.setSize(300, 75);
		confirmButton.setPosition(490/2 - confirmButton.getWidth() / 2, 120);
		stage.addActor(confirmButton);
		
		
		confirmButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	//TODO
	        	//Eingabefeld fuer Namen und Highscore adden
	        	String name = nameField.getText();
	        	
	        	if(name.length() > 10){
	        		name = name.substring(0, 10);
	        	}
	        	
	        	IOController.addToHighscore(name, score);
	        	Gdx.input.setInputProcessor(null);
	            game.setScreen(new GameoverScreen(game, backgroundYpos, score));
	        }
	    });
		
		
		/*
		 * Font fuer Schriftzuege
		 */
		
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter2.size = 35;
		scoreFont = generator.generateFont(parameter2);
		
		FreeTypeFontParameter parameter3 = new FreeTypeFontParameter();
		parameter3.size = 25;
		scoreFont2 = generator.generateFont(parameter3);
		
		FreeTypeFontParameter parameter4 = new FreeTypeFontParameter();
		parameter4.size = 25;
		scoreFont3 = generator.generateFont(parameter4);
		
		
		
		TextFieldStyle style = new TextFieldStyle();
		style.background = skin.getDrawable("textbox_01");
		style.disabledBackground = skin.getDrawable("textbox_01");
		style.cursor = skin.getDrawable("textbox_cursor_02");
		style.font = scoreFont3;
		style.fontColor = Color.BLACK;
		
		nameField = new TextField("", style);
		nameField.setPosition(100, 520);
		nameField.setSize(350, 50);
		
		stage.addActor(nameField);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		nameField.setPosition(73, 530);
		nameField.setSize(350, 50);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 490, 2048, 0, backgroundYpos, 490, 2048, false, false);
		
		scoreFont.draw(batch, "You qualified", 100, 800);
		scoreFont.draw(batch, "for highscore!", 100, 750);
		
		scoreFont2.draw(batch, "Enter name:", 160, 630);
		
		batch.end();
		
		//TODO Buttons
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
