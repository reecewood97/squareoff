package UserInterface;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Refresher extends Thread {

	private Stage primaryStage;
	private Scene scene1;
	private mainMenuNetwork net;
	private boolean b;
	
	public Refresher(Stage primaryStage, Scene scene1, mainMenuNetwork net, boolean b) {
		this.primaryStage = primaryStage;
		this.scene1 = scene1;
		this.net = net;
		this.b = b;
		
	}

	public void run() {
		try {
			while(b) {
				mainMenu.refreshHLobby(primaryStage, scene1, net);
				sleep(500);
			}
			
			while(!b) {
				mainMenu.refreshCLobby(primaryStage, scene1, net);
				sleep(500);
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}