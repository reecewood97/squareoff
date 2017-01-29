package Graphics;

import GameLogic.Board;

public class Main {
	
	public Main() {
	
	}
	
	
	public static void main(String[] args) {
	   
		
		Audio audio = new Audio();
		//audio.backgroundMusic();
		SplashSplash splashscreen = new SplashSplash(100);
		splashscreen.showSplash();
		Screen newui = new Screen(new Board());
		
			
		//Audio audio2 = new Audio();
			//audio2.explosion();
			//audio2.click();
			
		
		
	}
	
}