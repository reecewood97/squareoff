package Networking;

import java.io.IOException;

import javax.swing.text.StyledEditorKit.ItalicAction;

import GameLogic.Board;

/**
 * Sends a "None" move to the server-side board every frame to force it to update.
 * @author David
 *
 */
public class GameLoop extends Thread {
	
	private Board board;
	
	public GameLoop(Board board) {
		this.board = board;
	}
	
	/**
	 * Thread run method.
	 */
	public void run() {
		try {
			while(true) {
				board.input("None");
				sleep(35);
			}
		}
		catch(InterruptedException e) {
			//Thread killed.
		}
	}
}
