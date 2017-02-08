package Networking;

import java.io.BufferedReader;
import java.io.IOException;

import GameLogic.Board;

public class ServerReceiver extends Thread {
	
	private BufferedReader clientInput;
	private Board board;
	private boolean running;
	
	public ServerReceiver(BufferedReader clientInput, Board board) {
		this.clientInput = clientInput;
		this.board = board;
	}
	
	public void run() {
		running = true;
		
		try {
			String input;
			while(running && (input = clientInput.readLine()) != null) {		
				board.input(input); //This will also return a string that should be stuck into a queue.
			}
			clientInput.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
			//TODO
		}
	}
	
	public void close() {
		running = false;
	}
}
