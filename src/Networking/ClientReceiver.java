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
	private boolean running, inGame;
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
	@SuppressWarnings("unchecked")
	public void run() {
		
		running = true;
		inGame = false;
		
		try {
			Object ob;
			ArrayList<PhysObject> check = new ArrayList<PhysObject>();
			while(running && (ob = server.readObject()) != null) {
				//System.out.println(ob);        This keeps on printing...
				if(inGame && ob.getClass().isInstance(check)) {
					board.setObjects((ArrayList<PhysObject>) ob);
					ui.updateSBoard();
				}
				else if(!inGame && ob.getClass().isInstance(players)) 
					players = (ArrayList<String>) ob;
				else if((int)ob == Server.PLAY) {
					inGame = true;
					ui.setVisible();
//					Updater update = new Updater(ui);
//					update.start();
				}
				else if((int)ob == Server.QUIT) {
					running = false;
				}
				else if ((int)ob == 33){
					//running = false;
					int winner = 3;//This needs to be a read in
					board.setWinner(winner);
					ui = new Screen(board,q,"");
				}
			}
			
			ui.setInvisible();
			server.close();
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







