package Networking;

import java.net.*;
import GameLogic.Board;
import GameLogic.UserInput;
import Graphics.Screen;
import Graphics.SplashSplash;

import java.io.*;

/**
 * The class client class that can connect to a specified server.
 * @author djs568
 *
 */
public class Client {
	
	private Socket socket;
	private PrintStream toServer;
	private BufferedReader fromServer;
	private ClientSender sender;
	private ClientReceiver receiver;
	private Board board;
	private UserInput q;
	
	/**
	 * Constructor.
	 */
	public Client() {
		socket = null;
		toServer = null;
		fromServer = null;
		q = new UserInput();
		board = new Board();
	}
	
	/**
	 * Connects the client to a specified server.
	 * @param ip The IP address of the server to be connected to.
	 * @param port The port connected to of the server.
	 * @throws UnknownHostException If the host could not be found.
	 */
	public void connect(String ip, int port) throws UnknownHostException {
		//Creates a socket connecting to the server and then creates methods to communicate with the server.
		try {
			socket = new Socket(ip, port);
			toServer = new PrintStream(socket.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		SplashSplash splashscreen = new SplashSplash(1000);
		splashscreen.showSplash();
		@SuppressWarnings("unused")
		Screen newui = new Screen(board,q);
		
		//Creates and starts the  client-side threads to communicate with the server.
		sender = new ClientSender(toServer,q);
		receiver = new ClientReceiver(fromServer, board, newui);
			
		sender.start();
		receiver.start();
	}
	
	/**
	 * Disconnects the client from a server if it is connected.
	 */
	public void disconnect() {
		//Do nothing if the client is not connected.
		if(socket == null ||socket.isClosed()) return;
		
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
}
