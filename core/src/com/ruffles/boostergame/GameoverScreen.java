package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameoverScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	Texture background;
	int backgroundYpos = 0;
	
	
	Stage stage;
	
	TextButtonStyle style;
	
	TextButton playagainButton;
	TextButton menuButton;
	
	Skin skin;
	private TextureAtlas atlas;
	
	FreeTypeFontGenerator generator;
	
	int score;
	BitmapFont scoreFont;
	
	String highscoreName1;
	String highscoreName2;
	String highscoreName3;
	
	int highscoreScore1;
	int highscoreScore2;
	int highscoreScore3;
	
	Texture goldmedal;
	Texture silvermedal;
	Texture bronzemedal;
	private BitmapFont scoreFont2;
	
	public GameoverScreen(MyGdxGame game, int backgroundYpos, int score) {
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
		
		
		menuButton = new TextButton("Menu", style);
		menuButton.setSize(300, 75);
		menuButton.setPosition(490/2 - menuButton.getWidth() / 2, 120);
		stage.addActor(menuButton);
		
		playagainButton = new TextButton("Play again", style);
		playagainButton.setSize(300, 75);
		playagainButton.setPosition(490/2 - playagainButton.getWidth() / 2, 220);
		stage.addActor(playagainButton);
		
		menuButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	Gdx.input.setInputProcessor(null);
	            game.setScreen(new MainMenu(game, backgroundYpos));
	        }
	    });
		
		playagainButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	Gdx.input.setInputProcessor(null);
	            game.setScreen(new GameScreen(game, backgroundYpos));
	        }
	    });
		
		/*
		 * Font fuer Score Anzeige
		 */
		
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter2.size = 35;
		scoreFont = generator.generateFont(parameter2);
		
		FreeTypeFontParameter parameter3 = new FreeTypeFontParameter();
		parameter3.size = 25;
		scoreFont2 = generator.generateFont(parameter3);
		
		/*
		 * Medaillen Texturen laden
		 */
		
		goldmedal = new Texture(Gdx.files.internal("medals/goldmedalsmall.png"));
		silvermedal = new Texture(Gdx.files.internal("medals/silvermedalsmall.png"));
		bronzemedal = new Texture(Gdx.files.internal("medals/bronzemedalsmall.png"));
		
		/*
		 * Highscores aus .prefs auslesen
		 */
		
//		highscoreName1 = "Lars";
//		highscoreName2 = "Lars";
//		highscoreName3 = "Lars";
//		
//		highscoreScore1 = 16512;
//		highscoreScore2 = 12941;
//		highscoreScore3 = 5122;
		
		if(IOController.prefs.getString("highscoreName1") != null){
			highscoreName1 = IOController.prefs.getString("highscoreName1", "-1");
		}
		
		if(IOController.prefs.getInteger("highscoreScore1") > 0){
			highscoreScore1 = IOController.prefs.getInteger("highscoreScore1");
		}
		
		if(IOController.prefs.getString("highscoreName2") != null){
			highscoreName2 = IOController.prefs.getString("highscoreName2", "-1");
		}
		
		if(IOController.prefs.getInteger("highscoreScore2") > 0){
			highscoreScore2 = IOController.prefs.getInteger("highscoreScore2");
		}
		
		if(IOController.prefs.getString("highscoreName3") != null){
			highscoreName3 = IOController.prefs.getString("highscoreName3", "-1");
		}
		
		if(IOController.prefs.getInteger("highscoreScore3") > 0){
			highscoreScore3 = IOController.prefs.getInteger("highscoreScore3");
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 490, 2048, 0, backgroundYpos, 490, 2048, false, false);
		scoreFont.draw(batch, "Final Score:", 120, 800);
		
		if(score < 1000)
		scoreFont.draw(batch, score + "", 205, 750);
		
		else if(score <10000)
		scoreFont.draw(batch, score + "", 195, 750);
		
		else if(score <100000)
		scoreFont.draw(batch, score + "", 188, 750);
		
		else
		scoreFont.draw(batch, score + "", 184, 750);
		
		
		if(highscoreName1 != "-1"){
			scoreFont2.draw(batch, highscoreName1, 85, 600);
			scoreFont2.draw(batch, highscoreScore1 + "", 345, 600);
			
		}
		scoreFont2.draw(batch, "-", 250, 600);
		batch.draw(goldmedal, 55, 577);
		
		if(highscoreName2 != "-1"){
			scoreFont2.draw(batch, highscoreName2, 85, 550);
			scoreFont2.draw(batch, highscoreScore2 + "", 345, 550);
			
		}
		scoreFont2.draw(batch, "-", 250, 550);
		batch.draw(silvermedal, 55, 527);
		
		if(highscoreName3 != "-1"){
			scoreFont2.draw(batch, highscoreName3, 85, 500);
			scoreFont2.draw(batch, highscoreScore3 + "", 345, 500);
			
		}
		scoreFont2.draw(batch, "-", 250, 500);
		batch.draw(bronzemedal, 55, 477);
		
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
