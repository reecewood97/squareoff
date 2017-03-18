package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import GameLogic.Board;
import UserInterface.mainMenu;

/**
 * The Server Receiver receives inputs a client and controls what action is taken.
 * @author David
 *
 */
public class ServerReceiver extends Thread {
	
	private ObjectInputStream fromClient;
	private Board board;
	private boolean running, inGame;
	private ArrayList<String> players;
	private ClientTable table;
	private String name;
	private AIManager ais;
	
	/**
	 * Creates a new Server Receiver.
	 * @param fromClient The ObjectOutputStream from the client.
	 * @param board The server-side board.
	 * @param players The list of players connected to the server.
	 * @param table The table Server Receivers and Server Senders of all the clients.
	 * @param ais The AIManager of the server.
	 */
	public ServerReceiver(ObjectInputStream fromClient, Board board, ArrayList<String> players, ClientTable table, AIManager ais) {
		this.fromClient = fromClient;
		this.board = board;
		this.players = players; 
		this.table = table;
		this.ais = ais;
		name = null;
		running = false;
	}
	
	/**
	 * Thread run method.
	 */
	public void run() {
		running = true;
		inGame = false;
		
		try {
			//Reads the name of the client.
			name = (String)fromClient.readObject();
			
			//Checks if a client with that nickname is already connected.
			if(players.contains(name)) {
				name = null;
				table.get(this).send(Server.DISCONNECT);
			}
			//Allows the client to join if there aren't too many clients already joined.
			else if(players.size() < 4){
				table.get(this).send(Server.ACCEPTED);
				players.add(name);
				board.addName(name);
			}
			//Currently if more than 4 clients join, they can spectate.
			else {
				table.get(this).send(Server.DISCONNECT);
			}
			
			//For testing.
			setName(name + ": server receiver");
			table.get(this).setName(name + ": server sender");
			
			Object obj;
			while(running) {
				obj = fromClient.readObject();
				
				//Strings from the client are sent to the board.
				if(inGame && obj.getClass().isInstance("")) {
					board.input((String)obj);
				}
				else if(obj.getClass().isInstance(0)) {
					
					//The client asks for the player list.
					if((int)obj == Server.PLAYERLIST) {
						ArrayList<String> dummy = new ArrayList<String>();
						dummy.addAll(players);
						table.get(this).send(dummy);
					}
				}
			}
		}
		catch(IOException e) {
			//The connection was broken.
			close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Called when the game is starting.
	 */
	public void startGame() {
		inGame = true;
	}
	
	/**
	 * Kills the thread and cleans up, removing the client from the Client Table and the player list.
	 */
	public void close() {
		running = false;
		players.remove(name);
		board.removeName(name);
		table.get(this).send("I kill the Server sender.");
		table.remove(this);
		if(inGame) {
			ais.addAIs(1);
		}
	}
	
	/**
	 * Gets the player name. Waits for it to get it from the client. Will dead-lock if it doesn't receive a name.
	 * @return The nickname of the client.
	 */
	public String getPlayerName() {
		while(name == null) {
			try {
				sleep(50);
			}	
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return name;
	}
}
