package Networking;

import java.net.*;
import java.util.ArrayList;
import GameLogic.Board;
import Graphics.Screen;

import java.io.*;

/**
 * The class client class that can connect to a specified server.
 * Creates the client-side version of the game and creates threads that 
 * communicate with the server. Additionally creates the Game's user interface.
 * @author djs568
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
		
		//Creates a socket connecting to the server and then creates methods to communicate with the server.
		try {
			socket = new Socket(ip, port);
		}
		catch(IOException e) {
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
		
		//Creates and starts the  client-side threads to communicate with the server.
		q.clear();
		sender = new ClientSender(toServer, q);
		receiver = new ClientReceiver(fromServer, board, ui, this);
		
		sender.setName(name + ": client sender");
		receiver.setName(name + ": client receiver");
		
		sender.start();
		receiver.start();
		
		sender.send(name);

		return isConnected();
	}
	
	/**
	 * Disconnects the client from a server if it is connected.
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
		q.offer("I close the client sender.");
	}
	
	public ArrayList<String> getPlayers() {
		if(!isConnected()) return null;
		sender.send(Server.PLAYERLIST);
		return receiver.getPlayers();
	}
	
	public boolean inGame() {
		return receiver.inGame();
	}
	
	public boolean isConnected() {
		return socket != null && !socket.isClosed() && receiver.waitForAccept();
	}
}
