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
	private int difficulty, maxNumberOfPlayers;
	private ArrayList<String> players;
	
	/**
	 * Creates a new AIManager with no AIs.
	 * @param board The server-side board.
	 */
	public AIManager(Board board, ArrayList<String> players, int maxNumberOfPlayers) {
		ais = new ArrayList<AI>();
		running = false;
		this.board = board;
		this.difficulty = Server.EASY_AI;
		this.players = players;
		this.maxNumberOfPlayers = maxNumberOfPlayers;
	}
	
	/**
	 * Adds AIs to the AIManager.
	 * @param n The number of AIs to be added.
	 */
	public void addAIs(int n) {
		int playerNumber;
		for(int i = 0; i < n && (playerNumber = players.size()) < maxNumberOfPlayers; i++) {
			playerNumber = players.size() + 1;
			AI ai;
			String name = "AI " + (ais.size() + 1);
			switch(difficulty) {
				case Server.NORMAL_AI: ai = new NormalAI(playerNumber, 0, playerNumber, board, name);
					break;
				case Server.HARD_AI: ai = new DifficultAI(playerNumber, 0, playerNumber, board, name);	
					break;
				default: ai = new EasyAI(playerNumber, 0, playerNumber, board, name);
			}
			ais.add(ai);
			players.add(name);
			board.addName(name);
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
	 * Sets the difficulty.
	 * @param difficulty The new difficulty.
	 */
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
