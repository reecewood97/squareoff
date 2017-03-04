package Networking;

import GameLogic.Board;

public class GameLoop extends Thread {
	
	private Board board;
	private boolean running;
	
	public GameLoop(Board board) {
		this.board = board;
	}
	
	public void run() {
		running = true;
		while(running) {
			board.input("None");
			try {
				sleep(35); //Cranking up to a dank FPS
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
