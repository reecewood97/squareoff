package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import GameLogic.Board;
import GameLogic.UserInput;
import Networking.MoveQueue;

@SuppressWarnings("serial")
public class Screen extends JFrame {
   
    int framewidth = 1500;
    int frameHeight = 700;
    private ScreenBoard board;
   // private Audio audio;
    
    public Screen(Board newboard,MoveQueue q){
    	
    	//this.audio = audio;
    	
    	setBounds(100, 100, framewidth, frameHeight);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setTitle("SQUARE-OFF");
    	
    	Color lightblue = new Color(135,206,250);
    	
    	ButtonPanel controls = new ButtonPanel(this);
    	controls.setBackground(lightblue);
    	
    	if(newboard.getWinner() == 0){
    		
    		ScreenBoard sboard = new ScreenBoard(newboard);
    		sboard.setBackground(lightblue);
    		setLayout(new BorderLayout());
    		add(sboard, BorderLayout.CENTER);
    		add(controls, BorderLayout.NORTH);
 
    		HangerOn listeners = new HangerOn(q);
    		sboard = listeners.hangOn2(sboard);
    	}
    	else{
    		
    		WinnerBoard wboard = new WinnerBoard(newboard.getWinner());
    		wboard.setBackground(Color.BLACK);
    		controls.setBackground(Color.BLACK);
    		setLayout(new BorderLayout());
    		add(wboard, BorderLayout.CENTER);
    		add(controls, BorderLayout.NORTH);
    		
    	}
    	
    	   	
    	setVisible(true);
    }
    	    	
//    	this.board = new ScreenBoard(newboard);
//    	board.setBackground(lightblue);
//    	
//    	setLayout(new BorderLayout());
//    	add(board, BorderLayout.CENTER);
//    	add(controls, BorderLayout.NORTH);
// 
//    	HangerOn listeners = new HangerOn(q);
//    	board = listeners.hangOn2(board);
//    	   	
//    	setVisible(true);
    
    
    @Override
    public void paint(Graphics g) {
        
    	super.paint(g);
        
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      
       
    }
    
       
    
}