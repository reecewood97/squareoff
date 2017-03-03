package ai;

import GameLogic.Board;

/**
 * @author JeffLeung
 *
 */
public class DifficultAI extends AI {

	private final int mistakeAngle = 2;
	private final int mistakeVelocity = 3;

	public DifficultAI(int aiPlayer, int aiSquareID, int aiColour, Board board) {
		super(aiPlayer, aiSquareID, aiColour, board);
		setMistake(mistakeAngle, mistakeVelocity);
	}
	
}
