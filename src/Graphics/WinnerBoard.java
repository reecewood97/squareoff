package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class WinnerBoard extends JPanel {

	
	private int playernum;
	
	public WinnerBoard(int playernum){
		
		super();
		this.playernum = playernum;
		
		
	}
	
	public void paint(Graphics g) {
        
    	super.paint(g);
        
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(Color.WHITE);
        g2d.drawString("The winner is " + playernum, 200, 100);
	
	}
	
	
}
