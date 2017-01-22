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
				serverOutput.println(board.getUpdate());
				board.input("");
				sleep(40);
			}
			
			serverOutput.close();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			//TODO
		}
	}
}
