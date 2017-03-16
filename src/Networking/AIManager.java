package Networking;

import java.util.ArrayList;
import GameLogic.Board;
import GameLogic.Square;
import ai.AI;
import ai.DifficultAI;
import ai.EasyAI;
import ai.NormalAI;

/**
 * Contains an arraylist of all the AIs in a game and manages when they take their turns.
 * @author David
 *
 */
public class AIManager extends Thread {
	
	private ArrayList<AI> ais; 
	private boolean running; 
	private Board board; 
	private int difficulty;
	private ArrayList<String> players;
	
	/**
	 * Creates a new AIManager with no AIs.
	 * @param board The server-side board.
	 */
	public AIManager(Board board, ArrayList<String> players) {
		ais = new ArrayList<AI>();
		running = false;
		this.board = board;
		this.difficulty = Server.EASY_AI;
		this.players = players;
	}
	
	/**
	 * Adds AIs to the AIManager.
	 * @param n The number of AIs to be added.
	 */
	public void addAIs(int n) {
		for(int i = 1; i <= n; i++) {
			AI ai;
			switch(difficulty) {
				case Server.NORMAL_AI: ai = new NormalAI(i, 0, i, board);
					break;
				case Server.HARD_AI: ai = new DifficultAI(i, 0, i, board);	
					break;
				default: ai = new EasyAI(i, 0, i, board);
			}
			ais.add(ai);
			players.add("AI " + i);
		}
	}
	
	/**
	 * Thread run method.
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
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	/**
	 * Kills the thread.
	 */
	public void close() {
		running = false;
	}
}
