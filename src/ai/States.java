package ai;

public class States {
	
	public States() {
		
	}
	
	public void determineState() {
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
	}
	
	public boolean haveItems() {
//		if (there are items){
//			return true;
//		}
//		else {
//			return false;
//		}
		return false;
	}
	
	public void aiAttack() {
		// choose the closest target at this moment (get it from server copy)
		// attack the provided coordinate
		// More advance: choose enemy standing on a block that has less hp,
		// 				 then calculate will the grenade able to reach target (through physics engine)
		//				 if it can, attack, else, choose another target
	}
	
	public void aiMove() {
		// go to the best position to attack target
		// checks the best position through physics engine (get coordinates)
		// move to the provided coordinate
		
		// If the coordinate's block has low hp (e.g. cannot survive two hits), 
		// go to the blocks next to it which has higher hp
	}
}
