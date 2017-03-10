package ai;

import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;
import GameLogic.Square;

public class AITest {

	public static void main(String[] args) {
		Board board = new Board("map1");
		
		DifficultAI aid = new DifficultAI(2, 0, 0, board);
		EasyAI aie = new EasyAI(1, 0, 0, board);
		
		
		aid.changeAIPos();
		
		//ai.getFinalDestination();
		System.out.println(aid.getAIPos() + " \n");
//		System.out.println(ai.getFinalDestination());
		
//		ai.determineState();
//		System.out.println(ai.getAIPos());
		
		aid.determineResult();
		
		double velocity_chosen = aid.getVelocity();
		double angle_chosen = aid.getAngle();
		System.out.println(velocity_chosen + " " + angle_chosen);
		
		aid.alterResult();
		
		velocity_chosen = aid.getVelocity();
		angle_chosen = aid.getAngle();
		System.out.println(velocity_chosen + " " + angle_chosen);
	}

}
