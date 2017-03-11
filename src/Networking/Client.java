package Networking;

import java.net.*;
import java.util.ArrayList;
import GameLogic.Board;
import Graphics.Screen;

import java.io.*;

/**
 * The class client class that can connect to a specified server.
 * @author djs568
 *
 */
public class Client {
	
	private String name;
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private ClientSender sender;
	private ClientReceiver receiver;
	private Board board;
	private Queue q;
	private Screen ui;
	
	/**
	 * Constructor.
	 * @param name The client's nickname.
	 */
	public Client(String name) {
		this.name = name;
		socket = null;
		toServer = null;
		fromServer = null;
		q = new Queue();
		board = new Board("map1");
		ui = new Screen(board, q, name);
	}
	
	/**
	 * Connects the client to a specified server.
	 * @param ip 
	 * @param port
	 * @return
	 */
	public boolean connect(String ip, int port) {
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
		sender = new ClientSender(toServer, q);
		receiver = new ClientReceiver(fromServer, board, ui, this);
		
		sender.setName(name + ": client sender");
		receiver.setName(name + ": client receiver");
		
		sender.start();
		receiver.start();
		
		sender.send(name);
		
		return true;
	}
	
	/**
	 * Disconnects the client from a server if it is connected.
	 */
	public void disconnect() {
		
		//Makes the UI invisible. Doesn't turn off the music however.
		ui.setInvisible();
		
		//Do nothing if the client is not connected.
		if(!isConnected()) return;
		
		//Close the socket - this should join all the threads.
		try {
			socket.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		q.offer("I close the Client sender.");
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
		return socket != null && !socket.isClosed();
	}
}
