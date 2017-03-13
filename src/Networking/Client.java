package Networking;

import java.net.*;
import java.util.ArrayList;
import GameLogic.Board;
import Graphics.Screen;

import java.io.*;

/**
 * This can connect to a specified server.
 * Creates the client-side version of the game and also threads that 
 * communicate with the server. Additionally creates the game's user interface.
 * @author David
 *
 */
public class Client {
	
	private String name; //Client nickname.
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private ClientSender sender;
	private ClientReceiver receiver;
	private Board board; //Client-side version of the game.
	private Queue q; //A queue of objects to be sent to the server.
	private Screen ui; //The game UI.
	
	/**
	 * Creates a new Client.
	 * @param name The client's nickname.
	 */
	public Client(String name) {
		this.name = name;
		socket = null;
		toServer = null;
		fromServer = null;
		board = new Board("map1");
		q = new Queue();
		ui = new Screen(board, q, name);
	}
	
	/**
	 * Connects the client to a specified server.
	 * @param ip 
	 * @param port
	 * @return True if connection was successful and false otherwise.
	 */
	public boolean connect(String ip, int port) {
		if(isConnected()) return false;
		
		//Creates a socket connecting to the server and then creates threads to communicate with the server.
		try {
			socket = new Socket(ip, port);
		}
		catch(IOException e) {
			//Cannot find the server.
			return false;
		}
		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			fromServer = new ObjectInputStream(socket.getInputStream());
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		q.clear();
		
		sender = new ClientSender(toServer, q);
		receiver = new ClientReceiver(fromServer, board, ui, this);
		
		//For testing purposes.
		sender.setName(name + ": client sender");
		receiver.setName(name + ": client receiver");
		
		sender.start();
		receiver.start();
		
		//Send the client's name to the server.
		sender.send(name);

		//Waits for the server to respond to the client.
		return isConnected();
	}
	
	/**
	 * Disconnects the client from a server.
	 */
	public void disconnect() {
		//Makes the UI invisible. Doesn't turn off the music however.
		ui.setInvisible();
		
		//Do nothing if the client is not connected.
		if(socket == null) return;
		
		//Close the socket - this should join all the threads.
		try {
			socket.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		q.offer("I kill the client sender.");
		
		socket = null;
	}
	
	/**
	 * Gets the current list of players from the server.
	 * @return The current list of players or null if the client is not connected.
	 */
	public ArrayList<String> getPlayers() {
		if(!isConnected()) return null;
		sender.send(Server.PLAYERLIST);
		return receiver.getPlayers();
	}
	
	/**
	 * Tests if the client in the game.
	 * @return True if the client is in a game or false if they are in a lobby or not connected to a server.
	 */
	public boolean inGame() {
		return receiver.inGame();
	}
	
	/**
	 * Tests if the client is connected to a server. If it has just connected, it will wait until the server has responded.
	 * @return If the client is connected to a server.
	 */
	public boolean isConnected() {
		return socket != null && !socket.isClosed() && receiver.waitForAccept();
	}
}
