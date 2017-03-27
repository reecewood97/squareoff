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
 *         creates the panel at the top of the game arena for users to toggle
 *         sound and quit
 */
public class ButtonPanel extends JPanel {

	private boolean music_on = true;
	private boolean first = true;
	private Audio audio;
	private Board board;
	private HangerOn listeners;
	private Client client;
	private String name;
	private JButton weapons;

	/**
	 * constructor
	 * 
	 * @param screen
	 *            The screen
	 * @param board
	 *            The current board
	 * @param audio
	 *            The audio
	 */
	public ButtonPanel(Screen screen, Board board, Audio audio, String name, Client client) {

		super();

		// create main panel
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		add(mainpanel);

		// set attributes
		this.audio = audio;
		this.board = board;
		this.listeners = screen.getHangerOn();
		this.client = client;
		this.name = name;

		// exit button
		ImageIcon image2 = new ImageIcon("Files/Images/cross.png");
		JButton exit = new JButton(image2);
		exit.addActionListener(e -> openMainMenu(screen, board));
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);
		exit.setOpaque(false);

		// toggle sound button
		ImageIcon image3 = new ImageIcon("Files/Images/speaker1.png");
		JButton sound = new JButton(image3);
		sound.addActionListener(e -> ToggleBackgroundMusic(sound));
		sound.setBorderPainted(false);
		sound.setContentAreaFilled(false);
		sound.setFocusPainted(false);
		sound.setOpaque(false);

		// weapons menu button (only till key pressed used)
		weapons = new JButton("weapons menu");
		weapons.addActionListener(e -> showWeaponsMenu());
		weapons.setBorderPainted(false);
		weapons.setContentAreaFilled(false);
		weapons.setFocusPainted(false);
		weapons.setOpaque(false);

		// add buttons to panel
		mainpanel.add(sound, BorderLayout.WEST);
		mainpanel.add(exit, BorderLayout.EAST);
		mainpanel.add(weapons, BorderLayout.CENTER);

	}

	/**
	 * closes the screen
	 * 
	 * @param screen
	 *            The screen
	 * @param board
	 *            The current board
	 */
	public void openMainMenu(Screen screen, Board board) {

		client.disconnect();
		screen.setVisible(false);

	}

	/**
	 * open weapons menu if they are the active player
	 * 
	 */
	public void showWeaponsMenu() {

		// click sound
		Audio audioforclick = new Audio();
		audioforclick.click();

		Square square = (Square) board.getActiveBoard();
		int id = square.getPlayerID();

		ArrayList<String> a = client.getLocalPlayers();

		// checks they are the active player
		for (int i = 0; i < 4; i++) {

			try{
				String name2 = a.get(i);
			

				if ((name2.equals(name)) && ((i + 1) == id)) {
					// open menu
					NewWeaponsMenu menu = new NewWeaponsMenu(listeners, board, weapons);
					menu.open();
					weapons.setEnabled(false);
					
	
				}
			}
			catch(Exception e){
				
			}

		}

		

	}
	
	

	/**
	 * turn background music on and off
	 * 
	 * @param button
	 *            The toggle button
	 */
	public void ToggleBackgroundMusic(JButton button) {


		if (first) {
			firstOff(button);
		} else {

			
			if (music_on) {

				button.setOpaque(true);
				audio.endMusic();
			} 
			else {

				button.setOpaque(false);
				audio.newMusic();
				audio.getBackgroundMusic().start();
			}
		}

		music_on = !music_on;
		

	}
	
	

	/**
	 * start background music
	 */
	public void startMusic() {

		audio.newMusic();
		audio.getBackgroundMusic().start();
	}

	/**
	 * stop background music
	 */
	public void stopMusic() {

		audio.endMusic();
	}
	

	/**
	 * change button colour when pressed initially
	 * 
	 * @param button
	 */
	public void firstOff(JButton button) {
		button.setOpaque(true);
		audio.endMusic();
		first = false;
	}
	
	/**
	 * get method for music_on
	 * @return music_on true/false if music is on/off
	 */
	public boolean musicOn(){
		return music_on;
	}
}
