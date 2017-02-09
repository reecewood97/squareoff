package Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import GameLogic.Board;
import Graphics.Screen;

/**
 * The client receiver class. This is a thread that waits for a message from the server before relaying it to the Board.
 * @author djs568
 *
 */
public class ClientReceiver extends Thread {
	
	private ObjectInputStream server;
	private Board board;
	private boolean running;
	private Screen UI;
	private Queue q;
	
	/**
	 * Constructor.
	 * @param server
	 */
	public ClientReceiver(ObjectInputStream server, Board board, Screen UI, Queue q) {
		this.server = server;
		this.board = board;
		this.UI = UI;
		this.q = q;
	}
	
	/**
	 * Run method for the thread.
	 */
	public void run() {
		running = true;
		
		//Constantly waits for a string from the server and sends it to the board.
		try {
			while(running) {
				Board x = (Board) server.readObject();
				board.update(x);
				UI = new Screen(x,q);
				//Updoot fran
			}
			
			//Closes the BufferedReader if the thread has been itself closed.

		}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Terminates the while loop that checks for a new line from the server and closes the BufferedReader..
	 */
	public void close() {
		running = false;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}







