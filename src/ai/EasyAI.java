package ai;

import GameLogic.Board;

/**
 * @author JeffLeung
 *
 */
public class EasyAI extends AI{
	
	private final int mistakeAngle = 7;
	private final int mistakeVelocity = 10;

	public EasyAI(int aiPlayer, int aiSquareID, int aiColour, Board board) {
		super(aiPlayer, aiSquareID, aiColour, board);
	}
	
	/**
	 * Determines whether to move and attack or to pick up items
	 * Should be called by the server to send movements and attacks
	 */
	public void determineState() {
		changeAIPos();
		if(haveItems()) {
			// go get items
			// Then go attack
			aiMove();
			aiAttack();
			
			// More advance: locate item position, calculate time to reach item
			// 				 choose to get item and attack or attack directly
		}
		else {
			aiMove();
			aiAttack();
		}
//		for (int i = 0; i < 100; i++) {
//			moveRight();
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	/**
	 * Send out the final velocity and angle chosen for attack
	 */
	public void aiAttack() {
		// choose the closest target at this moment (get it from server copy)
		//Point2D.Double finalCoor = getFinalDestination();
		determineResult();
		double velocity_chosen = getVelocity();
		double angle_chosen = getAngle();
		sendAttack(angle_chosen, velocity_chosen);
		
		// attack the provided coordinate
		// 		by sending power, angle chosen to methods in other class.
		
		
		// More advance: choose enemy standing on a block that has less hp,
		// 				 then calculate will the grenade able to reach target (through physics engine)
		//				 if it can, attack, else, choose another target
	}
	
	public void determineResult() {
		super.determineResult(mistakeAngle, mistakeVelocity);
	}
	
	

}
