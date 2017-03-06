package Networking;

import java.util.ArrayList;

import GameLogic.Board;
import ai.AI;

public class GameLoop extends Thread {
	
	private Board board;
	private ArrayList<AI> ais;
	private boolean running;
	
	public GameLoop(Board board, ArrayList<AI> ais) {
		this.board = board;
		this.ais = ais;
	}
	
	public void run() {
		running = true;
		while(running) {
			board.input("None");
			try {
				sleep(35); //Cranking up to a dank FPS
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
