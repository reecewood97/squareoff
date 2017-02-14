package Networking;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;
import Graphics.Screen;

/**
 * The client receiver class. This is a thread that waits for a message from the server before relaying it to the Board.
 * @author djs568
 *
 */
public class ClientReceiver extends Thread {
	
	private ObjectInputStream server;
	private Board board;
	private Queue q;
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
		players = new ArrayList<String>();
	}
	
	/**
	 * Run method for the thread.
	 */
	public void run() {
		inGame = true;
		
		try {
			while(!inGame) {
				ArrayList<String> dummy = new ArrayList<String>();
				Object ob = server.readObject();
				if(!ob.equals(0)) 
					dummy.add((String) ob);
				else {
					players = dummy;
					dummy.clear();
				}
					
					
			}
			
			while(inGame) {
				ArrayList<PhysObject> x = (ArrayList<PhysObject>) server.readObject();
				//System.out.println(x);
	
				board.setObjects(x);
				ui.updateSBoard();
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







