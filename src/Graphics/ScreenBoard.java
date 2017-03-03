package Graphics;

import javax.swing.JPanel;
import GameLogic.Board;
import GameLogic.Explosion;
import GameLogic.PhysObject;
import GameLogic.Square;
import GameLogic.TerrainBlock;
import GameLogic.Weapon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

@SuppressWarnings("serial") 
public class ScreenBoard extends JPanel{
	
	private Board board;
	private double heightratio;
	private double widthratio;
	private boolean showTargetLine;
	private HangerOn hangeron;
	
	public ScreenBoard(Board board, double heightratio, double widthratio, HangerOn h){
		super();
		this.board = board;
		this.heightratio = heightratio;
		this.widthratio = widthratio;
		this.showTargetLine = false;
		this.hangeron = h;
		
		
	}
	
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
		
		paintBlocks(board.getBlocks(), g2d);
		paintSquares(board.getSquares(),g2d);
		paintWeapons(board.getWeapons(),g2d);
		paintExplosions(board.getExplosion(),g2d);
		paintTargetLine(board.getWeapons(),board.getTargetLine(),g2d);
	} 		
	
	
	
	public void paintBlocks(ArrayList<PhysObject> blocks, Graphics2D g2d){
		
		for(PhysObject block : blocks){
			
			int x = (int) block.getPos().getX();
			int y = (int) block.getPos().getY();
			
			y = 450 - y; 
			
			int newx = (int) (x*widthratio);
			int newy = (int) (y*heightratio);
			
			int blockwidth = (int) (40*widthratio);
			int blockheight = (int) (30*heightratio);
			int blocktype = ((TerrainBlock) block).getType();
			int blockhealth = ((TerrainBlock) block).getHealth(); 
			boolean visible = ((TerrainBlock) block).isVisible(); 
			
			//check if visible
			if(visible){
			
				g2d.setColor(Color.black);
				g2d.fillRect(newx,newy,blockwidth,blockheight);
				
				//if 1 then colour is brown
				if (blocktype == 1){
					g2d.setColor(new Color(139,69,19));
				}
				//else grey
				else{
					g2d.setColor(new Color(105,105,105));
				}
				
				//g2d.setColor(Color.DARK_GRAY);
				//g2d.fillRect(newx+2, newy-2, blockwidth, blockheight);
			
				//g2d.setColor(Color.GRAY);
				g2d.fillRect(newx+1,newy+1,blockwidth-2,blockheight-2);
				
				if(blockhealth == 1){
					
					g2d.setColor(Color.cyan);
					g2d.fillRect(newx + 2, newy + 3, blockwidth/10, blockheight/10);
					g2d.fillRect(newx + 10, newy + 3, blockwidth/10, blockheight/10);
					g2d.fillRect(newx + 2, newy + 20, blockwidth/10, blockheight/10);
					g2d.fillRect(newx + 10, newy + 20, blockwidth/10, blockheight/10);
				}

				
			}
		}
	}
	
	public void paintSquares(ArrayList<PhysObject> squares, Graphics2D g2d){
		
		for(PhysObject square : squares){
			
			if(((Square) square).getAlive()){
				int x = (int) square.getPos().getX();
				int y = (int) square.getPos().getY();
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
				else{
					
					eye1backstartx = (int) ((x)*widthratio);
					eye2backstartx = (int) ((x+16)*widthratio);
	
					eye1frontstartx = (int) ((x+2)*widthratio);
					eye2frontstartx = (int) ((x+18)*widthratio);
					
				}
				
				int eye1backstarty = (int) ((y + 10)*heightratio);
				int eye2backstarty = eye1backstarty;
				int eye1frontstarty = (int) ((y + 12)*heightratio);
				int eye2frontstarty = eye1frontstarty;
				int eyewidth = (int) (9*widthratio);
				int eyeheight = (int) (9*heightratio);
				int iriswidth = (int) (6*widthratio);
				int irisheight = (int) (6*heightratio);
				
				//g2d.fillOval(100, eye2backstarty, width, height);
				
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
	
	public void paintTargetLine(ArrayList<PhysObject> weapons, boolean b, Graphics2D g2d){
	
		if(b){
			Point mousepos = MouseInfo.getPointerInfo().getLocation();
		
			g2d.drawLine((int) weapons.get(0).getPos().getX(), 
					(int) weapons.get(0).getPos().getY(),
					(int) mousepos.getX(),
					(int) ((mousepos.getY())-(10*widthratio)));
		
		}
		
		
	}
	
	public void paintWeapons(ArrayList<PhysObject> weapons, Graphics2D g2d){
		
		
			for(PhysObject weapon : weapons){
				
				if (((Weapon)weapon).getInUse()){
					
					int x = (int) weapon.getPos().getX();
					int y = (int) weapon.getPos().getY();
					
	
					x = (int) (x*widthratio);
					y = (int) (y*heightratio);
					y = 450 - y;
					
					int weaponwidth = (int) (10*widthratio);
					int weaponheight = (int) (10*heightratio);
					g2d.setColor(Color.BLACK);
					g2d.fillOval(x,y,weaponwidth,weaponheight);
				
				}
			}
		
		
	}
	
	public void paintExplosions(ArrayList<PhysObject> explosion, Graphics2D g2d){
		
		for(PhysObject exp : explosion){
			if (((Explosion) exp).getInUse()){
				
				int x = (int) exp.getPos().getX();
				int y = (int) exp.getPos().getY();
				

				x = (int) (x*widthratio);
				y = (int) (y*heightratio);
				y = 450 - y;
				
				int size = ((Explosion) exp).getSize();
				
				int expwidth = (int) (size*widthratio);
				int expheight = (int) (size*heightratio);
				
				g2d.setColor(Color.ORANGE);
				g2d.fillOval(x,y,expwidth,expheight);
				
				if(size == 30){
					
					hangeron.setExp("1");
					hangeron.setExpUse("false");
					
				}
				else{
					
					size++;
					hangeron.setExp(size + "");
					
				}
			
			}
		}
		
		
	}
	
	
}
	