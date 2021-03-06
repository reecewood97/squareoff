package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import GameLogic.Board;
import GameLogic.PhysObject;
import Graphics.Screen;
import UserInterface.mainMenu;

/**
 * Receives information from the server and uses this to update the game.
 * @author David
 *
 */
public class ClientReceiver extends Thread {
		
	private ObjectInputStream server;
	private Board board; 
	private boolean inGame;
	private Screen ui;
	private ArrayList<String> players, newPlayers;
	private Client client;
	private int state; 

	
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
		players = null;
		newPlayers = new ArrayList<String>();
		state = Server.BASE;
	}
	
	/**
	 * Thread run method.
	 */
	@SuppressWarnings("unchecked")
	public void run() {		
		inGame = false;
		
		try {
			Object ob;
			ArrayList<String> anArrayList = new ArrayList<String>();
			while(true) {
				//Reads in an object from the server.
				ob = server.readObject();
				
				//Ints from the server tell say what to do.
				if(ob.getClass().isInstance(0)) {
					switch((int)ob) {
						case Server.PLAY: 						
							inGame = true;
							ui.start();
							break;
						case Server.ACCEPTED:
							state = Server.ACCEPTED;
							break;
						case Server.DISCONNECT:
							state = Server.DISCONNECT;
							client.disconnect();
							break;
						case Server.RESET_CONNECTION:
							client.resetConnection();
							break;
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
						int winner = 3;
						board.setWinner(winner);
					}
					//If Clients need to update their timer
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
		int i = 0;
		while(players == null && waitForAccept() && i < 200) {
			try {
				sleep(50);
			}	
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
			i++;
		}
		
		//Sets the client-side playerlist to the new playerlist if it isn't null.
		if(players != null) {
			newPlayers = players;
			players = null;
		}
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
		int i = 0;
		while(state == Server.BASE && i < 200) {
			try {
				sleep(50);
			}
			catch(InterruptedException e) {							
				e.printStackTrace();
				System.exit(1);
			}
			i++;
		}
		return state == Server.ACCEPTED;
	}
}







