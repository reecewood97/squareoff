package Networking;

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
	private Client client;

	
	/**
	 * Constructor.
	 * @param server
	 */
	public ClientReceiver(ObjectInputStream server, Board board, Screen ui, Client client) {
		this.server = server;
		this.board = board;
		this.ui = ui;
		this.client = client;
		players = null;
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
			ArrayList<String> check = new ArrayList<String>();
			while(running && (ob = server.readObject()) != null) {
				if(inGame) {
					if(ob.getClass().isInstance(check)) {
						board.setObjects((ArrayList<PhysObject>) ob);
						ui.updateSBoard();
					}
					else if ((int)ob == 33){
						//running = false;
						int winner = 3;//This needs to be a read in
						board.setWinner(winner);
						ui = new Screen(board,q,"");
					}
					else if ((int)ob == 34){
						board.startLocalTimer();
					}
				}
				else {
					if(ob.getClass().isInstance(0)) {
						if((int)ob == Server.PLAY) {
							inGame = true;
							ui.setVisible();
						}
						else if((int)ob == Server.DISCONNECT) {
							client.disconnect();
						}
					}
					else if(ob.getClass().isInstance(check)) {
							players = (ArrayList<String>)ob;
					}
				}
			}
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch(IOException e) {
			close();
		}
	}

	/**
	 * Terminates the while loop that checks for a new line from the server and closes the BufferedReader.
	 */
	private void close() {
		running = false;
		//System.out.println(getName() + " closed.");
	}
	
	public ArrayList<String> getPlayers() {
		while(players == null) {
			try {
				sleep(10);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		ArrayList<String> newPlayers = players;
		players = null;
		return newPlayers;
	}
	
	public boolean inGame() {
		return inGame;
	}
}







