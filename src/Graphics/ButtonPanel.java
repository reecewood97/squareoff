package Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import GameLogic.Board;
import GameLogic.Square;
import Networking.Client;
import UserInterface.mainMenu;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import Audio.*;

/**
 * 
 * @author Fran
 *
 * creates the panel at the top of the game arena for users to toggle sound and quit
 */
public class ButtonPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private boolean music_on = true;
	private boolean first = true;
	private Audio audio;
	private Board board;
	private HangerOn listeners;
	private Client client;
	private String name;
	
	/**
	 * constructor 
	 * @param screen The screen
	 * @param board The current board
	 * @param audio The audio
	 */
	public ButtonPanel(Screen screen, Board board, Audio audio,String name, Client client) {
		
		super();
		
		//create main panel
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		add(mainpanel);
		
		//set attributes
		this.audio = audio;
		this.board = board;
		this.listeners = screen.getHangerOn();
		this.client = client;
		this.name= name;
		
		//exit button
        ImageIcon image2 = new ImageIcon("Files/Images/cross.png");
        JButton exit = new JButton(image2);
		exit.addActionListener(e -> openMainMenu(screen,board));
		exit.setBorderPainted(false); 
        exit.setContentAreaFilled(false); 
        exit.setFocusPainted(false); 
        exit.setOpaque(false);
		
        
        //start audio
		//audio.startBackgroundMusic();
		
		//toggle sound button
        ImageIcon image3 = new ImageIcon("Files/Images/speaker1.png");
        JButton sound = new JButton(image3);
        sound.addActionListener(e -> ToggleBackgroundMusic(sound));
		sound.setBorderPainted(false); 
		sound.setContentAreaFilled(false); 
		sound.setFocusPainted(false); 
		sound.setOpaque(false);
		
		//weapons menu button (only till key pressed used)
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
	
	/**
	 * closes the screen
	 * 
	 * @param screen The screen
	 * @param board The current board
	 */
	public void openMainMenu(Screen screen, Board board){
	
		client.disconnect();
		board.notifyQuit(); //method not complete
		screen.setVisible(false);
		
	}

	/**
	 * open weapons menu if they are the active player
	 * 
	 */
	public void showWeaponsMenu(){
		
		
		//click sound
		Audio audioforclick = new Audio();
		audioforclick.click();
		
		Square square = (Square) board.getActivePlayer();
		int id = square.getPlayerID();
		
		ArrayList<String> a = client.getClientList();
	
		//checks they are the active player
		/*
		for(int i = 0; i < 4; i++){
			
			String name2 = a.get(i);
			
			if(name2.equals(name)){
				
				if((i+1)==id){
					
					//open menu*/
					NewWeaponsMenu menu = new NewWeaponsMenu(listeners,board);
					menu.open();
					
					//break;
					
				//}
				
			//}
			
			
			
		//}
		
		
	}
	
	public void startMusic(){
		
		audio.startBackgroundMusic();
	}
	
	/**
	 * Stops the music.
	 */
	public void stopMusic() {
		audio.endBackgroundMusic();
	}
	
	/**
	 * turn background music on and off
	 * 
	 * @param button The toggle button
	 */
	public void ToggleBackgroundMusic(JButton button){
		
		if(first){
			firstOff(button);
		}
		else{
		
			if(music_on){
				
				button.setOpaque(true);
				audio.endBackgroundMusic();
			}
			else{
				
				button.setOpaque(false);
				audio.endBackgroundMusic();
				//audio = new Audio();
				audio.startBackgroundMusic();
			}
		}
		
		music_on = !music_on;
		
	}
	
	/**
	 * change button colour when pressed initially
	 * @param button
	 */
	public void firstOff(JButton button){
		
		button.setOpaque(true);
		audio.endBackgroundMusic();
		first = false;
	}	
}
