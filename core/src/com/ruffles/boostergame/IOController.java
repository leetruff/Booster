package com.ruffles.boostergame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class IOController {

	public static Preferences prefs = Gdx.app.getPreferences("boosterprefs");

	public static boolean qualifiesForHighscore(int score) {
		
		int highscoreScore3 = 0;
		
		
		if(!IOController.prefs.contains("highscoreScore3"))
			return true;
			
		if(IOController.prefs.contains("highscoreScore3")){
			highscoreScore3 = IOController.prefs.getInteger("highscoreScore3");
		}
		
		if(score > highscoreScore3)
			return true;
		
		return false;
	}
	
	public static void addToHighscore(String name, int score){
		
		String highscoreName1 = "";
		String highscoreName2 = "";
		String highscoreName3 = "";
		
		int highscoreScore1 = -1;
		int highscoreScore2 = -1;
		int highscoreScore3 = -1;
		
		if(IOController.prefs.contains("highscoreName1")){
			highscoreName1 = IOController.prefs.getString("highscoreName1", "-1");
		}
		
		if(IOController.prefs.contains("highscoreScore1")){
			highscoreScore1 = IOController.prefs.getInteger("highscoreScore1");
		}
		
		if(IOController.prefs.contains("highscoreName2")){
			highscoreName2 = IOController.prefs.getString("highscoreName2", "-1");
		}
		
		if(IOController.prefs.contains("highscoreScore2")){
			highscoreScore2 = IOController.prefs.getInteger("highscoreScore2");
		}
		
		if(IOController.prefs.contains("highscoreName3")){
			highscoreName3 = IOController.prefs.getString("highscoreName3", "-1");
		}
		
		if(IOController.prefs.contains("highscoreScore3")){
			highscoreScore3 = IOController.prefs.getInteger("highscoreScore3");
		}
		
		
		if(score > highscoreScore1){
			highscoreName3 = highscoreName2;
			highscoreScore3 = highscoreScore2;
			
			highscoreName2 = highscoreName1;
			highscoreScore2 = highscoreScore1;
			
			
			highscoreName1 = name;
			highscoreScore1 = score;
			
			prefs.putString("highscoreName1", highscoreName1);
			prefs.putInteger("highscoreScore1", highscoreScore1);
			
			prefs.putString("highscoreName2", highscoreName2);
			prefs.putInteger("highscoreScore2", highscoreScore2);
			
			prefs.putString("highscoreName3", highscoreName3);
			prefs.putInteger("highscoreScore3", highscoreScore3);
			
			prefs.flush();
		}
		
		else if(score > highscoreScore2){
			highscoreName3 = highscoreName2;
			highscoreScore3 = highscoreScore2;
			
			highscoreName2 = name;
			highscoreScore2 = score;
			
			prefs.putString("highscoreName1", highscoreName1);
			prefs.putInteger("highscoreScore1", highscoreScore1);
			
			prefs.putString("highscoreName2", highscoreName2);
			prefs.putInteger("highscoreScore2", highscoreScore2);
			
			prefs.putString("highscoreName3", highscoreName3);
			prefs.putInteger("highscoreScore3", highscoreScore3);
			
			prefs.flush();
		}
		
		else if(score > highscoreScore3){
			highscoreName3 = name;
			highscoreScore3 = score;
			
			prefs.putString("highscoreName1", highscoreName1);
			prefs.putInteger("highscoreScore1", highscoreScore1);
			
			prefs.putString("highscoreName2", highscoreName2);
			prefs.putInteger("highscoreScore2", highscoreScore2);
			
			prefs.putString("highscoreName3", highscoreName3);
			prefs.putInteger("highscoreScore3", highscoreScore3);
			
			prefs.flush();
		}
		
		
	}
}
