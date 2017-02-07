package Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import GameLogic.Board;
import GameLogic.PhysObject;
import GameLogic.Square;
import GameLogic.TerrainBlock;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


@SuppressWarnings("serial")
public class ScreenBoard extends JPanel{

	Board board; 
	
	public ScreenBoard(Board board){
		
		super();
		this.board = board;
	
	}
	
	@Override
    public void paint(Graphics g) {
        
    	super.paint(g);
        
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
       
        g2d.setColor(Color.blue);
        g2d.fillRect(0,500,1500,200);
        
        int w = 0;
        
        for(int i = 0; i < 50; i++){
        	
        	g2d.fillOval(w, 488, 30, 20);
        	w = w+30;
        }
       
       // paintBlocks(board.getBlocks(), g2d);
        paintSquares(board.getSquares(),g2d);
        
    }
	
	public void paintBlocks(ArrayList<PhysObject> blocks, Graphics2D g2d){
    	
		for(PhysObject block : blocks){
			
			
    		int x = (int) block.getPos().getX();
    		//System.out.println("bx is: " + x);
    		
    		int y = (int) block.getPos().getY();
    		y = 700 - y;
    		//System.out.println("by is: " + y);
    		
    		int blocktype = ((TerrainBlock) block).getType();
    		int blockhealth = ((TerrainBlock) block).getHealth();
    		boolean visible = ((TerrainBlock) block).isVisible();
    		
    		if(visible){
    			
    			if (blocktype == 1){
    				g2d.setColor(new Color(139,69,19));
    			}
    			else{
    			
    				g2d.setColor(new Color(105,105,105));
    			}
    		
    			if((blockhealth==1) && (blocktype == 0)){
    				g2d.fillRect(x,y,40,30);
    			}
    			else if((blockhealth==1) && (blocktype == 1)){
    				
    				g2d.fillRect(x, y, 40, 30);
    				g2d.setColor(new Color(135,206,250));
    				
    				for(int i = 0; i <30; i++){
    					
    					int randomx = ThreadLocalRandom.current().nextInt(x, x+40);
    					int randomy = ThreadLocalRandom.current().nextInt(y, y+30);
    					g2d.fillRect(randomx, randomy, 5, 5);
    					
    				}
    			
    			}
    		}
    	}
    }
    
    public void paintSquares(ArrayList<PhysObject> squares, Graphics2D g2d){
    	
    	for(PhysObject square : squares){
    		
    		//square = (Square) square;
    		
    		int x = (int) square.getPos().getX();
    		//System.out.println("x is: " + x);
    		
    		int y = (int) square.getPos().getY();
    		y = 700 - y;
    		//System.out.println("y is: " + y);
    		
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
    		
    		g2d.fillRect(x,y,30,30);
    	
    		
    	}
    	
    }
   
    /* 
    public void paintWeapon(ArrayList<PhysObject> objs, Graphics2D g2d){
    	
    	for(PhysObject obj: objs){
    		
    		if(obj.getType() == BombExplodeOnImpact){
    		
    			g2d.setColor(Color.BLACK);
    			g2d.fillOval((int) obj.getPos().getX(), (int) obj.getPos().getY(), obj.getWidth(), 
    					obj.getHeight());
    		}
    	}
    	
    	
    }
	*/
	
}
