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
import Networking.Client;
import Networking.Queue; 

/**
 * screen class creates screen board - game arena, and the button panel 
 * to display graphical rep of the board
 * 
 * @author Fran
 *
 */
public class Screen extends JFrame {
	
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
	private Client client;
	
	public Screen(Board newboard,Queue q,String name, Client client){
		
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
		setUndecorated(true);
		setLayout(new BorderLayout());
		
		//set attributes
		this.listeners = new HangerOn(q,name,widthratio,heightratio);
		this.client = client;
		
		//create button panel
		this.controls = new ButtonPanel(this,newboard, new Audio(),name,client);
		this.controls.setBackground(lightblue);
		
		//show screen board 
		this.sboard = new ScreenBoard(newboard, heightratio, widthratio, listeners);
		sboard.setBackground(lightblue);
		add(sboard, BorderLayout.CENTER);
		sboard = listeners.hangOn2(sboard);
		
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
	
	/**
	 * start playing music
	 */
	public void startMusic(){	
		controls.startMusic();
	}
	
	/**
	 * Stops the music.
	 */
	public void stopMusic() {
		controls.stopMusic();;
	}
	
	/**
	 * get hanger on associated with this screen
	 * @return listeners The HangerOn object instance
	 */
	public HangerOn getHangerOn(){
		
		return listeners;
	}
	
	/**
	 * Set the UI to become visible, starts the music and starts the listeners.
	 */
	public void start() {
		setVisible();
		startMusic();
		listeners.start();
	}
	
	public void end() {
		setInvisible();
		stopMusic();
		listeners.end();
	}
}