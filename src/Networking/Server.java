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
				
				BufferedReader clientInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
				new ServerReceiver(clientInput, board).start();
				
				PrintStream serverOutput = new PrintStream(s.getOutputStream());
				new ServerSender(serverOutput, board).start();
			} 
		}
		catch (IOException e) {
			e.printStackTrace();
			//TODO
		}
	}
	
	public void close() throws IOException {
		running = false;
		socket.close();
	}
}
