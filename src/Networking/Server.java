package Networking;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import GameLogic.Board;

public class Server extends Thread {

	private int port;
	private Board board;
	private boolean running;
	private ServerSocket socket;
	private ArrayList<String> players;
	
	public Server(int port) {
		this.port = port;
		board = new Board();
		players = new ArrayList<String>();
		socket = null;

	}
	
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
				Socket s = socket.accept();
				
				ObjectInputStream fromClient = new ObjectInputStream(s.getInputStream());
				new ServerReceiver(fromClient, board, players).start();
				
				ObjectOutputStream toClient = new ObjectOutputStream(s.getOutputStream());
				new ServerSender(toClient, board, players).start();
			} 
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void close() throws IOException {
		running = false;
		socket.close();
	}
}
