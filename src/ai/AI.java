package ai;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

<<<<<<< HEAD
import gameLogic.Board;
import gameLogic.PhysObject;
import gameLogic.Square;
=======
import GameLogic.Board;
import GameLogic.PhysObject;
import GameLogic.Square;
import GameLogic.TerrainBlock;
>>>>>>> ad87e017eaa9288847a307119dbb434aa6e55a06

// Coordinates size: 800 x 450

public class AI {
	
	private static final double maxVelocity = 20;
	private static final double gravity = 9.81;
	private int myID; // Square ID
	private int myColour;
	private Point2D.Double myPos;
	private Board board;
	private int myPlayer; // AI Player ID
	private double outAngle;
	private double outVelocity;
	
	public AI(int aiID, int aiColour, int aiPlayer, Board board, Point2D.Double startPos) {
		setID(aiID);
		setColour(aiColour);
		setPlayer(aiPlayer);
		this.board = board;
		setPos(startPos); // start position
	}
	
	private void setPlayer(int player) {
		this.myPlayer = player;
	}

	private void setPos(Point2D.Double pos) {
		this.myPos = pos;
	}

	private void setColour(int colour) {
		this.myColour = colour;
	}

	public void setID(int id) {
		this.myID = id;
	}

	private void determineState() {
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
	
	private void aiAttack() {
		// choose the closest target at this moment (get it from server copy)
		Point2D.Double finalCoor = getFinalDestination();
		determineResult();
		double velocity_chosen = getVelocity();
		double angle_chosen = getAngle();
		// attack the provided coordinate
		// 		by sending power, angle chosen to methods in other class.
		
		
		// More advance: choose enemy standing on a block that has less hp,
		// 				 then calculate will the grenade able to reach target (through physics engine)
		//				 if it can, attack, else, choose another target
	}
	
	private void aiMove() {
		// go to the best position to attack target
		// checks the best position through physics engine (get coordinates)
		// move to the provided coordinate
		
		ArrayList<PhysObject> blocks = board.getBlocks();
		double myX = getAIPos().getX();
		double myY = getAIPos().getY();
		double targetX = myX;
		double targetY = myY - 30.0;
		TerrainBlock currentBlock = (TerrainBlock) blocks.get(0);
		for (PhysObject block:blocks) {
			if ((block.getPos().getY() == myY - 30.0) && (block.getPos().getX() <= myX - 25.0) && (block.getPos().getX() > myX)) {
				currentBlock = (TerrainBlock) block;
			}
		}
		int currentBlockHealth = currentBlock.getHealth();
		double distance = 99999999999.0;
		// Stage 1:
		// Only move if the blocks that it's standing on has low hp.
		// If the coordinate's block has low hp (e.g. cannot survive two hits), 
		// go to the blocks next to it which has higher hp.
		
		//if ( current block has low hp) {
		//	find closest blocks that has a higher hp
		//	while ( approaching target block) {
				//if ( end of platform ) {
				//		moveUP();
				//		moveRight() / moveLeft() at the same time with moveUP();
				//	}
				//	else {
				//		keep moving
				//	}
		//	}
		
		//}
		
		if (currentBlockHealth <= 2) {
			int largerHealth = currentBlockHealth;
			double xPos = currentBlock.getPos().getX();
			double yPos = currentBlock.getPos().getY();;
			for (PhysObject block:blocks) {
				TerrainBlock searchBlock = (TerrainBlock) block;
				if (searchBlock.getHealth() >= largerHealth) {
					double sBlockX = searchBlock.getPos().getX();
					double sBlockY = searchBlock.getPos().getY();
					double xDis = xPos - sBlockX;
					double yDis = yPos - sBlockY;
					// calculate shortest displacement by pythagoras theorem
					double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
					if (displacement < distance) {
						distance = displacement;
						targetX = sBlockX;
						targetY = sBlockY;
					}
				}
			}
			while ((xPos < targetX) || (xPos > targetX + 25.0) || yPos != targetY) {
				if (xPos > targetX) {
					Point2D nextblock = new Point2D.Double(xPos + 3.0, yPos);
					if (!blocks.contains(nextblock)) {
						moveUp();
						moveRight();
					}
					moveRight();
				}
				else {
					Point2D nextblock = new Point2D.Double(xPos - 3.0, yPos);
					if (!blocks.contains(nextblock)) {
						moveUp();
						moveRight();
					}
					moveLeft();
				}
				xPos = getAIPos().getX();
				yPos = getAIPos().getY() - 30.0;
			}
		}
		
		// Stage 2:
		// If the angle of shooting >90 or <0 but still cannnot find a shooting path, move to elsewhere
		// Usually places that has higher hp (defense), or, there is a clear shooting path (ai and enemy on the same level(y axis))
		
		// Stage 3:
		// Decide whether go to defense (places that could be targeted by less enemy and/or blocks with higher hp) 
		// or attack when there is a clear target (a must hit enemy situation).

	}
	
	// Determine the accurate result to hit the chosen enemy
	// Currently assuming there are no obstacles(blocks) in the path of the grenade
	// Next step: calculate and record the coordinates that the grenade is at for every second 
	// 			  (Recording the path of coordinates of the grenade). 
	//			  Then, iterate through the path(list of coordinates) to check is there any blocks in those coordinates
	//			  If there is at least one coordinate that is a block, calculate another path.
	public void determineResult(){
		Point2D.Double target = getFinalDestination();
		// double xdis = Math.abs(getAIPos().getX() - target.getX());
		double ydis = getAIPos().getY() - target.getY(); // no need to absolute
		double acc_angle = 45.0;
		double acc_velocity = maxVelocity/2;
		if (ydis < 0) {
			// angle larger than 45 degrees
			boolean hit = false;
			int state = calculation(acc_angle, acc_velocity);
			hit = isHit(state);
			while (!hit) {
				if (state == 1) { // too close
					// angle increase by 3 degrees (?)
					acc_angle += 3.0;
					while (acc_velocity <= maxVelocity && !hit) {
						// increase power
						acc_velocity += 10.0;
						state = calculation(acc_angle, acc_velocity);
						hit = isHit(state);
					}
				}
				else if (state == 2) { // too far
					// angle increase by 3 degrees (?)
					acc_angle += 3.0;
					while (acc_velocity > 0 && !hit) {
						// decrease power
						acc_velocity -= 10.0;
						state = calculation(acc_angle, acc_velocity);
						hit = isHit(state);
					}
				}
			}
			
			// output angle and power (force)
			setAngle(acc_angle);
			setVelocity(acc_velocity);
		}
		else {
			//try with 45 degrees and decrease it.
			boolean hit = false;
			int state = calculation(acc_angle, acc_velocity);
			hit = isHit(state);
			while (!hit) {
				if (state == 1) { // too close
					// angle increase by 3 degrees (?)
					acc_angle -= 3.0;
					while (acc_velocity <= maxVelocity && !hit) {
						// increase power
						acc_velocity += 10.0;
						state = calculation(acc_angle, acc_velocity);
						hit = isHit(state);
					}
				}
				else if (state == 2) { // too far
					// angle increase by 3 degrees (?)
					acc_angle -= 3.0;
					while (acc_velocity > 0 && !hit) {
						// decrease power
						acc_velocity -= 10.0;
						state = calculation(acc_angle, acc_velocity);
						hit = isHit(state);
					}
				}
			}
			
			// output angle and power (force)
			setAngle(acc_angle);
			setVelocity(acc_velocity);
		}
	}
	
	public int calculation(double a, double v) {
		Point2D.Double target = getFinalDestination();
		double xdis = Math.abs(getAIPos().getX() - target.getX());
		double ydis = getAIPos().getY() - target.getY(); // no need to absolute
		double angle = a;
		double velocity = v;
		double r = 0;
		// R = v * cos(angle) * ((v * sin(angle)) + sqrt((v * v * sin(angle) * sin(angle)) + 2 * g * ydis))
		r = (velocity * Math.cos(angle) * ((velocity * Math.sin(angle)) + Math.sqrt((v * v * Math.sin(angle) * Math.sin(angle)) + 2 * gravity * ydis))) / 2;
		
		if (r > (xdis - 5) && r < (xdis + 5)) {
			return 0;
		}
		else if (r < xdis) {
			return 1;
		}
		else {
			return 2;
		}
		
		//return 0 if accurate
		//return 1 if target not reach
		//return 2 if over target
	}
	
	public boolean isHit(int cal) {
		if (cal == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void moveLeft() {
		
	}
	
	public void moveRight() {
		
	}
	
	public void moveUp() {
		
	}
	
	public void setAngle(double angle_chosen) {
		this.outAngle = angle_chosen;
	}
	
	public void setVelocity(double velocity_chosen) {
		this.outVelocity = velocity_chosen;
	}
	
	public double getAngle() {
		return this.outAngle;
	}
	
	public double getVelocity() {
		return this.outVelocity;
	}
	
	
	public void changeAIPos() {
		ArrayList<PhysObject> squares = board.getSquares();
		int numOfPlayers = squares.size();
		for (int i = 0; i < numOfPlayers; i++) {
			if (((Square) squares.get(i)).getPlayerID() == myID) {
				setPos(squares.get(i).getPos());
			}
		}
	}
	
	public Point2D.Double getAIPos() {
		return this.myPos;
	}
	
	public Point2D.Double getFinalDestination() {
		// Call functions that get enemy position from board
		ArrayList<PhysObject> squares = board.getSquares();
		int numOfPlayers = squares.size();
		int myX = (int) getAIPos().getX();
		int myY = (int) getAIPos().getY();
		int finalX = 0;
		int finalY = 0;
		PhysObject finalSquare = null;
		double finalDis = 0;
		for (int i = 0; i < numOfPlayers; i++) {
			PhysObject enemySquare = squares.get(i);
			if (((Square) enemySquare).getPlayerID() != myPlayer) {
				// get position of enemies
				int enemyX = (int) enemySquare.getPos().getX();
				int enemyY = (int) enemySquare.getPos().getY();
				double xDis = myX - enemyX;
				double yDis = myY - enemyY;
				
				// calculate shortest displacement by pythagoras theorem
				double displacement = Math.sqrt((yDis * yDis) + (xDis * xDis));
				if (displacement < finalDis) {
					finalDis = displacement;
					finalX = enemyX;
					finalY = enemyY;
					finalSquare = enemySquare;
				}
			}
		}
		
		// return the coordinates
		return finalSquare.getPos();
	}
	
}
