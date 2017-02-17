package Networking;

import java.net.*;
import java.util.ArrayList;

import GameLogic.Board;
import Graphics.Screen;
import Graphics.SplashSplash;
import Graphics.Updater;

import java.io.*;

/**
 * The class client class that can connect to a specified server.
 * @author djs568
 *
 */
public class Client {
	
	private String name;
	private boolean isHost;
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private ClientSender sender;
	private ClientReceiver receiver;
	private Board board;
	private Queue q;
	
	/**
	 * Constructor.
	 * @param name The client's nickname.
	 * @param isHost Whether the client is the host.
	 */
	public Client(String name) {
		this.name = name;
		socket = null;
		toServer = null;
		fromServer = null;
		q = new Queue();
		board = new Board();
	}
	
	/**
	 * Connects the client to a specified server.
	 * @param ip The IP address of the server to be connected to.
	 * @param port The port connected to of the server.
	 * @throws UnknownHostException If the host could not be found.
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
		
		
		SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();

		Screen newui = new Screen(board, q);
		
		//Creates and starts the  client-side threads to communicate with the server.
		sender = new ClientSender(toServer, q, name);
		receiver = new ClientReceiver(fromServer, board, newui);
		
		sender.start();
		receiver.start();
		
		return true;
	}
	
	/**
	 * Disconnects the client from a server if it is connected.
	 */
	public void disconnect() {
		//Do nothing if the client is not connected.
		if(socket == null || socket.isClosed()) return;
		
		//Close the socket and stop the threads.
		try {
			socket.close();
			sender.close();
			receiver.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public ArrayList<String> getPlayers() {
		return receiver.getPlayers();
	}
	
	public void play() {
		sender.play();
	}
	
	public void quit() {
		sender.quit();
	}
}
