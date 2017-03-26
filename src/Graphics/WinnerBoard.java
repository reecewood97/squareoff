package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * winner board class displays winning player at game end
 * @author Fran
 *
 */
public class WinnerBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	private int playernum;
	
	/**
	 * constructor
	 * @param playernum The winning player
	 */
	public WinnerBoard(int playernum){
		
		super();
		this.playernum = playernum;
		
		
	}
	
	
	/**
	 * paint method
	 * @param g Graphics
	 * 
	 */
	public void paint(Graphics g) {
        
    	super.paint(g);
        
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        //write winner to screen
        g2d.setColor(Color.WHITE);
        g2d.drawString("The winner is " + playernum, 200, 100);
	
	}
	
	
}
