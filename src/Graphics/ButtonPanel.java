package graphics;

import java.awt.BorderLayout;
import java.io.File;
import java.util.concurrent.TimeUnit;

import gameLogic.Board;
import userInterface.mainMenu;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import Audio.*;
import com.sun.prism.paint.Color;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel{

	private boolean music_on = true;
	private boolean first = true;
	private Audio audio;
	private Screen screen;
	
	//@SuppressWarnings("deprecation")
	public ButtonPanel(Screen screen, Board board) {
		
		super();
		
		this.screen = screen;
		
		setLayout(new BorderLayout());
	
		//back to main menu
        ImageIcon image2 = new ImageIcon("Files/Images/cross.png");
        JButton exit = new JButton(image2);
		exit.addActionListener(e -> openMainMenu(screen,board));
		exit.setBorderPainted(false); 
        exit.setContentAreaFilled(false); 
        exit.setFocusPainted(false); 
        exit.setOpaque(false);
		
        audio = new Audio();
		audio.startBackgroundMusic();
		
		//toggle sound
        ImageIcon image3 = new ImageIcon("Files/Images/speaker1.png");
        JButton sound = new JButton(image3);
        sound.addActionListener(e -> ToggleBackgroundMusic(sound));
		sound.setBorderPainted(false); 
		sound.setContentAreaFilled(false); 
		sound.setFocusPainted(false); 
		sound.setOpaque(false);
		
		
		//add(help);
		add(sound,BorderLayout.WEST);
		add(exit, BorderLayout.EAST);
		
	}
	
	

	public void openMainMenu(Screen screen, Board board){
		
		board.notifyQuit();
		screen.setVisible(false);
		mainMenu mainmenu = new mainMenu();
		mainmenu.launchMenu();
		
	}
	public void ToggleBackgroundMusic(JButton button){
		
		if(first){
			firstOff(button);
		}
		else{
		
			if(music_on){
				
				System.out.println("music on");
				button.setOpaque(true);
				//button.setForeground(Color.RED);
				audio.endBackgroundMusic();
			}
			else{
				
				System.out.println("music off");
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
