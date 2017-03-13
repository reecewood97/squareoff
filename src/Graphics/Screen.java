package Graphics; 

import java.awt.BorderLayout;
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Graphics; 
import java.awt.Graphics2D; 
import java.awt.RenderingHints; 
import java.awt.Toolkit; 
import javax.swing.JFrame;
import Audio.Audio;
import GameLogic.Board; 
import Networking.Queue; 

/**
 * screen class creates screen board - game arena, and the button panel 
 * to display graphical rep of the board
 * 
 * @author Fran
 *
 */
public class Screen extends JFrame {
	
	private static final long serialVersionUID = 6929712903042802033L;
	private Dimension screenSize;
	private double screenheight;
	private double screenwidth;
	private double framewidth = 800;
	private double frameheight = 450;
	private double widthratio;
	private double heightratio;
	private ScreenBoard sboard;
	private HangerOn listeners;
	private ButtonPanel controls;
	
	public Screen(Board newboard,Queue q,String name){
		
		//set window size
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenheight = screenSize.getHeight();
		screenwidth = screenSize.getWidth();
		heightratio = screenheight/frameheight;
		widthratio = screenwidth/framewidth;
		setBounds(0,0,(int)screenwidth,(int)screenheight);
		
		//set background colour to light blue
		Color lightblue = new Color(135,206,250);

		//set design
		setUndecorated(false);//CHANGE ME BEFORE FINAL RELEASE
		setLayout(new BorderLayout());
		
		this.listeners = new HangerOn(q,name);
		listeners.start();
		
		//create button panel
		this.controls = new ButtonPanel(this,newboard, new Audio());
		this.controls.setBackground(lightblue);
		
		//show screen board if no winner
		if(newboard.getWinner() == -1){
			
			this.sboard = new ScreenBoard(newboard, heightratio, widthratio, listeners);
			sboard.setBackground(lightblue);
			add(sboard, BorderLayout.CENTER);
			
			sboard = listeners.hangOn2(sboard);
		}
		//show winner board if there is a winner
		else{
			
			WinnerBoard wboard = new WinnerBoard(newboard.getWinner());
			wboard.setBackground(Color.BLACK);
			controls.setBackground(Color.BLACK);
			add(wboard, BorderLayout.CENTER);
			
		}
		
		//add button panel to screen
		add(controls, BorderLayout.NORTH);
		
		//hide
		setVisible(false);
	}
	
	
	/**
	 * paint method
	 * @param g Graphics
	 */
	@Override     
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}
	

	
	/**
	 * 
	 * show screen method
	 */
	public void setVisible(){
		setVisible(true);
	}     
	
	/**
	 * hide screen method
	 */
	public void setInvisible(){
		setVisible(false);
	}     
	
	/**
	 * repaint screen board
	 */
	public void updateSBoard(){
		
		
		sboard.repaint();
		this.repaint(); 
		
	}
	
	public void startMusic(){
		
		controls.startMusic();
	}
	
	/**
	 * get hanger on associated with this screen
	 * @return listeners The HangerOn object instance
	 */
	public HangerOn getHangerOn(){
		
		return listeners;
	}
}