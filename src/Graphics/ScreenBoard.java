package Graphics;

import javax.swing.JPanel;
import GameLogic.Board;
import GameLogic.Square;
import GameLogic.TerrainBlock;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;


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
       
        paintBlocks(board.getBlocks(), g2d);
        paintSquares(board.getSquares(),g2d);
        
    }
	
	public void paintBlocks(ArrayList<TerrainBlock> blocks, Graphics2D g2d){
    	
		for(TerrainBlock block : blocks){
			
			//System.out.println("length of blocks is " + blocks.size());
    		int x = (int) block.getPos().getX();
    		//System.out.println("bx is: " + x);
    		
    		int y = (int) block.getPos().getY();
    		y = 700 - y;
    		//System.out.println("by is: " + y);
    		
    		int blocktype = block.getType();
    		boolean visible = block.isVisible();
    		
    		if(visible){
    			
    			if (blocktype == 1){
    				g2d.setColor(Color.BLACK);
    			}
    			else{
    			
    				g2d.setColor(Color.ORANGE);
    			}
    		
    			g2d.fillRect(x,y,60,30);
    		}
    	}
    }
    
    public void paintSquares(ArrayList<Square> squares, Graphics2D g2d){
    	
    	for(Square square : squares){
    		int x = (int) square.getPos().getX();
    		//System.out.println("x is: " + x);
    		
    		int y = (int) square.getPos().getY();
    		y = 700 - y;
    		//System.out.println("y is: " + y);
    		
    		int playernum = square.getPlayerID();
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
	
	
}
