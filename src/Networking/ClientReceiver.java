package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

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
	private boolean inGame;
	private Screen ui;
	private ArrayList<String> players;
	
	/**
	 * Constructor.
	 * @param server
	 */
	public ClientReceiver(ObjectInputStream server, Board board, Screen ui) {
		this.server = server;
		this.board = board;
		this.ui = ui;
	}
	
	/**
	 * Run method for the thread.
	 */
	public void run() {
		inGame = false;
		
		try {
			while(!inGame) {
				Object ob = server.readObject();
				if(players.getClass().isInstance(ob))
					players = (ArrayList<String>) server.readObject();
				
			}
			
			while(inGame) {
				board.update(server.readObject());
			}
			

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
		inGame = false;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public ArrayList<String> getPlayers() {
		return players;
	}
}







