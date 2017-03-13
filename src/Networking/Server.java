package Networking;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import GameLogic.Board;
import ai.*;

/**
 * The server which clients can connect to. Contains a "master" copy of the game which
 * are sent to all the clients when playing.
 * 
 * @author djs568
 *
 */
public class Server extends Thread {

	public static final int BASE = 0; 
	public static final int PLAY = 1;
	public static final int DISCONNECT = 2;
	public static final int ACCEPTED = 3;
	public static final int PLAYERLIST = 4;
	public static final int EASY_AI = 11;
	public static final int NORMAL_AI = 12;
	public static final int HARD_AI = 13;
	
	private int port, aiDifficulty;
	private Board board;
	private boolean running;
	private ServerSocket socket;
	private ArrayList<String> players;
	private ClientTable table;
	
	/**
	 * Creates a new Server.
	 * @param port The port.
	 */
	public Server(int port) {
		this.port = port;
		board = new Board("map1");
		players = new ArrayList<String>();
		socket = null;
		table = new ClientTable();
		running = false;
		aiDifficulty = EASY_AI;
	}
	
	/**
	 * Thread run method.
	 */
	public void run() {
		try {
			socket = new ServerSocket(port);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		running = true;
		
		try {
			while(running) {
				//Allows clients to connect to the server.
				Socket s = socket.accept();

				//Creates Threads to communicate with a client that has connected.
				ObjectInputStream fromClient = new ObjectInputStream(s.getInputStream());
				ServerReceiver sr = new ServerReceiver(fromClient, board, players, table);
				sr.start();
				
				ObjectOutputStream toClient = new ObjectOutputStream(s.getOutputStream());
				ServerSender ss = new ServerSender(toClient, board);
				ss.start();
				
				//Adds Threads to a Client Table.
				table.add(sr, ss);
			}			
		}
		catch (IOException e) {
			//Server Socket closed.
		}
	}
	
	/**
	 * Closes the Server Socket. Will become dead-locked if clients fail to disconnect.
	 */
	public void close() {
		//Tell everyone to disconnect.
		table.sendAll(Server.DISCONNECT);
		running = false;
		
		//Wait for everyone to disconnct.
		while(table.size() > 0) {
			try {
			     sleep(50);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		//Close the Server Socket.
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Starts the game with AIs.
	 */
	public void startGame() {
		//Adds AIs.
		AIManager ais = addAIs();
	
		//Tells everyone that the game is starting.
		for(ServerReceiver r: table.getReceivers()) {
			r.startGame();
			table.get(r).startGame();
		}

		//Start the board, the GameLoop and the AIs.
		board.startGame();	
		new GameLoop(board).start();
		ais.start();
	}
	
	/**
	 * Creates an AIManager with AIs in it.
	 * @return An AIManager with AIs in it
	 */
	private AIManager addAIs() {
		AIManager ais = new AIManager(board);
		
		//Wait for all players to be in the lobby.
		for(ServerReceiver s: table.getReceivers()) {
			s.getPlayerName();
		}
		
		int maxPlayers = 4;
		int numberOfPlayers = players.size();
		
		//Fills remaining spots in the game with AIs of a certain difficulty.
		for(int i = numberOfPlayers + 1; i <= maxPlayers; i++) {
			AI ai;
			switch(aiDifficulty) {
				case NORMAL_AI: ai = new NormalAI(i, 0, i, board);
					break;
				case HARD_AI: ai = new DifficultAI(i, 0, i, board);	
					break;
				default: ai = new EasyAI(i, 0, i, board);
			}
			ais.add(ai);
			players.add("AI " + (i - numberOfPlayers));
		}

		return ais;
	}
	
	/**
	 * Kicks a client from the server. Will become dead-locked if client exists but doesn't disconnect.
	 * @param name The name of the client to be kicked.
	 * @return If the client was successfully kicked.
	 */
	public boolean kick(String name) {
		//Iterate through the Server Receivers.
		for(ServerReceiver r: table.getReceivers()) {
			
			//Finds the client with that name.			
			if(r.getPlayerName().equals(name)) {
				
				//Tells them to disconnect.				
				table.get(r).send(Server.DISCONNECT);
				
				//Waits for them to disconnect.
				while(players.contains(name)) {
					try {
						sleep(50);
					}
					catch(InterruptedException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the current player list.
	 * @return The current player list.
	 */
	public ArrayList<String> getPlayers() {
		return players;
	}
	
	/**
	 * Sets the AI difficulty.
	 * @param aiDifficulty The new AI difficulty.
	 */
	public void setAIDifficulty(int aiDifficulty) {
		this.aiDifficulty = aiDifficulty;
	}
}
