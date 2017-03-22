package Graphics;

import GameLogic.Board;
import Networking.Client;
import Networking.Queue;

public class Main {
	
	public static void main(String[] args) {
	   
		SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();
		Screen newui = new Screen(new Board(),new Queue(),"fran", new Client("fran"));
		newui.setVisible();

	}
	
}