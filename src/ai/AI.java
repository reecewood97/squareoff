package ai;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Random;

import GameLogic.Board;
import GameLogic.Move;
import GameLogic.PhysObject;
import GameLogic.Square;
import GameLogic.TerrainBlock;
import Networking.Queue;

// Coordinates size: 800 x 450

/**
 * The class of an AI player. It does all the calculation that an AI need currently.
 * It sends the moves played to the server through the AIClientSender class.
 * @author JeffLeung
 *
 */
public abstract class AI {
	
	private static final double maxVelocity = 100;
	private static final double gravity = 9.81;
	private int mySquareID; // Square ID
	private int myColour;
	private Point2D.Double myPos;
	private Board board;
	private int myPlayer; // AI Player ID
	private String myName;
	private double outAngle;
	private double outVelocity;
	private Queue q;
	private double mistakeAngle = 0;
	private double mistakeVelocity = 0;
	private boolean haveObstacles = false;
	private boolean timesUp = false;	
	
	
	/**
	 * Constructor that set up AI player
	 * @param aiID Square ID
	 * @param aiColour colour for this AI player
	 * @param aiPlayer player ID of this Square
	 * @param board Board of the current game
	 */
	public AI(int aiPlayer, int aiSquareID, int aiColour, Board board, String name) {
		setSquareID(aiSquareID);
		setColour(aiColour);
		setPlayer(aiPlayer);
		setBoard(board);
		setAIName(name);
	}
	
	
	
	/**
	 * Set board
	 * Update the board for calculation
	 * @param updatedBoard board
	 */
	public void setBoard(Board updatedBoard) {
		this.board = updatedBoard;
	}
	
	/**
	 * Set Player ID
	 * @param player player ID
	 */
	public void setPlayer(int player) {
		this.myPlayer = player;
	}

	/**
	 * Set Position of Square
	 * @param pos position of Square
	 */
	public void setPos(Point2D.Double pos) {
		this.myPos = pos;
	}

	/**
	 * Set colour of Player
	 * @param colour
	 */
	public void setColour(int colour) {
		this.myColour = colour;
	}

	/**
	 * Set Square ID
	 * @param id square ID
	 */
	public void setSquareID(int id) {
		this.mySquareID = id;
	}
	
	/**
	 * Get the SquareID of the AI Player
	 * @return SquareID
	 */
	public int getSquareID() {
		return this.mySquareID;
	}
	
	/**
	 * Get the PlayerID of the AI Player
	 * @return PlayerID
	 */
	public int getPlayerID() {
		return this.myPlayer;
	}
	
	/**
	 * Get the Colour of AI
	 * @return colour
	 */
	public int getColour() {
		return this.myColour;
	}
	
	
	
	
	/**
	 * Set the angle chosen to attack
	 * @param angle_chosen angle chosen to attack
	 */
	public void setAngle(double angle_chosen) {
		this.outAngle = angle_chosen;
	}
	
	/**
	 * Set the velocity chosen to attack
	 * @param velocity_chosen velocity chosen to attack
	 */
	public void setVelocity(double velocity_chosen) {
		this.outVelocity = velocity_chosen;
	}
	
	/**
	 * Get the angle chosen to attack
	 * @return angle chosen to attack
	 */
	public double getAngle() {
		return this.outAngle;
	}
	
	/**
	 * Get the velocity chosen to attack
	 * @return velocity chosen to attack
	 */
	public double getVelocity() {
		return this.outVelocity;
	}
	
	
	
	/**
	 * Get will there be obstacle
	 * @return true if there are obstacles, false if shooting path is clear
	 */
	public boolean thereAreObstacles() {
		return this.haveObstacles;
	}
	
	/**
	 * Set will there be obstacle
	 * @param thereAreObstacles true if there are obstacles, false if shooting path is clear
	 */
	public void setObstacles(boolean thereAreObstacles) {
		this.haveObstacles = thereAreObstacles;
	}
	
	
	/**
	 * Change the position of the Square
	 */
	public void changeAIPos() {
		ArrayList<PhysObject> squares = board.getSquares();
		int numOfPlayers = squares.size();
		for (int i = 0; i < numOfPlayers; i++) {
			if (((Square) squares.get(i)).getPlayerID() == myPlayer) {
				setPos(squares.get(i).getPos());
			}
		}
	}
	
	/**
	 * Get the position of the Square
	 * @return the position of the Square
	 */
	public Point2D.Double getAIPos() {
		return this.myPos;
	}
	
	/**
	 * Set name of AI
	 * @param name Name of AI
	 */
	public void setAIName(String name) {
		this.myName = name;
	}
	
	/**Get name of AI
	 * @return Name of AI
	 */
	public String getAIName() {
		return this.myName;
	}
	
	
	
	/**
	 * Check are there any random items on the board
	 * @return true if there are, false if there are no items
	 */
	public boolean haveItems() {
//		if (there are items){
//			return true;
//		}
//		else {
//			return false;
//		}
		return false;
	}
	
	public void setEndTurn(boolean isTimesUp) {
		this.timesUp = isTimesUp;
	}
	
	public boolean getEndTurn() {
		return this.timesUp;
	}
	
	/**
	 * Determines whether to move and attack or to pick up items
	 * Should be called by the server to send movements and attacks
	 */
	public void determineState() {
		ArrayList<PhysObject> squares = board.getSquares();
		for (PhysObject player:squares) {
			if (((Square) player).getPlayerID() == myPlayer) {
				if (player.getInUse()) {
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					changeAIPos();
					
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					aiMove();
					
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					aiAttack();
				}
			}
		}
		
		return;
	}
	
	/**
	 * Send out the final velocity and angle chosen for attack
	 */
	public void aiAttack() {
		if (board.getTime() >= 20 * 1000) {
			return;
		}
		alterResult();
		double velocity_chosen = getVelocity();
		double angle_chosen = getAngle();
		sendAttack(angle_chosen, velocity_chosen);  // by sending power, angle chosen to methods in other class.
		
		return ;
	}
	
	public void aiMoveCal(double targetX, double targetY) {
		System.out.println(targetX + " " + targetY);
		ArrayList<PhysObject> blocks = board.getBlocks();
		double xPos = getAIPos().getX();
		double yPos = getAIPos().getY() - 30.0;
		moveUpLeft();
		moveUpRight();
		int i = 0;
		boolean jumpLeft = false;
		boolean jumpRight = false;
		while ((xPos < targetX - 23.0) || (xPos > targetX + 23.0) || yPos != targetY) {
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ArrayList<PhysObject> squares = board.getSquares();
			for (PhysObject player:squares) {
				if (((Square) player).getPlayerID() == myPlayer) {
					if (!((Square)player).getAlive()) {
						return;
					}
				}
			}
			
			if (board.getTime() >= 15 * 1000) {
				return;
			}
			
			changeAIPos();
			xPos = getAIPos().getX();
			yPos = getAIPos().getY() - 30.0;
			
			if (xPos < targetX) {
				System.out.println(xPos);
				
				if (yPos > targetY) {
					moveRight();
					continue;
				}
				
				//detect edge
				for (PhysObject block:blocks) {
					double blockX = block.getPos().getX();
					double blockY = block.getPos().getY();
					if (((xPos + 50.1 >= blockX - 25.0) && (xPos + 50.1 <= blockX + 25.0)) && yPos == blockY) {
						jumpLeft = false;
						break;
					}
					jumpLeft = true;
				}
				for (PhysObject block:blocks) {
					double blockX = block.getPos().getX();
					double blockY = block.getPos().getY();
					if (((xPos - 50.1 >= blockX - 25.0) && (xPos - 50.1 <= blockX + 25.0)) && yPos == blockY) {
						jumpRight = false;
						break;
					}
					jumpRight = true;
				}
				
				if (jumpLeft && !jumpRight) {
					for (int j = 0; j < 10; j++) {
						moveUpLeft();
						moveLeft();
					}
					System.out.println(myName + "Detected Edge. Jump Left");
					jumpLeft = false;
				}
				else if (jumpRight && !jumpLeft) {
					for (int j = 0; j < 10; j++) {
						moveUpRight();
						moveRight();
					}
					System.out.println(myName + "Detected Edge. Jump Right");
					jumpRight = false;
				}
				else {
					jumpRight = false;
					jumpLeft = false;
				}
				
				if (yPos < targetY) {
					for (PhysObject block:blocks) {
						double blockX = block.getPos().getX();
						double blockY = block.getPos().getY();
						if (((blockY < targetY) && (blockX < targetX)) && (yPos + 100.0 >= blockY) && (xPos >= blockX - 50.0) && (xPos <= blockX -24.9)) {
							moveUpRight();
							System.out.println(myName + "Jump Right");
							break;
						}
					}
				}
				
				moveRight();
				System.out.println(myName + "move Right");

//				Point2D.Double newPos = new Point2D.Double(xPos + 2, targetY + 30);
//				setPos(newPos);
//				xPos += 2;
				i++;
				System.out.println("Right " + i);
			}
			else {
				System.out.println(xPos);
				
				if (yPos > targetY) {
					moveLeft();
					continue;
				}
				
				// detect edge
				for (PhysObject block:blocks) {
					double blockX = block.getPos().getX();
					double blockY = block.getPos().getY();
					if (((xPos + 50.1 >= blockX - 25.0) && (xPos + 50.1 <= blockX + 25.0)) && yPos == blockY) {
						jumpLeft = false;
						break;
					}
					jumpLeft = true;
				}
				for (PhysObject block:blocks) {
					double blockX = block.getPos().getX();
					double blockY = block.getPos().getY();
					if (((xPos - 50.1 >= blockX - 25.0) && (xPos - 50.1 <= blockX + 25.0)) && yPos == blockY) {
						jumpRight = false;
						break;
					}
					jumpRight = true;
				}
				
				if (jumpLeft && !jumpRight) {
					for (int j = 0; j < 10; j++) {
						moveUpLeft();
						moveLeft();
					}
					System.out.println(myName + "Detected Edge. Jump Left");
					jumpLeft = false;
				}
				else if (jumpRight && !jumpLeft) {
					for (int j = 0; j < 10; j++) {
						moveUpRight();
						moveRight();
					}
					System.out.println(myName + "Detected Edge. Jump Right");
					jumpRight = false;
				}
				else {
					jumpRight = false;
					jumpLeft = false;
				}
				
				if (yPos < targetY) {
					for (PhysObject block:blocks) {
						double blockX = block.getPos().getX();
						double blockY = block.getPos().getY();
						if (((blockY < targetY) && (blockX < targetX)) && (yPos + 100.0 >= blockY) && (xPos >= blockX - 50.0) && (xPos <= blockX -24.9)) {
							moveUpLeft();
							System.out.println(myName + "Jump Left");
							break;
						}
					}
				}
				
				moveLeft();
				System.out.println(myName + "move Left");
//				xPos -= 2;

//				Point2D.Double newPos = new Point2D.Double(xPos - 2, targetY + 30);
//				setPos(newPos);
				i++;
				System.out.println("Left " + i);
			}
		}
		return ;
	}
	
	/**
	 * Movement of the Square of the AI player
	 */
	public abstract void aiMove();

	/**
	 * Determine the accurate result to hit the chosen enemy
	 * Currently assuming there are no obstacles(blocks) in the path of the grenade
	 * Next step: calculate and record the coordinates that the grenade is at for every second 
	 * (Recording the path of coordinates of the grenade).
	 * Then, iterate through the path(list of coordinates) to check is there any blocks in those coordinates
	 * If there is at least one coordinate that is a block, calculate another path.
	 * @param mistakeAngle 
	 * @param mistakeVelocity 
	 */
	public void determineResult(){
		Point2D.Double target = getFinalDestination();
		double xdis = target.getX() - getAIPos().getX();
		double ydis = getAIPos().getY() - target.getY(); // no need to absolute
		double acc_angle = 45.0;
		double acc_velocity = maxVelocity/2;
		if(!haveObstacles) {
			if (ydis < 0) {
				// angle larger than 45 degrees
				boolean hit = false;
				int state = calculation(acc_angle, acc_velocity, target);
				hit = isHit(state);
				while (!hit) {
					if (state == 1) { // too close
						// angle increase by 3 degrees (?)
						acc_angle += 3.0;
						while (acc_velocity <= maxVelocity && !hit) {
							// increase power
							acc_velocity += 7.5;
							state = calculation(acc_angle, acc_velocity, target);
							hit = isHit(state);
						}
					}
					else if (state == 2) { // too far
						// angle increase by 3 degrees (?)
						acc_angle += 3.0;
						while (acc_velocity > 0 && !hit) {
							// decrease power
							acc_velocity -= 7.5;
							state = calculation(acc_angle, acc_velocity, target);
							hit = isHit(state);
						}
					}
				}
			}
			else {
				//try with 45 degrees and decrease it.
				boolean hit = false;
				int state = calculation(acc_angle, acc_velocity, target);
				hit = isHit(state);
				while (!hit) {
					if (state == 1) { // too close
						// angle increase by 3 degrees (?)
						acc_angle -= 3.0;
						while (acc_velocity <= maxVelocity && !hit) {
							// increase power
							acc_velocity += 7.5;
							state = calculation(acc_angle, acc_velocity, target);
							hit = isHit(state);
						}
					}
					else if (state == 2) { // too far
						// angle increase by 3 degrees (?)
						acc_angle -= 3.0;
						while (acc_velocity > 0 && !hit) {
							// decrease power
							acc_velocity -= 7.5;
							state = calculation(acc_angle, acc_velocity, target);
							hit = isHit(state);
						}
					}
				}
			}
			
			boolean enemyOnRight = false;
			ArrayList<PhysObject> squares = board.getSquares();
			for (PhysObject square:squares) {
				if (getAIPos().getX() > square.getPos().getX()) {
					enemyOnRight = true;
					continue;
				}
				enemyOnRight = false;
			}
			
			if(xdis < 0 || enemyOnRight) {
				acc_angle = acc_angle * (-1);
				acc_velocity *= -1;
			}

			setAngle(acc_angle);
			setVelocity(acc_velocity);
			
			determineObstacle(target, getAIPos());
		}
		

		
	}
	
	public void setMistake(double mistakeAngle, double mistakeVelocity) {
		this.mistakeAngle = mistakeAngle;
		this.mistakeVelocity = mistakeVelocity;
	}
	
	public void alterResult() {
		determineResult();
		// output angle and power (force)
		
		double acc_angle = getAngle();
		double acc_velocity = getVelocity();
		double final_angle;
		double final_velocity;
		int mistakeAngle = (int) this.mistakeAngle;
		int mistakeVelocity = (int) this.mistakeVelocity;
		Random random = new Random();
		int posOrNeg = random.nextInt(2);
		if (posOrNeg == 0) {
			final_angle = acc_angle + random.nextInt(mistakeAngle) + (((double)random.nextInt(10)) / 10);
			final_velocity = acc_velocity + random.nextInt(mistakeVelocity) + (((double)random.nextInt(10)) / 10);
		}
		else {
			final_angle = acc_angle - random.nextInt(mistakeAngle);
			final_velocity = acc_velocity - random.nextInt(mistakeVelocity);
		}
		setAngle(final_angle);
		setVelocity(final_velocity);
	}
	
	/**
	 * Calculate whether the chosen angle and velocity would be accurate enough to hit chosen target (only suitable for projectile motion weapons)
	 * States would be return, return 0 if accurate, return 1 if target not reach, return 2 if over target
	 * @param a angle given
	 * @param v velocity given
	 * @return the state of such angle and velocity chosen
	 */
	private int calculation(double a, double v, Point2D.Double target) {
//		Point2D.Double target = getFinalDestination();
		double xdis = Math.abs(getAIPos().getX() - target.getX());
		double ydis = getAIPos().getY() - target.getY(); // no need to absolute
		double angle = a;
		double velocity = v;
		double r = 0;
		// R = v * cos(angle) * ((v * sin(angle)) + sqrt((v * v * sin(angle) * sin(angle)) + 2 * g * ydis))
		r = (velocity * Math.cos(angle) * ((velocity * Math.sin(angle)) + Math.sqrt((v * v * Math.sin(angle) * Math.sin(angle)) + 2 * gravity * ydis))) / 2;
		
		if (r > (xdis - 100) && r < (xdis + 100)) {
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
	
	/**
	 * Determine whether a target is hit with the state return by calculation
	 * @param cal state that calculation has returned, 0 if accurate, 1 if target not reach, 2 if over target
	 * @return true if hit, false, if too far or too close
	 */
	private boolean isHit(int cal) {
		if (cal == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void determineObstacle(Point2D.Double target, Point2D.Double aiPos) {
		double aiX = aiPos.getX();
		double aiY = aiPos.getY() + 30.0;
		
		double angle = getAngle();
		double velocity = getVelocity();
		double range = Math.abs(target.getX() - aiPos.getY());
		
		//calculate time
		// x = velocity * cos(angle) * t
		double vix = velocity * Math.cos(angle);
		double timeOfFlight = range / vix;
		
		//calculate x coordinate
		// y = velocity * sin(angle) * t - 0.5 * g * t^2
		double yDis = 0;
		double xDis = 0;
		
		
		ArrayList<PhysObject> blocks = board.getBlocks();
		for (int t = 1; t < timeOfFlight; t ++) {
			yDis = (velocity * Math.sin(angle) * t) - (0.5 * gravity * t * t);
			xDis = velocity * Math.cos(angle);
//			if (there are obstacles) { // method determining obstacles need to be made
//				return true;
//			}
			for(PhysObject block:blocks) {
				double blockX = block.getPos().getX();
				double blockY = block.getPos().getY();
				if ((aiX < (blockX + 50)) && (aiX > (blockX-50)) && (aiY < (blockY + 50)) && (aiY > (blockY-50))) {
					setObstacles(true);
					return ;
				}
				aiX += xDis;
				aiY += yDis;
			}
		}
		
		setObstacles(false);
	}
	
	/**
	 * Send move left command
	 */
	private void moveLeft() {
		board.input("Pressed A  " + myName);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send move right command
	 */
	private void moveRight() {
		board.input("Pressed D  " + myName);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send jump command
	 */
	private void moveUp() {
		board.input("Pressed  W " + myName);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send jump to the right command
	 * @return move
	 */
	private void moveUpRight() {
		board.input("Pressed DW " + myName);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send jump to the left command
	 * @return move
	 */
	private void moveUpLeft() {
		board.input("Pressed AW " + myName);
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send attack command by sending the angle and velocity to attack
	 * @param angle angle to attack
	 * @param velocity velocity to attack
	 */
	private void sendAttack(double angle, double velocity){
		double xVel = velocity * Math.cos(angle);
		double yVel = velocity * Math.cos(angle);
		
		String command = xVel  + ", " + yVel + ", " + myName + ", AItakesashotx86";

		board.input(command);
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * Determine a target to attack and calculate the position of the target
	 * Choosing target by the shortest displacement (by pythagoras theorem)
	 * @return the position of the chosen target
	 */
	public abstract Point2D.Double getFinalDestination();
	
}
