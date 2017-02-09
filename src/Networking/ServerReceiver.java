package Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import GameLogic.Board;
import GameLogic.Move;

public class ServerReceiver extends Thread {
	
	private ObjectInputStream clientInput;
	private Board board;
	private boolean running;
	
	public ServerReceiver(ObjectInputStream clientInput, Board board) {
		this.clientInput = clientInput;
		this.board = board;
	}
	
	public void run() {
		running = true;
		
		try {
			Move input;
			while(running && (input = (Move)clientInput.readObject()) != null) {				
				board.input(input);
				}
			clientInput.close();	
		}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
			//TODO
		}
	}
	
	public void close() {
		running = false;
	}
}
