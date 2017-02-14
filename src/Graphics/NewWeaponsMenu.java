package Graphics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameLogic.Board;
import Networking.Queue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NewWeaponsMenu extends JFrame {

	
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	    
	    double screenheight = screenSize.getHeight(); 
	    double screenwidth = screenSize.getWidth();
	    double framewidth = 800;
	    double frameHeight = 450;
	    String[] weaponArray = {"Bomb","Ball","Grenade"};
	    int currentWeapon = 0;
	    JLabel picLabel;
	 
	    public NewWeaponsMenu(){
	    	
	    	setBounds(0,0,(int)screenwidth/10,(int)screenheight/10);
	    	setUndecorated(true);
	    	setBackground(Color.white);
	    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	setTitle("SQUARE-OFF");
	    
	    	JPanel toppanel = new JPanel();
	    	JPanel bottompanel = new JPanel();
	    	JPanel centerpanel = new JPanel();
	    	toppanel.setBackground(Color.blue);
	    	centerpanel.setBackground(Color.green);
	    	//bottompanel.setBackground(Color.black);
	    	//centerpanel.setBackground(Color.green);
	    	
	    	JButton select = new JButton("Select");
	    	JButton left = new JButton("<");
	    	JButton right = new JButton(">");
	    	JButton exit = new JButton("Exit");
	    	
	    	left.addActionListener(e -> cycleLeft());
	    	right.addActionListener(e -> cycleRight());
	    	select.addActionListener(e -> select());
	    	exit.addActionListener(e -> exit());
	    	
	    	centerpanel.setLayout(new BorderLayout());
	    	toppanel.add(new JLabel("Weapons Menu"), BorderLayout.NORTH);
	    	centerpanel.add(left, BorderLayout.WEST);
	    	
	    	
	    	
	    	bottompanel.setLayout(new FlowLayout());
	    	bottompanel.add(select);
	    	bottompanel.add(exit);
	    
	    	add(toppanel,BorderLayout.NORTH);
	    	add(bottompanel, BorderLayout.SOUTH);
	    	add(centerpanel, BorderLayout.CENTER);
	    	
	    	
	    	
	    	
	    	
			try {
				BufferedImage myPicture = ImageIO.read(new File("Files/Images/" + weaponArray[currentWeapon] 
						+ ".png"));
				//picLabel = new JLabel(new ImageIcon(myPicture));
				picLabel = new JLabel(weaponArray[currentWeapon]);
				//picLabel.setBounds(0, 0, 10, 10);
				centerpanel.setLayout(new FlowLayout());
				centerpanel.add(new JLabel("   "));
				centerpanel.add(picLabel);
				centerpanel.add(new JLabel("   "));
				
				
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			centerpanel.add(right, BorderLayout.EAST);
			
			centerpanel.setBackground(Color.WHITE);
			toppanel.setBackground(Color.WHITE);
			bottompanel.setBackground(Color.WHITE);
			
	    	
			setVisible(false);
	    	
	    }

	    
	    @Override
	    public void paint(Graphics g) {
	        
	    	super.paint(g);
	       
	    	Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);  
	       
	    }
	    
	    public void open(){
	    	
	    	setVisible(true);
	    }
	    
	    public void setVisible(){
	    	
	    	setVisible(true);
	    
	    }
	    
	    public void setInvisible(){
	    	setVisible(false);
	    }
	    



	public void cycleRight(){
	  
	  if (currentWeapon == 2){
		  currentWeapon = 0;
	  }
	  else{
		  currentWeapon = currentWeapon + 1;
	  }
	  

	  BufferedImage image;
	  try {
		image = ImageIO.read(new File("Files/Images/" + weaponArray[currentWeapon] 
					+ ".png"));

		  picLabel.setText(weaponArray[currentWeapon]);
	  }
	  catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  
	 // picLabel.setIcon(new ImageIcon(image));
	  
	  
	}
	
	public void select(){
		//TODO
	}


	public void cycleLeft(){
	  
	  if (currentWeapon == 0){
		  currentWeapon = 2;
	  }
	  else{
		  currentWeapon = currentWeapon - 1;
	  }
	  
	  BufferedImage image;
	
	  //image = ImageIO.read(new File("Files/Images/" + weaponArray[currentWeapon] 
		//		+ ".png"));
	  picLabel.setText(weaponArray[currentWeapon]);
	  
	}
	
	public void exit(){
		
		setVisible(false);
	}
}
