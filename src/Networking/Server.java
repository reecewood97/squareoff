package Networking;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import GameLogic.Board;
import ai.*;

/**
 * The server which clients can connect to. Contains a "master" copy of the game
 * which are sent to all the clients when playing.
 * 
 * @author djs568
 *
 */
public class Server extends Thread {

	public static final int BASE = 0;
	public static final int PLAY = 1;
	public static final int DISCONNECT = 2;
	public static final int ACCEPTED = 3;
	public static final int RESET_CONNECTION = 4;

	public static final int PLAYERLIST = 5;

	public static final int EASY_AI = 11;
	public static final int NORMAL_AI = 12;
	public static final int HARD_AI = 13;

	private int port;
	private Board board;
	private boolean running, inGame;
	private ServerSocket socket;
	private ArrayList<String> players;
	private ClientTable table;
	private AIManager ais;
	private GameLoop gl;

	/**
	 * Creates a new Server.
	 * 
	 * @param port
	 *            The port.
	 */
	public Server(int port) {
		this.port = port;
		board = new Board();
		players = new ArrayList<String>();
		socket = null;
		table = new ClientTable();
		running = false;
		ais = new AIManager(board, players, 4);
		gl = new GameLoop(board);
	}

	/**
	 * Thread run method.
	 */
	public void run() {

		// Create a new ServerSocket.
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		running = true;

		try {
			while (running) {
				// Allows clients to connect to the server.
				Socket s = socket.accept();

				// Creates Threads to communicate with a client that has
				// connected.
				ObjectInputStream fromClient = new ObjectInputStream(s.getInputStream());
				ServerReceiver sr = new ServerReceiver(fromClient, board, players, table, ais);

				ObjectOutputStream toClient = new ObjectOutputStream(s.getOutputStream());
				ServerSender ss = new ServerSender(toClient, board, this);

				// Adds Threads to a Client Table.
				table.add(sr, ss);

				ss.start();
				sr.start();
			}
		} catch (IOException e) {
			// Server Socket closed.
		}
	}

	/**
	 * Closes the Server Socket. Will become dead-locked if clients fail to
	 * disconnect.
	 */
	public void close() {
		// Tell everyone to disconnect.
		table.sendAll(Server.DISCONNECT);
		running = false;

		// Wait for everyone to disconnect.
		while (table.size() > 0) {
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		// Close the Server Socket.
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Kill the AIManager and GameLoop threads.
		ais.interrupt();
		gl.interrupt();
	}

	/**
	 * Starts the game with AIs.
	 */
	public void startGame() {

		// Do nothing if server is in game.
		if (inGame)
			return;

		inGame = true;
		
		// Wait for all players to be in the lobby.
		for (ServerReceiver s : table.getReceivers()) {
			s.getPlayerName();
		}

		// Adds AIs.
		ais.addAIs();

		// Tells everyone that the game is starting.
		for (ServerReceiver r : table.getReceivers()) {
			r.startGame();
			table.get(r).startGame();
		}

		// Start the board, the GameLoop and the AIs.
		board.startGame();
		gl.start();
		ais.start();
	}

	/**
	 * Kicks a client from the server. Will become dead-locked if client exists
	 * but doesn't disconnect.
	 * 
	 * @param name
	 *            The name of the client to be kicked.
	 * @return If the client was successfully kicked.
	 */
	public boolean kick(String name) {
		// Iterate through the Server Receivers.
		for (ServerReceiver r : table.getReceivers()) {

			// Finds the client with that name.
			if (r.getPlayerName().equals(name)) {

				// Tells them to disconnect.
				table.get(r).send(Server.DISCONNECT);

				// Waits for them to disconnect.
				while (players.contains(name)) {
					try {
						sleep(50);
					} catch (InterruptedException e) {
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
	 * 
	 * @return The current player list.
	 */
	public ArrayList<String> getPlayers() {
		return players;
	}

	/**
	 * Sets the AI difficulty.
	 * 
	 * @param aiDifficulty
	 *            The new AI difficulty.
	 */
	public void setAIDifficulty(int difficulty) {
		ais.setDifficulty(difficulty);
	}

	/**
	 * Disconnects all clients from the server and then reconnects them, resetting everything to as it should be before the game..
	 */
	public void reset() {
		// Reset and close everything.
		players.clear();
		inGame = false;
		for (ServerReceiver r : table.getReceivers()) {
			r.setInGame(false);
		}
		
		board = new Board();
		ais.interrupt();
		ais = new AIManager(board, players, 4);
		gl.interrupt();
		gl = new GameLoop(board);
		
		//Tell all clients to reconnect.
		table.sendAll(Server.RESET_CONNECTION);
	}
}
