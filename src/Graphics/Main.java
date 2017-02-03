package Graphics;

import java.io.IOException;

import GameLogic.Board;

public class Main {
	
	public Main() {
	
	}
	
	
	public static void main(String[] args) throws IOException {
	   
		
		Audio audio = new Audio();
		//audio.backgroundMusic();
		SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();
		Screen newui = new Screen(new Board());

			
		
		
	}
	
}