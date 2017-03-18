package Networking;

import GameLogic.Board;

/**
 * Sends a "None" move to the server-side board every frame to force it to update.
 * @author David
 *
 */
public class GameLoop extends Thread {
	
	private Board board;
	private boolean running;
	
	public GameLoop(Board board) {
		this.board = board;
	}
	
	/**
	 * Thread run method.
	 */
	public void run() {
		running = true;
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
	
	/**
	 * Kills the thread.
	 */
	public void close() {
		running = false;
	}
}
