package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UI extends JFrame {
   
    int framewidth = 1500;
    int frameHeight = 700;
    
    public UI(){
    	setBounds(100, 100, framewidth, frameHeight);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	setTitle("SQUARE-OFF");
    }
    
    
    @Override
    public void paint(Graphics g) {
        
    	super.paint(g);
        
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLUE);
        g2d.fillOval(0, 0, 30, 30);
        
        g.setColor(Color.CYAN);
        g.fillRect(0,0,framewidth,frameHeight);
        
        g.setColor(Color.BLUE);
        g.fillRect(0, 600, framewidth, frameHeight);
        
        JPanel p = new JPanel();
        p.setBackground(Color.GRAY);
      
        g.setColor(Color.yellow);
        g.fillOval(20, 50, 100, 100);
        
    }
    
    public void paintBlocks(List<Block> blocks, Graphics2D g2d){
    	
    	for(Block block : blocks){
    		int x = block.getX();
    		int y = block.getY();
    		String blocktype = block.getType();
    		if (blocktype.equals("Wood")){
    			g2d.setColor(Color.BLACK);
    		}
    		g2d.fillRect(x,y,30,30);
    	}
    }
    
    public void paintSquares(List<Square> squares, Graphics2D g2d){
    	
    	for(Square square : squares){
    		int x = square.getX();
    		int y = square.getY();
    		int playernum = square.getPlayer();
    		if (playernum == 1){
    			g2d.setColor(Color.BLUE);
    		}
    		else if(playernum == 2){
    			g2d.setColor(Color.WHITE);
    		}
    		else if(playernum == 3){
    			g2d.setColor(Color.MAGENTA);
    		}
    		else{
    			
    			g2d.setColor(Color.YELLOW);
    			
    		}
    		
    		g2d.fillRect(x,y,30,30);
    	}
    	
    }

    public void run() throws InterruptedException {
       
    	new UI();
    	
        
    }
}