package Networking;

import java.io.*;
import java.net.*;

import GameLogic.Board;

public class Server extends Thread {

	private Board board;
	private boolean running;
	private ServerSocket socket;
	
	public Server(int port) {
		board = new Board();
		
		socket = null;
		try {
			socket = new ServerSocket(port);
		}
		catch(IOException e) {
			e.printStackTrace();
			//TODO
		}
	}
	
	public void run() {
		running = true;
		try {
			while(running) {
				Socket s = socket.accept();
				
				ObjectInputStream fromClient = new ObjectInputStream(s.getInputStream());
				new ServerReceiver(fromClient, board).start();
				
				ObjectOutputStream toClient = new ObjectOutputStream(s.getOutputStream());
				new ServerSender(toClient, board).start();
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
