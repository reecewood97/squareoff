package Networking;

import GameLogic.*;

public class GameLoop extends Thread {
	
	private Board board;
	private AIManager ais;
	private boolean running;
	
	public GameLoop(Board board, AIManager ais) {
		this.board = board;
		this.ais = ais;
	}
	
	public void run() {
		running = true;
		ais.start();
		while(running) {
			board.input("None");
			try {
				sleep(35);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
