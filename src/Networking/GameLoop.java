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
			System.out.println("update");
			try {
				sleep(40);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
