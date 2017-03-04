package Graphics;

import GameLogic.Board;
import Networking.Queue;

public class Main {
	
	public static void main(String[] args) {
	   
		SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();
		Screen newui = new Screen(new Board("map1"),new Queue(),"fran");
		newui.setVisible();

	}
	
}