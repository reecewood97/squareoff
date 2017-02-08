package Networking;

import java.io.*;
import GameLogic.Board;

public class ServerSender extends Thread {

	private PrintStream serverOutput;
	private Board board;
	private boolean running;
	
	public ServerSender(PrintStream serverOutput, Board board) {
		this.serverOutput = serverOutput;
		this.board = board;
	}
	
	public void run() {
		running = true;
		
		try {
			while(running) {
				System.out.println(board.getUpdate());
				serverOutput.println(board.getUpdate()); //I actually need to pull down from the queue and give the results of the moves.
				//board.input("");
				sleep(40);
			}
			
			serverOutput.close();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			//TODO
		}
	}
	
	public void close() {
		running = false;
	}
}
