package Networking;

import java.util.ArrayList;
import GameLogic.Board;
import GameLogic.Square;
import ai.AI;

/**
 * Contains an arraylist of all the AIs in a game and manages when they take their turns.
 * @author David
 *
 */
public class AIManager extends Thread {
	
	private ArrayList<AI> ais; //ArrayList of AIs to be managed.
	private boolean running; //Whether the thread is currently running.
	private Board board; //The server-side board.
	
	/**
	 * Creates a new AIManager with no AIs.
	 * @param board The server-side board.
	 */
	public AIManager(Board board) {
		ais = new ArrayList<AI>();
		running = false;
		this.board = board;
	}
	
	/**
	 * Adds an AI to the AIManager.
	 * @param ai The AI being added.
	 */
	public void add(AI ai) {
		ais.add(ai);
	}
	
	/**
	 * Run method of the Thread.
	 */
	public void run() {
		running = true;
		int playerTurn;
		while(running) {
			playerTurn = ((Square)board.getActivePlayer()).getPlayerID();
			//Iterates through the AIs and checks if it's one of their turns.
			for(AI ai: ais) {
				if(ai.getPlayerID() == playerTurn) {
					//Activates the AI whose turn it is, then ends the AI's turn.
					ai.determineState();
					board.incrementTurn();
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
	
	/**
	 * Kills the thread.
	 */
	public void close() {
		running = false;
	}
}
