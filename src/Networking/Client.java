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
	
	private String name;
	private int port;
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private ClientSender sender;
	private ClientReceiver receiver;
	private Board board; 
	private Queue q; 
	private Screen ui;
	
	/**
	 * Creates a new Client.
	 * @param name The client's nickname.
	 */
	public Client(String name) {
		this.name = name;
		port = -1;
		socket = null;
		toServer = null;
		fromServer = null;
	}
	
	/**
	 * Connects the client to a specified server.
	 * @param ip 
	 * @param port
	 * @return True if connection was successful and false otherwise.
	 */
	public int connect(String ip, int port) {
		//Checks that the client isn't already connected.
		if(isConnected()) return 2;
		
		this.port = port;
	
		//Creates a socket connecting to the server and then creates threads to communicate with the server.
		try {
			socket = new Socket(ip, port);
		}
		catch(IOException e) {
			//Cannot find the server.
			return 1;
		}
		try {
			toServer = new ObjectOutputStream(socket.getOutputStream());
			fromServer = new ObjectInputStream(socket.getInputStream());
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		board = new Board("map1");
		q = new Queue();
		ui = new Screen(board, q, name, this);
		
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
		return (isConnected()?0:2);
	}
	
	/**
	 * Disconnects the client from a server.
	 */
	public void disconnect() {
		//Do nothing if the client is not connected.
		if(socket == null) return;
		
		//Makes the GameUI invisible.
		ui.end();
		
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
	
	/**
	 * Disconnects from a server and then reconnects to it.
	 * @return If the connection was successfully reset.
	 */
	public boolean resetConnection() {
		//Checks that the client is connected to a server.
		if(!isConnected()) return false;
		
		//Gets the ip address.
		String ip = socket.getInetAddress().getHostAddress();
		
		//Disconnects from the server.
		disconnect();
	
		//Reconnects.
		return connect(ip, port) == 0;
	}
}









