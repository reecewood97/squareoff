package ai;

import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;
import GameLogic.Square;

public class AITest {

	public static void main(String[] args) {
		Board board = new Board("map1");
		
		DifficultAI ai = new DifficultAI(2, 0, 0, board);
		
		ai.changeAIPos();
		
		//ai.getFinalDestination();
		System.out.println(ai.getAIPos() + " \n");
//		System.out.println(ai.getFinalDestination());
		
//		ai.determineState();
		System.out.println(ai.getAIPos());
		
		ai.determineResult();
		
		double velocity_chosen = ai.getVelocity();
		double angle_chosen = ai.getAngle();
		System.out.println(velocity_chosen + " " + angle_chosen);
		
		ai.alterResult();
		
		velocity_chosen = ai.getVelocity();
		angle_chosen = ai.getAngle();
		System.out.println(velocity_chosen + " " + angle_chosen);
	}

}
