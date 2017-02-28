package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import GameLogic.Board;
import UserInterface.mainMenu;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Audio.*;


@SuppressWarnings("serial")
public class ButtonPanel extends JPanel{

	private boolean music_on = true;
	private boolean first = true;
	private Audio audio;
	private Board board;
	
	public ButtonPanel(Screen screen, Board board) {
		
		super();
		
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		add(mainpanel);
		mainpanel.setBackground(Color.WHITE);
		//mainpanel.setBounds(0, 0, screenwidth, height);
		
		this.board = board;
	
		//exit button
        ImageIcon image2 = new ImageIcon("Files/Images/cross.png");
        JButton exit = new JButton(image2);
		exit.addActionListener(e -> openMainMenu(screen,board));
		exit.setBorderPainted(false); 
        exit.setContentAreaFilled(false); 
        exit.setFocusPainted(false); 
        exit.setOpaque(false);
		
        //start audio
        audio = new Audio();
		audio.startBackgroundMusic();
		
		//toggle sound button
        ImageIcon image3 = new ImageIcon("Files/Images/speaker1.png");
        JButton sound = new JButton(image3);
        sound.addActionListener(e -> ToggleBackgroundMusic(sound));
		sound.setBorderPainted(false); 
		sound.setContentAreaFilled(false); 
		sound.setFocusPainted(false); 
		sound.setOpaque(false);
		
		//weapons menu button
		JButton weapons = new JButton("weapons menu");
	    weapons.addActionListener(e -> showWeaponsMenu());
		weapons.setBorderPainted(false); 
		weapons.setContentAreaFilled(false); 
		weapons.setFocusPainted(false); 
		weapons.setOpaque(false);
			
		//add buttons to panel
		mainpanel.add(sound,BorderLayout.WEST);
		mainpanel.add(exit, BorderLayout.EAST);
		mainpanel.add(weapons, BorderLayout.CENTER);


	}
	
	public void openMainMenu(Screen screen, Board board){
	
		board.notifyQuit();
		
		screen.setVisible(false);
		//mainMenu mainmenu = new mainMenu();
		mainMenu.showUI();
		
		
	}
	
	public void showWeaponsMenu(){
		
		Audio audioforclick = new Audio();
		audioforclick.click();
		
		NewWeaponsMenu menu = new NewWeaponsMenu(board);
		menu.open();
		
	}
	public void ToggleBackgroundMusic(JButton button){
		
		if(first){
			firstOff(button);
		}
		else{
		
			if(music_on){
				
				//System.out.println("music on");
				button.setOpaque(true);
				audio.endBackgroundMusic();
			}
			else{
				
				//System.out.println("music off");
				button.setOpaque(false);
				audio = new Audio();
				audio.startBackgroundMusic();
			}
		}
		
		music_on = !music_on;
		
	}
	
	public void firstOff(JButton button){
		button.setOpaque(true);
		audio.getBackgroundMusic().end();
		first = false;
	}
	

		
	
}
