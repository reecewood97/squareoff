package Networking;

import java.io.BufferedReader;
import java.io.IOException;

import GameLogic.Board;

/**
 * The client receiver class. This is a thread that waits for a message from the server before relaying it to the Board.
 * @author djs568
 *
 */
public class ClientReceiver extends Thread {
	
	private BufferedReader server;
	private Board board;
	private boolean running;
	
	/**
	 * Constructor.
	 * @param server
	 */
	public ClientReceiver(BufferedReader server, Board board) {
		this.server = server;
		this.board = board;
	}
	
	/**
	 * Run method for the thread.
	 */
	public void run() {
		running = true;
		
		//Constantly waits for a string from the server and sends it to the board.
		try {
			while(running) {
				board.update(server.readLine());
			}
			
			//Closes the BufferedReader if the thread has been itself closed.

		}
		catch(IOException e) {
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







