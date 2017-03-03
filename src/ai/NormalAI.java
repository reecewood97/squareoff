package ai;

import GameLogic.Board;

/**
 * @author JeffLeung
 *
 */
public class NormalAI extends AI {
	
	private final double mistakeAngle = 4;
	private final double mistakeVelocity = 6;
	
	public NormalAI(int aiPlayer, int aiSquareID, int aiColour, Board board) {
		super(aiPlayer, aiSquareID, aiColour, board);
		setMistake(mistakeAngle, mistakeVelocity);
	}

}
