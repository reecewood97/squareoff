package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JFrame;


import GameLogic.Board;

@SuppressWarnings("serial")
public class Screen extends JFrame {
   
    int framewidth = 1500;
    int frameHeight = 700;
    
    public Screen(Board newboard){
    	setBounds(100, 100, framewidth, frameHeight);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	setTitle("SQUARE-OFF");
    	
    
    	ButtonPanel controls = new ButtonPanel();
    	controls.setBackground(Color.WHITE);
    	controls.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    	
    	ScreenBoard board = new ScreenBoard(newboard);
    	//HangerOn listeners = new HangerOn();
    	//board = listeners.hangOn2(board);
    
    	setLayout(new BorderLayout());
    	add(board, BorderLayout.CENTER);
    	add(controls, BorderLayout.NORTH);
 
    	   	
    }
    
    
    @Override
    public void paint(Graphics g) {
        
    	super.paint(g);
        
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    
       
    }
    
    
}