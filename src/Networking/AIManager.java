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
	}
	
	public void add(AI ai) {
		ais.add(ai);
	}
	
	public void run() {
		running = true;
		boolean aiTurn = false;
		while(running) {
			int playerTurn = ((Square)board.getActivePlayer()).getPlayerID();
			while(!aiTurn) {
				for(AI ai: ais) {
					if(ai.getPlayerID() == playerTurn) {
						aiTurn = true;
						ai.determineState();
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
			if(playerTurn == ((Square)board.getActivePlayer()).getPlayerID()) {
				board.incrementTurn();
			}
		}
		
	}
}
