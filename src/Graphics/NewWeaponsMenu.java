package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Audio.Audio;
import GameLogic.Board;

/**
 * 
 * @author Fran
 *
 */
public class NewWeaponsMenu extends JFrame {

		private static final long serialVersionUID = 1L;
		private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	    private double screenheight = screenSize.getHeight(); 
	    private double screenwidth = screenSize.getWidth();
	    private Audio audio;
	   // private double framewidth = 800;
	    //private double frameHeight = 450;
	    private String[] weaponArray = {"Bomb","Ball","Grenade"};
	    private int currentWeapon = 0;
	    //private JLabel picLabel;
	    private JButton image;
	    
	 
	    /**
	     * weapon menu constructor
	     */
	    public NewWeaponsMenu(){
	    	
	    		    	
	    	//edit menu settings
	    	setBounds(0,0,(int)screenwidth/10,(int)screenheight/8);
	    	setUndecorated(true);
	    	setBackground(Color.white);
	    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	setTitle("SQUARE-OFF");
	    
	    	
	    	//create panels
	    	JPanel toppanel = new JPanel();
	    	JPanel bottompanel = new JPanel();
	    	JPanel centerpanel = new JPanel();
	    	
	    	JPanel leftbuttonpanel = new JPanel();
	    	leftbuttonpanel.setLayout(new BorderLayout());
	    	
	    	JPanel rightbuttonpanel = new JPanel();
	    	rightbuttonpanel.setLayout(new BorderLayout());
	    	
	    	
	    	//create buttons
	    	JButton select = new JButton("Select");
	    	JButton left = new JButton("<");
	    	JButton right = new JButton(">");
	    	JButton exit = new JButton("Exit");
	    	 	
	    
	    	//add left button to leftbuttonpanel
	    	leftbuttonpanel.setLayout(new BorderLayout());
	    	leftbuttonpanel.add(new JLabel(" "), BorderLayout.NORTH);
	    	leftbuttonpanel.add(left, BorderLayout.CENTER);
	    	leftbuttonpanel.add(new JLabel(" "), BorderLayout.SOUTH);
	    	
	    	//add right button to right button panel
	    	rightbuttonpanel.setLayout(new BorderLayout());
	    	rightbuttonpanel.add(new JLabel(" "), BorderLayout.NORTH);
	    	rightbuttonpanel.add(right, BorderLayout.CENTER);
	    	rightbuttonpanel.add(new JLabel(" "), BorderLayout.SOUTH);
	    	
	    	//set current weapon image
	    	ImageIcon image2 = new ImageIcon("Files/Images/" + weaponArray[currentWeapon] + ".png");
	    	image = new JButton(image2);
	    	image.setSize(5,5);
			image.setBorderPainted(false); 
			image.setContentAreaFilled(false); 
			image.setFocusPainted(false); 
			image.setOpaque(false);
			
			//establish button actions
	    	left.addActionListener(e -> cycleLeft());
			right.addActionListener(e -> cycleRight());
	    	select.addActionListener(e -> select());
	    	exit.addActionListener(e -> exit());
	    	
	    	//add weapon menu title to menu
	    	toppanel.add(new JLabel("Weapons Menu"), BorderLayout.NORTH);
	    	
	    	//add leftbuttonpanel, weapon and rightbuttonpanel to centerpanel
	    	centerpanel.setLayout(new BorderLayout());
	    	centerpanel.add(leftbuttonpanel, BorderLayout.WEST);
	    	centerpanel.add(image,BorderLayout.CENTER);
	        centerpanel.add(rightbuttonpanel, BorderLayout.EAST);
			
	    	//add select adn exit buttons to bottompanel
	    	bottompanel.setLayout(new FlowLayout());
	    	bottompanel.add(select);
	    	bottompanel.add(exit);
	    
	    	//add main panels to menu
	    	add(toppanel,BorderLayout.NORTH);
	    	add(bottompanel, BorderLayout.SOUTH);
	    	add(centerpanel, BorderLayout.CENTER);
	    	
	    	//set panel colours
			centerpanel.setBackground(Color.WHITE);
			toppanel.setBackground(Color.WHITE);
			bottompanel.setBackground(Color.WHITE);
			
			
			//establish sounds
			audio = new Audio();
			
	    	//hide menu
			setVisible(false);
	    	
	    }

	    
	    /**
	     * get current weapon method
	     * @return currentWeapon the weapon in use
	     */
	    public int getCurrentWeapon(){
	    	
	    	return currentWeapon;
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
	     * open menu method
	     */
	    public void open(){
	    	
	    	setVisible(true);
	    }
	    
	    
	    /**
	     * hide menu method
	     */
	    public void setInvisible(){
	    	
	    	setVisible(false);
	    }
	    


	    /**
	     * cycleRight method cycles weapons array to the right
	     * 
	     */
	    public void cycleRight(){
	  
	    	//click sound
	    	audio.click();
	    	
	    	//update current weapon
	    	if (currentWeapon == 2)
	    	{
	    		
	    		currentWeapon = 0;
	    	}
	    	else
	    	{
	    		currentWeapon = currentWeapon + 1;
	    	}
	  
	  
	    	//update image
	    	updateWeaponImage();
	  
	   
	    }
	
	    
	    /**
	     * select weapon
	     */
	    public void select(){
	    	
	    	//click sound
	    	audio.click();
	    	
	    	//TODO notify board of weapon choice
	    }


	    public void cycleLeft() {
	  
	    	//click sound
	    	audio.click();
	    	
	    	if (currentWeapon == 0)
	    	{
	    		currentWeapon = 2;
	    	}
	    	else
	    	{
	    		currentWeapon = currentWeapon - 1;
	    	}
		  
	    	//update image
	    	updateWeaponImage();
	  
	    }
	
	
	    /**
	     * updates weapon image to current weapon
	     */
	    public void updateWeaponImage(){
	    	
	    	//update image to current weapon
	    	ImageIcon image2 = new ImageIcon("Files/Images/" + weaponArray[currentWeapon] + ".png");
	    	image.setIcon(image2);
	    	
	    }
	    
	    /**
	     * exit method hides menu
	     */
	    public void exit(){
	    	
	    	//click sound
	    	audio.click();
	    	
	    	//hide menu
	    	setVisible(false);
	    }
}
