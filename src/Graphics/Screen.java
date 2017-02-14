package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;

import GameLogic.Board;
import GameLogic.UserInput;
import Networking.Queue;

@SuppressWarnings("serial")
public class Screen extends JFrame {
   
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
    
    double screenheight = screenSize.getHeight(); 
    double screenwidth = screenSize.getWidth();
    double framewidth = 800;
    double frameHeight = 450;
    //double heightfactor = screenheight/frameHeight;
    //double widthfactor = screenwidth/framewidth;
    
    private ScreenBoard board;
    
    
    public Screen(Board newboard,Queue q){
    	
    	
    	//double newheight = frameHeight * heightfactor;
    	//double newwidth = framewidth * widthfactor;
    
    	setBounds(0,0,(int)screenwidth,(int)screenheight);
    	//setBounds(100, 100, framewidth, frameHeight);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setTitle("SQUARE-OFF");
    	
    	Color lightblue = new Color(135,206,250);
    	
    	ButtonPanel controls = new ButtonPanel(this,newboard);
    	controls.setBackground(lightblue);
    	
    	if(newboard.getWinner() == -1){

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
    	
    }

    
    @Override
    public void paint(Graphics g) {
        
    	super.paint(g);
       
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);  
       
    }
    
    public void setVisible(){
    	
    	setVisible(true);
    
    }
    
    public void setInvisible(){
    	setVisible(false);
    }
    
}