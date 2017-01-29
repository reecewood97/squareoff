package Graphics;

import javax.swing.JPanel;
import GameLogic.Board;
import GameLogic.Square;
import GameLogic.TerrainBlocks;
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
		
			
		this.setBackground(Color.cyan);
		this.board = board;
		
	
		
	}
	
	@Override
    public void paint(Graphics g) {
        
    	super.paint(g);
        
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
       
        
        g2d.setColor(Color.yellow);
        g2d.fillOval(40, 40,  40, 40);
        
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
	
	public void paintBlocks(ArrayList<TerrainBlocks> blocks, Graphics2D g2d){
    	
    	for(TerrainBlocks block : blocks){
    		int x = (int) block.getPos().getX();
    		int y = (int) block.getPos().getY();
    		int blocktype = block.getType();
    		boolean visible = block.isVisible();
    		
    		if(visible){
    			
    			if (blocktype == 1){
    				g2d.setColor(Color.BLACK);
    			}
    			else{
    			
    				g2d.setColor(Color.ORANGE);
    			}
    		
    			g2d.fillRect(x,y,30,30);
    		}
    	}
    }
    
    public void paintSquares(ArrayList<Square> squares, Graphics2D g2d){
    	
    	for(Square square : squares){
    		int x = (int) square.getPos().getX();
    		int y = (int) square.getPos().getY();
    		int playernum = square.getPlayer();
    		if (playernum == 1){
    			g2d.setColor(Color.PINK);
    		}
    		else if(playernum == 2){
    			g2d.setColor(Color.RED);
    		}
    		else if(playernum == 3){
    			g2d.setColor(Color.GREEN);
    		}
    		else{
    			
    			g2d.setColor(Color.YELLOW);
    			
    		}
    		
    		g2d.fillRect(x,y,30,30);
    	}
    	
    }
	
	
}
