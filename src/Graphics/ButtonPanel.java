package Graphics;

import java.awt.BorderLayout;
import GameLogic.Board;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import Audio.*;


@SuppressWarnings("serial")
public class ButtonPanel extends JPanel{

	private boolean music_on = true;
	private boolean first = true;
	private Audio audio;
	private Screen screen;
	private NewWeaponsMenu newmenu; // = new WeaponsMenu();
	private boolean firstopen;
	
	
	public ButtonPanel(Screen screen, Board board) {
		
		super();
		this.screen = screen;
		//opened = false;
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
	
		add(sound,BorderLayout.WEST);
		add(exit, BorderLayout.EAST);
		
		firstopen=true;
		newmenu = new NewWeaponsMenu();
		//newmenu.launchMenu();
		//newmenu.exit();
	

	}
	
	public void openMainMenu(Screen screen, Board board){
	
		board.notifyQuit();
		
		/*
		screen.setVisible(false);
		mainMenu mainmenu = new mainMenu();
		mainmenu.launchMenu();
		*/
		
		newmenu.open();
		
		
		
	}
	public void ToggleBackgroundMusic(JButton button){
		
		if(first){
			firstOff(button);
		}
		else{
		
			if(music_on){
				
				System.out.println("music on");
				button.setOpaque(true);
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
