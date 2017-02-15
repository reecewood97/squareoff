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
	private ClientSender sender;

	
	/**
	 * Constructor.
	 * @param server
	 */
	public ClientReceiver(ObjectInputStream server, Board board, Screen ui, ClientSender sender) {
		this.server = server;
		this.board = board;
		this.ui = ui;
		players = new ArrayList<String>();
		this.sender = sender;
	}
	
	/**
	 * Run method for the thread.
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		inGame = false;
		
		try {
			Object ob;
			while(!inGame && (ob = server.readObject()) != null) {
				if(ob.getClass().isInstance(players)) 
					players = (ArrayList<String>) ob;
				else if((int)ob == Server.PLAY) {
					inGame = true;
				}
				
			}
			
			ui.setVisible();
			
			while(inGame) {
				ArrayList<PhysObject> x = (ArrayList<PhysObject>) server.readObject();
	
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







