package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import GameLogic.Board;
import GameLogic.PhysObject;
import Graphics.Screen;

/**
 * Receives information from the server and uses this to update the game.
 * @author David
 *
 */
public class ClientReceiver extends Thread {
		
	private ObjectInputStream server;
	private Board board; //Client-side board.
	private boolean running, inGame;
	private Screen ui;
	private ArrayList<String> players;
	private Client client;
	private int state; //Whether the server has accepted the client.

	
	/**
	 * Creates a new Client Receiver.
	 * @param server The ObjectInput stream from the server.
	 * @param board The client-side board.
	 * @param ui The UI.
	 * @param client The client.
	 */
	public ClientReceiver(ObjectInputStream server, Board board, Screen ui, Client client) {
		this.server = server;
		this.board = board;
		this.ui = ui;
		this.client = client;
		running = false;
		players = null;
		state = Server.BASE;
	}
	
	/**
	 * Thread run method.
	 */
	@SuppressWarnings("unchecked")
	public void run() {		
		running = true;
		inGame = false;
		
		try {
			Object ob;
			ArrayList<String> anArrayList = new ArrayList<String>();
			while(running) {
				//Reads in an object from the server.
				ob = server.readObject();
				
				//Ints from the server tell say what to do.
				if(ob.getClass().isInstance(0)) {
					//Start the game.
					if((int)ob == Server.PLAY && !inGame) {
						inGame = true;
						ui.startGame();
					}
					//Client has connected to a server.
					else if((int)ob == Server.ACCEPTED) {
						state = Server.ACCEPTED;
					}
					//Server has told the client to disconnect.
					else if((int)ob == Server.DISCONNECT) {
						state = Server.DISCONNECT;
						client.disconnect();
					}
				}
				//Specific objects whilst in-game.
				if(inGame) {
					//The ArrayList to update the board with.
					if(ob.getClass().isInstance(anArrayList)) {
						board.setObjects((ArrayList<PhysObject>) ob);
						ui.updateSBoard();
					}
					//If the game has finished.
					else if ((int)ob == 33){
						//running = false;
						int winner = 3;//This needs to be a read in
						board.setWinner(winner);
						//ui.winScreen();
					}
					//Not sure...
					else if ((int)ob == 34){
						board.startLocalTimer();
					}
				}
				//Whilst not in-game.
				else {
					//The current list of players in the lobby.
					if(ob.getClass().isInstance(anArrayList)) {
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
			//The socket has closed.
		}
	}
	
	/**
	 * Waits for the current list of players from the server. Will cause dead-lock if server doesn't send the list.
	 * @return The list of players from the server.
	 */
	public ArrayList<String> getPlayers() {
		//Waits for the list.
		while(players == null) {
			try {
				sleep(50);
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
	
	/**
	 * Returns if the client is in a game currently.
	 * @return If the client is in a game.
	 */
	public boolean inGame() {
		return inGame;
	}
	
	/**
	 * Waits for the server to accept a client if it hasn't already. Returns false if the server rejects the client.
	 * Will dead-lock if server doesn't respond.
	 * @return If the client has been accepted by the server.
	 */
	public boolean waitForAccept() {
		while(state == Server.BASE) {
			try {
				sleep(50);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return state == Server.ACCEPTED;
	}
}







