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
	private static final double gravity = 0.5;
	private int mySquareID; // Square ID
	private int myColour;
	private Point2D.Double myPos;
	private Board board;
	private int myPlayer; // AI Player ID
	private String myName;
	private double outAngle;
	private double outVelocity;
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
		try {
			Thread.sleep(100);
			ArrayList<PhysObject> squares = board.getSquares();
			for (PhysObject player:squares) {
				if (((Square) player).getPlayerID() == myPlayer) {
					if (player.getInUse()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						changeAIPos();
						
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						aiMove();
						
						try {
							Thread.sleep(150);
						} catch (InterruptedException e) {
							//e.printStackTrace();
						}
						aiAttack();
					}
				}
			}
		} catch (InterruptedException e) {
			//AIManager interrupted.
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
// 		determineResult();
		alterResult();
		double velocity_chosen = getVelocity();
		double angle_chosen = getAngle();
		sendAttack(angle_chosen, velocity_chosen);  // by sending power, angle chosen to methods in other class.
		
		return ;
	}
	
	public void aiMoveCal(double targetX, double targetY) {
		boolean skip = false;
//		System.out.println("move to: " + targetX + " " + targetY);
		ArrayList<PhysObject> blocks = board.getBlocks();
		double xPos = getAIPos().getX();
		double yPos = getAIPos().getY() - 30.0;
//		moveUp();
		int i = 0;
		while (((int)xPos >= targetX + 30.0) || ((int)xPos <= targetX) || !(yPos <= targetY && yPos >= targetY -30.0 )) {
 			System.out.println("xpos" + xPos);
 			System.out.println("ypos" + yPos);
// 			System.out.println("targety" + targetY);
// 			System.out.println("targetx" + targetX);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			

			if (skip) {
				skip = false;
				break;
			}
			
			ArrayList<PhysObject> squares = board.getSquares();
			for (PhysObject player:squares) {
				if (((Square) player).getPlayerID() == myPlayer) {
					if (!((Square)player).getAlive()) {
						return;
					}
				}
			}
			
// 			if (board.getTime() >= 15 * 1000) {
// 				return;
// 			}
			
			changeAIPos();
			xPos = getAIPos().getX();
			yPos = getAIPos().getY() - 30.0;
			
			boolean jumpLeft = false;
			boolean jumpRight = false;
			boolean dontJumpDown = false;
			boolean jumpLeftObs = false;
			boolean jumpRightObs = false;
			
			if (xPos <= targetX) {
				//System.out.println("move to: " + targetX + " " + targetY);
				//System.out.println(xPos + " " + yPos);
				System.out.println("go right");
				
				if (yPos > targetY) {
					for (PhysObject block:blocks) {
						double blockX = block.getPos().getX();
						double blockY = block.getPos().getY();
						if(blockX == targetX && blockY == targetY) {
							System.out.println("upper block!!!!!!!!");
							if (xPos < targetX + 60.0 && xPos > targetX - 20.0) {
								targetX = xPos;
								targetY = yPos;
								//System.out.println("destination change to " + targetX + " " + targetY);
								skip = true;
								break;
							}
							
						}
					}
					for (int k = 0; k < 15; k++) {
						moveRight();
						i++;
						//System.out.println(myName + "go down right! " + i);
					}
					continue;
				}
				
				//detect edge
				jumpLeft = determineNBR(xPos, yPos);
				jumpRight = determineNBL(xPos, yPos);
				dontJumpDown = determineNBD(xPos, yPos);
				jumpLeftObs = determineObsL(xPos, yPos);
				jumpRightObs = determineObsR(xPos, yPos);
				
				System.out.println("jl, jr, djd " + jumpLeft + jumpRight + dontJumpDown);
				if (jumpLeft && !jumpRight && !dontJumpDown) {
					for (int j = 0; j < 10; j++) {
						moveUpLeft();
						moveLeft();
					}
					System.out.println(myName + "Detected Edge. Jump Left");

//					System.out.println("blocks underneath");
					jumpLeft = false;
					dontJumpDown = false;
				}
				else if (jumpRight && !jumpLeft && !dontJumpDown) {
					for (int j = 0; j < 10; j++) {
						moveUpRight();
						moveRight();
					}
					System.out.println(myName + "Detected Edge. Jump Right");
					jumpRight = false;
					dontJumpDown = false;

//					System.out.println("blocks underneath");
				}
				else if (!jumpLeft && !jumpRight) {
					jumpRight = false;
					jumpLeft = false;
					dontJumpDown = false;
					System.out.println("continue movement");
				}
				else if (jumpLeft && jumpRight) {
					targetX = xPos;
					targetY = yPos;
					System.out.println("destination change to " + targetX + " " + targetY);
					jumpRight = false;
					jumpLeft = false;
					dontJumpDown = false;
					break;
				}
				else {
					targetX = xPos;
					targetY = yPos;
					jumpRight = false;
					jumpLeft = false;
					dontJumpDown = false;
					break;
				}
				
				if (jumpLeftObs && !jumpRightObs) {
					moveUpLeft();
					moveLeft();
					jumpLeftObs = false;
					jumpRightObs = false;
				}
				else if (jumpRightObs && !jumpLeftObs) {
					moveUpRight();
					moveRight();
					jumpLeftObs = false;
					jumpRight = false;
				}
				else {
					jumpLeftObs = false;
					jumpRightObs = false;
				}
				
				if (yPos < targetY) {
					System.out.println("y < targetY");
					for (PhysObject block:blocks) {
						double blockX = block.getPos().getX();
						double blockY = block.getPos().getY();
						if(blockX == targetX && blockY == targetY) {
							if (xPos < targetX + 60.0 && xPos > targetX - 20.0) {
								System.out.println("under block!!!!!!!!");
								targetX = xPos;
								targetY = yPos;
								System.out.println("destination change to " + targetX + " " + targetY);
								skip = true;
								break;
							}
						}
						if (((blockY <= targetY) && (blockX < targetX)) && (yPos >= blockY - 101.0) && (xPos >= blockX - 40.0) && (xPos < blockX - 25.0)) {
							moveRight();
							moveUpRight();
							System.out.println(myName + "Jump Right");
							for (int k = 0; k < 9; k++) {
								moveRight();
								i++;
								System.out.println(myName + "go right! " + i);
							}
							break;
						}
					}
				}
				
				moveRight();
				System.out.println(myName + "move Right");
				i++;
				System.out.println("Right " + i);
			}
			else {
				//System.out.println("move to: " + targetX + " " + targetY);
				//System.out.println(xPos + " " + yPos);
				System.out.println("go left");
				
				if (yPos > targetY) {
					for (PhysObject block:blocks) {
						double blockX = block.getPos().getX();
						double blockY = block.getPos().getY();
						if(blockX == targetX && blockY == targetY) {
							System.out.println("upper block!!!!!!!!");
							if (xPos < targetX + 60.0 && xPos > targetX - 20.0) {
								targetX = xPos;
								targetY = yPos;
								//System.out.println("destination change to " + targetX + " " + targetY);
								skip = true;
								break;
							}
							
						}
					}
					for (int k = 0; k < 15; k++) {
						moveLeft();
						i++;
						//System.out.println(myName + "go down left! " + i);
					}
					continue;
				}
				
				// detect edge
				jumpLeft = determineNBR(xPos, yPos);
				jumpRight = determineNBL(xPos, yPos);
				dontJumpDown = determineNBD(xPos, yPos);
				jumpLeftObs = determineObsL(xPos, yPos);
				jumpRightObs = determineObsR(xPos, yPos);

				System.out.println("jl, jr, djd " + jumpLeft + jumpRight + dontJumpDown);
				if (jumpLeft && !jumpRight && !dontJumpDown) {
					for (int j = 0; j < 10; j++) {
						moveUpLeft();
						moveLeft();
					}
//					System.out.println(myName + "Detected Edge. Jump Left");

//					System.out.println("blocks underneath");
					jumpLeft = false;
					dontJumpDown = false;
				}
				else if (jumpRight && !jumpLeft && !dontJumpDown) {
					for (int j = 0; j < 10; j++) {
						moveUpRight();
						moveRight();
					}
//					System.out.println(myName + "Detected Edge. Jump Right");
					jumpRight = false;
					dontJumpDown = false;

//					System.out.println("blocks underneath");
				}
				else if (!jumpLeft && !jumpRight) {
					jumpRight = false;
					jumpLeft = false;
					dontJumpDown = false;
				}
				else if (jumpLeft && jumpRight) {
					targetX = xPos;
					targetY = yPos;
//					System.out.println("destination change to " + targetX + " " + targetY);
					jumpRight = false;
					jumpLeft = false;
					dontJumpDown = false;
					break;
				}
				else {
					targetX = xPos;
					targetY = yPos;
					jumpRight = false;
					jumpLeft = false;
					dontJumpDown = false;
					break;
				}
				
				
				
				if (jumpLeftObs) {
					moveUpLeft();
					moveLeft();
					jumpLeftObs = false;
					jumpRightObs = false;
				}
				else if (jumpRightObs) {
					moveUpRight();
					moveRight();
					jumpLeftObs = false;
					jumpRight = false;
				}
				else {
					jumpLeftObs = false;
					jumpRightObs = false;
				}
				
				if (yPos < targetY) {
					System.out.println("y < targetY");
					for (PhysObject block:blocks) {
						double blockX = block.getPos().getX();
						double blockY = block.getPos().getY();
						if(blockX == targetX && blockY == targetY) {
							if (xPos < targetX + 60.0 && xPos > targetX - 20.0) {
								System.out.println("under block!!!!!!!!");
								targetX = xPos;
								targetY = yPos;
								System.out.println("destination change to " + targetX + " " + targetY);
								skip = true;
								break;
							}
							
						}
						if (((blockY <= targetY) && (blockX > targetX)) && (yPos >= blockY - 101.0) && (xPos <= blockX + 80.0) && (xPos > blockX + 65.0)) {
							moveLeft();
							moveUpLeft();
							System.out.println(myName + "Jump Left");
							for (int k = 0; k < 9; k++) {
								moveLeft();
								i++;
								System.out.println(myName + "go left! " + i);
							}
							break;
						}
					}
				}
				
				moveLeft();
				System.out.println(myName + "move Left");
				i++;
				System.out.println("Left " + i);
			}
		}
		return ;
	}
	
	public boolean determineNBR(double xPos, double yPos) {
		boolean jumpLeft = false;
		ArrayList<PhysObject> blocks = board.getBlocks();
		for (PhysObject block:blocks) {
			if (!block.getInUse()) {
				jumpLeft = true;
				continue;
			}
			double blockX = block.getPos().getX();
			double blockY = block.getPos().getY();
			if ((xPos >= blockX) && (xPos < blockX + 120.0) && (yPos >= blockY) && (yPos <= blockY + 35)) {
				jumpLeft = false;
				return jumpLeft;
			}
			jumpLeft = true;
		}
		return jumpLeft;
	}
	
	public boolean determineNBL(double xPos, double yPos) {
		System.out.println("xPos in NBL" + xPos);
		System.out.println("yPos in NBL" + yPos);
		boolean jumpRight = false;
		ArrayList<PhysObject> blocks = board.getBlocks();
		for (PhysObject block:blocks) {
			if (!block.getInUse()) {
				jumpRight = true;
				continue;
			}
			double blockX = block.getPos().getX();
			double blockY = block.getPos().getY();
			if (((xPos <= blockX)) && (xPos >= blockX - 80.0) && (yPos >= blockY) && (yPos <= blockY + 35)) {
				jumpRight = false;
				return jumpRight;
			}
			jumpRight = true;
		}
		return jumpRight;
	}
	
	public boolean determineNBD(double xPos, double yPos) {
		boolean dontJumpDown = true;
		ArrayList<PhysObject> blocks = board.getBlocks();
		for (PhysObject block:blocks) {
			if (!block.getInUse()) {
				dontJumpDown = true;
				continue;
			}
			double blockX = block.getPos().getX();
			double blockY = block.getPos().getY();
			if (((xPos >= blockX - 40.0) && (xPos <= blockX + 80.0)) && (yPos > blockY)) {
//				System.out.println("block is underneath");
				dontJumpDown = false;
				return dontJumpDown;
			}
			dontJumpDown = true;
//			System.out.println("no blocks underneath");
		}
		return dontJumpDown;
	}
	
	public boolean determineObsL(double xPos, double yPos) {
		boolean jumpLeftObs = false;
		ArrayList<PhysObject> blocks = board.getBlocks();
		for (PhysObject block:blocks) {
			if (!block.getInUse()) {
				continue;
			}
			double blockX = block.getPos().getX();
			double blockY = block.getPos().getY();
			if ((xPos - 80 <= blockX) && (xPos - 40 >= blockX) && (yPos + 30.0 == blockY)) {
				jumpLeftObs = true;
				return jumpLeftObs;
			}
			jumpLeftObs = false;
		}
		return jumpLeftObs;
	}
	
	public boolean determineObsR(double xPos, double yPos) {
		boolean jumpRightObs = false;
		ArrayList<PhysObject> blocks = board.getBlocks();
		for (PhysObject block:blocks) {
			if (!block.getInUse()) {
				continue;
			}
			double blockX = block.getPos().getX();
			double blockY = block.getPos().getY();
			if ((xPos + 110 >= blockX) && (xPos + 70 <= blockX) && (yPos + 30.0 == blockY)) {
				jumpRightObs = true;
				return jumpRightObs;
			}
			jumpRightObs = false;
		}
		return jumpRightObs;
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
	////System.out.println("myself: " + getAIPos());
		Point2D.Double target = getFinalDestination();
		double xdis = target.getX() - getAIPos().getX();
		double ydis = getAIPos().getY() - target.getY(); // no need to absolute
		double acc_angle = 45.0;
		double acc_velocity = maxVelocity/2;
		if (ydis < 0) {
			// angle larger than 45 degrees
			boolean hit = false;
			acc_angle = 90;
			int state = calculation(acc_angle, acc_velocity, target);
			hit = isHit(state);
			while (!hit) {
//				if (state == 3) {
//					if (target.getX() > getAIPos().getX()) {
//						aiMoveCal(target.getX() + 40.0, target.getY());
//					}
//					else {
//						aiMoveCal(target.getX() -, target.getY());
//					}
//				}
				acc_velocity = maxVelocity/2;
				if (acc_angle >= 90 || acc_angle <= 0) {
					acc_angle = 0;
				}
				if (state == 1) { // too close
					// angle increase by 3 degrees (?)
					acc_angle += 2.75;
					while (acc_velocity <= maxVelocity && !hit) {
						// increase power
						acc_velocity += 4.5;
						state = calculation(acc_angle, acc_velocity, target);
						hit = isHit(state);
						//System.out.println("acc_a: " + acc_angle + "acc_v: " + acc_velocity);
					}
				}
				else if (state == 2) { // too far
					// angle increase by 3 degrees (?)
					acc_angle += 2.75;
					while (acc_velocity > 0 && !hit) {
						// decrease power
						acc_velocity -= 4.5;
						state = calculation(acc_angle, acc_velocity, target);
						hit = isHit(state);
						//System.out.println("acc_a: " + acc_angle + "acc_v: " + acc_velocity);
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
//				if (state == 3) {
//					if (target.getX() > getAIPos().getX()) {
//						aiMoveCal(target.getX() - 160.0, target.getY());
//					}
//					else {
//						aiMoveCal(target.getX() + 160.0, target.getY());
//					}
//				}
				acc_velocity = maxVelocity/2;
				if (acc_angle <= 0 || acc_angle >= 90) {
					acc_angle = 90;
				}
				if (state == 1) { // too close
					// angle increase by 3 degrees (?)
					acc_angle -= 2.75;
					while (acc_velocity <= maxVelocity && !hit) {
						// increase power
						acc_velocity += 4.5;
						state = calculation(acc_angle, acc_velocity, target);
						hit = isHit(state);
						//System.out.println("acc_a: " + acc_angle + "acc_v: " + acc_velocity);
					}
				}
				else if (state == 2) { // too far
					// angle increase by 3 degrees (?)
					acc_angle -=2.75;
					while (acc_velocity > 0 && !hit) {
						// decrease power
						acc_velocity -= 4.5;
						state = calculation(acc_angle, acc_velocity, target);
						hit = isHit(state);
						//System.out.println("acc_a: " + acc_angle + "acc_v: " + acc_velocity);
					}
				}
			}

			//System.out.println("acc_a: " + acc_angle + "acc_v: " + acc_velocity);
			
			if(xdis < 0) {
				acc_velocity *= -1;
			}

			setAngle(acc_angle);
			setVelocity(acc_velocity);
			//System.out.println("acc_a: " + acc_angle + "acc_v: " + acc_velocity);
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
		double aiX = getAIPos().getX();
//		double originalX = aiX;
		double aiY = getAIPos().getY();
//		double originalY = aiY;
		double range = Math.abs(aiX - target.getX());
		//System.out.println("range: " + range);
		double height = target.getY() - aiY; // no need to absolute
		//System.out.println("height: " + height);
		////System.out.println("a in cal: " + a + "v in cal: " + v);
		double angle = Math.toRadians(a);
		double velocity = v;
//		double xVel = Math.abs(velocity * Math.cos(angle)) * 30;
//		double yVel = Math.abs(velocity * Math.sin(angle)) * 30;
		double r = 0;
//		double h = 0;
		double g = 1;
		double vix = velocity * Math.cos(angle);
//		double timeOfFlight = range / xVel;
		
		
		double yDis = 0;
		double xDis = 0;
		
//		g = (range / 100) * ((Math.abs(height) + 100) / 100);
		

		if (height > 90) { // X
			g = 1.7;
		}
		else if (range < 40) {
			g = 0.5;
		}
		else if (range <= 80 && height <= 110) {
			g = 1;
		}
		else if (range > 80 && range <= 120 && height <= 110) {
			g = 0.9;
		}
		else if (range > 120 && range <= 200 && height <= 110) {
			g = ((range - 100) / 40) / 1.5;
		}
		else if (range > 200 && range <= 280 && height <= 110) {
			g = ((range - 100) / 40) / 1;
		}
		else if (range > 280 && range <= 360 && height <= 110) {
			g = ((range - 200) / 40) / 1.8;
		}
		else if (range > 360) {
			g = 2.123;
		}

		
//		//System.out.println("time of flight: " + timeOfFlight);
//		for (int t = 1; t <= timeOfFlight; t++) {
//			yDis = yVel - gravity;
//			xDis = xVel;
//			aiX += xDis;
//			aiY = originalY + yDis;
//		}
		
//		r = Math.abs(aiX - originalX);
//		h = aiY - originalY;
		// R = v * cos(angle) * ((v * sin(angle)) + sqrt((v * v * sin(angle) * sin(angle)) + 2 * g * ydis))
//		r = (velocity * Math.cos(angle) * ((velocity * Math.sin(angle)) + Math.sqrt((v * v * Math.sin(angle) * Math.sin(angle)) + 2 * gravity * ydis))) / 2;
		// R = (v * cos(angle) / g) * ((v * sin(angle)) + Math.sqrt((v * v * sin(angle) * sin(angle)) + (2 * g * ydis)))
		r = (velocity * Math.cos(angle) / (gravity * g)) * ((velocity * Math.sin(angle)) + Math.sqrt((velocity * velocity * Math.sin(angle) * Math.sin(angle)) + (2 * (gravity * g) * height)));
		////System.out.println(r);
		
		if (r > (range - 15) && r < (range + 15)) {
			return 0;
		}
		else if (r < range) {
			return 1;
		}
		else {
			return 2;
		}
		
		//return 0 if accurate
		//return 1 if target not reach
		//return 2 if over target
		//return 3 move
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
		double aiY = aiPos.getY() - 30.0;
		
		double angle = Math.toRadians(getAngle());
		double velocity = Math.abs(getVelocity());
		double range = Math.abs(target.getX() - aiPos.getX());
		double height = aiY - target.getY();
		
		//calculate time
		// x = velocity * cos(angle) * t
		double vix = velocity * Math.cos(angle);
		double timeOfFlight = range / vix;
		
		//calculate x coordinate
		// y = velocity * sin(angle) * t - 0.5 * g * t^2
		double yDis = 0;
		double xDis = 0;
		
		double g = 1;
		
		if (height > 90) { // X
			g = 1.7;
		}
		else if (range < 40) {
			g = 0.5;
		}
		else if (range <= 80 && height <= 110) {
			g = 1;
		}
		else if (range > 80 && range <= 120 && height <= 110) {
			g = 0.9;
		}
		else if (range > 120 && range <= 200 && height <= 110) {
			g = ((range - 100) / 40) / 1.5;
		}
		else if (range > 200 && range <= 280 && height <= 110) {
			g = ((range - 100) / 40) / 1;
		}
		else if (range > 280 && range <= 360 && height <= 110) {
			g = ((range - 200) / 40) / 1.8;
		}
		else if (range > 360) {
			g = 2.123;
		}
		
		ArrayList<PhysObject> blocks = board.getBlocks();
		for (int t = 1; t < timeOfFlight; t ++) {
			yDis = (velocity * Math.sin(angle)) - (gravity * g);
			xDis = velocity * Math.cos(angle);
//			if (there are obstacles) { // method determining obstacles need to be made
//				return true;
//			}
			for(PhysObject block:blocks) {
				double blockX = block.getPos().getX();
				double blockY = block.getPos().getY();
				if ((aiX < (blockX + 42)) && (aiX > (blockX - 2)) && (aiY < (blockY + 30)) && (aiY > (blockY - 5))) {
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
			Thread.sleep(70);
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
			Thread.sleep(70);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 * Send jump command
	 */
	private void moveUp() {
		board.input("Pressed  W " + myName);
		try {
			Thread.sleep(70);
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
			Thread.sleep(70);
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
			Thread.sleep(70);
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
//		double xVel = (velocity * Math.cos(Math.toRadians(angle)) / 800) * 30;
//		double yVel = Math.abs((velocity * Math.sin(Math.toRadians(angle)) / 450) * 30);
		double xVel = velocity * Math.cos(Math.toRadians(angle));
		double yVel = Math.abs(velocity * Math.sin(Math.toRadians(angle)));
		//System.out.println("xV: " + xVel + ", yV: " + yVel);
		String command = xVel  + ", " + yVel + ", " + myName + ", AItakesashotx86";

		board.input(command);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean dontKillMyself(double aix, double aiy, double tx, double ty) {
		if ((aiy == ty) && (aix >= tx - 60.0) && (aix <= tx + 100.0)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determine a target to attack and calculate the position of the target
	 * Choosing target by the shortest displacement (by pythagoras theorem)
	 * @return the position of the chosen target
	 */
	public abstract Point2D.Double getFinalDestination();
	
}
