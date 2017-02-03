package Graphics;

import GameLogic.Board;

public class Main {
	
	public static void main(String[] args) {
	   
		
		@SuppressWarnings("unused")
		Audio audio = new Audio();
		SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();
		@SuppressWarnings("unused")
		Screen newui = new Screen(new Board());

	}
	
}