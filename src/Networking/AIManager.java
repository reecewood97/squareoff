package Networking;

import java.util.ArrayList;
import GameLogic.Board;
import GameLogic.Square;
import ai.AI;

public class AIManager extends Thread {
	
	private ArrayList<AI> ais;
	private boolean running;
	private Board board;
	
	public AIManager(Board board) {
		ais = new ArrayList<AI>();
		running = false;
		this.board = board;
	}
	
	public void add(AI ai) {
		ais.add(ai);
	}
	
	public void run() {
		running = true;
		int playerTurn;
		while(running) {
			playerTurn = ((Square)board.getActivePlayer()).getPlayerID();
			for(AI ai: ais) {
				if(ai.getPlayerID() == playerTurn) {
					ai.determineState();
					board.incrementTurn();
					break;
				}
			}
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
	
		}
	}
}
