package ai;

import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.PhysObject;
import GameLogic.Square;

public class AITest {

	public static void main(String[] args) {
		Board board = new Board("map1");
		
		DifficultAI aid = new DifficultAI(2, 0, 0, board, "aid");
		EasyAI aie = new EasyAI(1, 0, 0, board, "aie");
		
		aie.changeAIPos();
		aid.changeAIPos();
		
		//ai.getFinalDestination();
		System.out.println("Easy " + aid.getAIPos() + " \n");
		System.out.println("Difficult " + aie.getAIPos() + " \n");
//		System.out.println(ai.getFinalDestination());
		
//		ai.determineState();
//		System.out.println(ai.getAIPos());
		
		aid.determineResult();
		
		double velocity_chosen_d = aid.getVelocity();
		double angle_chosen_d = aid.getAngle();
		System.out.println(velocity_chosen_d + " " + angle_chosen_d);
		
		aid.alterResult();
		
		velocity_chosen_d = aid.getVelocity();
		velocity_chosen_d = aid.getAngle();
		System.out.println(velocity_chosen_d + " " + angle_chosen_d);
		
		aie.determineResult();
		
		double velocity_chosen_e = aie.getVelocity();
		double angle_chosen_e = aie.getAngle();
		System.out.println(velocity_chosen_e + " " + angle_chosen_e);
		
		aie.alterResult();
		
		velocity_chosen_e = aie.getVelocity();
		angle_chosen_e = aie.getAngle();
		System.out.println(velocity_chosen_e + " " + angle_chosen_e);
	}

}
