package graphics;

import gameLogic.Board;
import gameLogic.UserInput;
import networking.Queue;

public class Main {
	
	public static void main(String[] args) {
	   
		SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();
		//@SuppressWarnings("unused")
		Screen newui = new Screen(new Board(),new Queue());
		newui.setVisible();

	}
	
}