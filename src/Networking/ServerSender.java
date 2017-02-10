package Networking;

import java.io.*;
import GameLogic.Board;

public class ServerSender extends Thread {

	private ObjectOutputStream serverOutput;
	private Board board;
	private boolean running;
	
	public ServerSender(ObjectOutputStream serverOutput, Board board) {
		this.serverOutput = serverOutput;
		this.board = board;
	}
	
	public void run() {
		running = true;
		
		try {
			while(running) {
				System.out.println("This is only a test3");
				serverOutput.writeObject((board.getUpdate()));
				sleep(40);
			}
			
			serverOutput.close();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void close() {
		running = false;
		try {
			serverOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
