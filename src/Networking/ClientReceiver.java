package Networking;

import java.io.BufferedReader;
import java.io.IOException;

import GameLogic.Board;

public class ClientReceiver extends Thread {
	
	private BufferedReader server;
	private Board board;
	private boolean running;
	
	public ClientReceiver(BufferedReader server) {
		this.server = server;
		board = new Board();
	}
	
	public void run() {
		running = true;
		
		try {
			while(running) {
				board.update(server.readLine());
			}
			
			server.close();
		}
		catch(IOException e) {
			e.printStackTrace();
			//TODO
		}
	}

	public void close() throws IOException {
		running = false;
	}
}
