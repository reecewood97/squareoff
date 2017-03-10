package Networking;

import java.util.ArrayList;

import GameLogic.*;
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
			if(!aiRunning()) {
				for(AI ai: ais) {
					if(ai.getPlayerID() == ((Square)board.getActivePlayer()).getPlayerID()) {
						ai.start();
						break;
					}
				}
			}
			
			try {
				sleep(35);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	private boolean aiRunning() {
		boolean b = false;
		for(AI ai: ais) b |= ai.getAITurn();
		return b;
	}
}
