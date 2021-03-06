package Graphics;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import GameLogic.Board;
import GameLogic.Explosion;
import GameLogic.PhysObject;
import GameLogic.Square;
import GameLogic.TerrainBlock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * screen board class draws the arena and paints all the phys objects to it
 * @author Fran
 *
 */
public class ScreenBoard extends JPanel{
	
	private Board board;
	private double heightratio;
	private double widthratio;
	private HangerOn hangeron;
	
	/**
	 * 
	 * @param board The current version of the board
	 * @param heightratio The ratio to fit the board height to the pplayer's screen 
	 * @param widthratio The ratio to fit the board width to the pplayer's screen 
	 * @param h HangerOn object
	 */
	public ScreenBoard(Board board, double heightratio, double widthratio, HangerOn h){
		
		super();
		this.board = board;
		this.heightratio = heightratio;
		this.widthratio = widthratio;
		this.hangeron = h;
		
	}
	
	/**
	 * paint method
	 */
	@Override
	public void paint(Graphics g) { 
		
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.blue);
		
		//draw sea
		int a = 0;
		int b = 350;
		int c = 800;
		int d = 100;
		
		a = (int) (a*widthratio);
		b = (int) (b*heightratio);
		c = (int) (c*widthratio);
		d = (int) (d*heightratio);
		
		g2d.fillRect(a,b,c,d);
		
		for(int i = 0; i < c; i+=40){
			g2d.fillOval(i, b-5, 40, 40);
		}
		
		try{
			g2d.setFont(new Font("Arial", Font.PLAIN, 20)); 
			g2d.setColor(Color.BLACK);
			g2d.drawString("Player " + (((Square) (board.getActiveBoard())).getPlayerID()) +
					"'s turn", (int) (80*widthratio), (int) (10*heightratio));
			g.drawString("Timer: " + board.getTime(), (int) (700*widthratio),
					(int) (10*heightratio));
			
			
			paintBlocks(board.getBlocks(), g2d);
			paintSquares(board.getSquares(),g2d);
			paintWeapons(board.getWeapons(),g2d); 
			paintExplosions(board.getExplosion(),g2d);
			paintTargetLine(board.getWeapons(),board.getTargetLine(),g2d);
			paintParticles(board.getParticles(),g2d);
		}
		catch(Exception e){}
	}
	
	/**
	 * paint the particles onto the panel
	 * @param particles The ArrayList of particles
	 * @param g2d Graphics 2D
	 */
	public void paintParticles(ArrayList<PhysObject> particles, Graphics2D g2d){
		
		for(PhysObject particle : particles) {
			
			int x = (int) particle.getPos().getX();
			int y = (int) particle.getPos().getY()+particle.getHeight();
			y = 450 - y;
			
			int newx = (int) (x*widthratio);
			int newy = (int) (y*heightratio);
			
			int width = (int) (particle.getWidth()*widthratio);
			int height = (int) (particle.getHeight()*heightratio);
			
			if(particle.getInUse()) {
				g2d.setColor(Color.black);
				g2d.fillRect(newx,newy,width,height);
			}
		}
	}
	
	
	/**
	 * paint the blocks onto the panel
	 * @param blocks The ArrayList of blocks
	 * @param g2d Graphics 2D
	 */
	public void paintBlocks(ArrayList<PhysObject> blocks, Graphics2D g2d){
		
		for(PhysObject block : blocks){
			
			int x = (int) block.getPos().getX();
			int y = (int) block.getPos().getY()+block.getHeight();
			y = 450 - y; 
			
			int newx = (int) (x*widthratio);
			int newy = (int) (y*heightratio);
			
			int blockwidth = (int) (40*widthratio);
			int blockheight = (int) (30*heightratio);
			int blocktype = ((TerrainBlock) block).getType();
			int blockhealth = ((TerrainBlock) block).getHealth(); 
			boolean visible = ((TerrainBlock) block).isVisible(); 
			
			//check if visible
			if(block.getInUse()){
				
				//shading
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect(newx+2, newy-2, blockwidth, blockheight);
				g2d.setColor(Color.black);
				g2d.fillRect(newx,newy,blockwidth,blockheight);
				
				//if blocktype is 1 then colour is brown
				if (blocktype == 1){
					g2d.setColor(new Color(139,69,19));
				}
				//else grey
				else{
					
					//dark grey for full health
					if(blockhealth == 2){
						g2d.setColor(new Color(105,105,105));
					}
					
					//light grey for half health
					else{
						g2d.setColor(new Color(169,169,169));
					}
					
				}
				

				g2d.fillRect(newx+1,newy+1,blockwidth-2,blockheight-2);

				
			}
		}
	}
	
	/**
	 * paint the squares onto the arena
	 * @param squares The ArrayList of squares
	 * @param g2d Graphics
	 */
	public void paintSquares(ArrayList<PhysObject> squares, Graphics2D g2d){
		
		for(PhysObject square : squares){
			
			if(((Square) square).getAlive()){
				int x = (int) square.getPos().getX();
				int y = (int) square.getPos().getY()+square.getHeight();//TODO
				y = 450 - y;
				
				int newx = (int) (x*widthratio);
				int newy = (int) (y*heightratio);
				
				int squarewidth = (int) (30*widthratio);
				int squareheight = (int) (30*heightratio);
				
				int playernum = ((Square) square).getPlayerID();
				if (playernum == 1){
					
					g2d.setColor(Color.RED);
				}
				else if(playernum == 2){
					g2d.setColor(Color.BLUE);
				}
				else if(playernum == 3){
					g2d.setColor(Color.YELLOW);
				}
				else{
					g2d.setColor(Color.GREEN);
				}
				
				g2d.fillRect(newx,newy,squarewidth,squareheight);
				
				g2d.setColor(Color.WHITE);
				
				int eye1backstartx = 0;
				int eye2backstartx = 0;
	
				int eye1frontstartx = 0;
				int eye2frontstartx = 0;
				
				if(((Square) square).getFacing().equals("Right")){
					eye1backstartx = (int) ((x + 8)*widthratio);
					eye2backstartx = (int) ((x+22)*widthratio);
					eye1frontstartx = (int) ((x+10)*widthratio);
					eye2frontstartx = (int) ((x+24)*widthratio);
				}
				else if(((Square) square).getFacing().equals("Left")){
					
					eye1backstartx = (int) ((x)*widthratio);
					eye2backstartx = (int) ((x+16)*widthratio);	
					eye1frontstartx = (int) ((x+2)*widthratio);
					eye2frontstartx = (int) ((x+18)*widthratio);
				}
				else {
					eye1backstartx = (int) ((x+4)*widthratio);
					eye2backstartx = (int) ((x+19)*widthratio);	
					eye1frontstartx = (int) ((x+6)*widthratio);
					eye2frontstartx = (int) ((x+21)*widthratio);
				}
				
				int eye1backstarty = (int) ((y + 10)*heightratio);
				int eye2backstarty = eye1backstarty;
				int eye1frontstarty = (int) ((y + 12)*heightratio);
				int eye2frontstarty = eye1frontstarty;
				int eyewidth = (int) (9*widthratio);
				int eyeheight = (int) (9*heightratio);
				int iriswidth = (int) (6*widthratio);
				int irisheight = (int) (6*heightratio);
				
				g2d.setColor(Color.black);
				g2d.fillOval(eye1backstartx-1, eye1backstarty-1, eyewidth+2, eyeheight+2);
				g2d.fillOval(eye2backstartx-1, eye2backstarty-1, eyewidth+2, eyeheight+2);
				g2d.setColor(Color.white);
				g2d.fillOval(eye1backstartx, eye1backstarty, eyewidth, eyeheight);
				g2d.fillOval(eye2backstartx, eye2backstarty, eyewidth, eyeheight);
				g2d.setColor(Color.black);
				g2d.fillOval(eye1frontstartx, eye1frontstarty, iriswidth, irisheight);
				g2d.fillOval(eye2frontstartx, eye2frontstarty, iriswidth, irisheight);
				
				
				
				
			}
		}
	}
	
	/**
	 * 
	 * @param weapons The ArrayList of weapons (1)
	 * @param targetLine The arraylist of targetline (1)
	 * @param g2d Graphics
	 */
	public void paintTargetLine(ArrayList<PhysObject> weapons,
			ArrayList<PhysObject> targetline, Graphics2D g2d){
	
		
		if(targetline.get(0).getInUse()){
			
			Square square = (Square) board.getActiveBoard();
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.BLACK);
			g2d.drawLine(((int) ((square.getPos().getX()+15) * widthratio)), 
					((int) ((450 - square.getPos().getY()-square.getHeight()+15)
							* heightratio)),
					((int) (this.getMousePosition().getX())),
					((int) (this.getMousePosition().getY())));
			
	
		}
		
		
	}
	
	/**
	 * paint weapon method
	 * @param weapons The ArrayList of weapons (1)
	 * @param g2d Graphics
	 */
	public void paintWeapons(ArrayList<PhysObject> weapons, Graphics2D g2d){
			
			for(PhysObject weapon : weapons){
				
				if (weapon.getInUse()){
					
					int x = (int) (((int) weapon.getPos().getX())*widthratio);
					
					int y = (int) ((450 - ((int) weapon.getPos().getY()+weapon.getHeight()))
							*heightratio);
					
					int weaponwidth = (int) (10*widthratio);
					int weaponheight = (int) (10*heightratio);
				
					if(weapon.getName().contains("ExplodeOnImpact")){
						g2d.setColor(Color.BLACK);
						g2d.fillOval(x,y,weaponwidth,weaponheight);
					}
					else if(weapon.getName().contains("TimedGrenade")){
					
						g2d.setColor(Color.DARK_GRAY);
						g2d.fillRect(x, y, weaponwidth, weaponheight);
					}
					else{
						
						g2d.setColor(Color.BLACK);
						g2d.fillRect(x, y, weaponwidth, weaponheight);
						
					}
					
				}
			}
		
		
	}
	
	/**
	 * paint explosions
	 * @param explosion The explosion object being drawn
	 * @param g2d Graphics
	 */
	public void paintExplosions(ArrayList<PhysObject> explosion, Graphics2D g2d){
		
		for(PhysObject exp : explosion){
		
			if (((Explosion) exp).getInUse()){
			
				double size = ((Explosion) exp).getSize();
				
				int x = (int) (((int) (exp.getPos().getX()))*widthratio);
				int y = (int) ((450 - ((int) (exp.getPos().getY()+exp.getHeight())))
						*heightratio);
				
				int sizeint = (int) size;	
				
				BufferedImage image;
				
				if(sizeint < 20){
					try {
					image = ImageIO.read(new File("Files/Images/exp1.png"));
					g2d.drawImage(image, (int) (x - 20*widthratio), 
							(int) (y - 20*heightratio), null);
				} 
				catch (IOException e) {
				
				}
				 
				}
				else if (sizeint >=20 && sizeint < 30){
					
					try {
						image = ImageIO.read(new File("Files/Images/exp1.5.png"));
						g2d.drawImage(image,(int) (x - 40*widthratio), 
								(int) (y - 40*heightratio), null);
					} 
					catch (IOException e) {
						
					}
					
					
				}
				else if (sizeint >=30 && sizeint < 40){
					
					try {
						image = ImageIO.read(new File("Files/Images/exp2.png"));
						g2d.drawImage(image,(int) (x - 40*widthratio), 
								(int) (y - 40*heightratio), null);
					} 
					catch (IOException e) {
						
					}
					
					
				}
				else if (sizeint >=40 && sizeint < 50){
					
					try {
						image = ImageIO.read(new File("Files/Images/exp2.5.png"));
						g2d.drawImage(image,(int) (x - 40*widthratio), 
								(int) (y - 40*heightratio), null);
					} 
					catch (IOException e) {
						
					}
					
					
				}
				else{
					
					try {
						image = ImageIO.read(new File("Files/Images/EXP3.png"));
						g2d.drawImage(image,(int) (x - 60*widthratio), 
								(int) (y - 60*heightratio), null);
					} 
					catch (IOException e) {
					
					}
					
				}
			}
		}
		
		
	}
	
	
}
	