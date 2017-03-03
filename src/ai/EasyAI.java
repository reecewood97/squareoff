package ai;

import GameLogic.Board;

/**
 * @author JeffLeung
 *
 */
public class EasyAI extends AI{
	
	private final double mistakeAngle = 7;
	private final double mistakeVelocity = 10;

	public EasyAI(int aiPlayer, int aiSquareID, int aiColour, Board board) {
		super(aiPlayer, aiSquareID, aiColour, board);
		setMistake(mistakeAngle, mistakeVelocity);
	}
	
}
