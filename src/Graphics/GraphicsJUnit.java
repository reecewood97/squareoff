package Graphics;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Audio.Audio;
import Audio.BackgroundMusic;
import GameLogic.Board;
import GameLogic.Explosion;
import GameLogic.TimedGrenade;
import Networking.Client;
import Networking.Queue;

/**
 * class for testing graphics methods - most testing has been completed by user testing
 * @author Fran
 *
 */
public class GraphicsJUnit {

	//SCREEN CLASS
	private Dimension screenSize;
	//SCREEN CLASS
	//private Dimension screenSize;
	private double screenheight;
	private double screenwidth;
	private double framewidth;
	private double frameheight;
	private double widthratio;
	private double heightratio;
	private ScreenBoard sboard;
	private HangerOn listeners;
	private ButtonPanel controls;
	private Client client;
	private Screen screen;
	private JButton btn;
	private WinnerBoard winboard;
	//SCREENBOARD CLASS
	private Board board;
	//HANGERON
	private Queue q;
	private String name, input, keysPressed;
	private boolean running,targetInUse;
	//WEAPONS MENU
    private Audio audio;
    private String[] weaponArray = {"Bomb","Missile","Grenade"};
    private int currentWeapon = 0;
    private JButton image;
    private boolean weaponselected;
    private NewWeaponsMenu wepMenu;
	//BUTTON PANEL
    private boolean music_on;
	private boolean first;
	private boolean run;
	private BackgroundMusic music;
	//SPLASHSPLASH
	private SplashSplash splash;
	//WINNERBOARD
	private int playernum;


	@Before
	public void setUp() throws Exception {
		
		//screen
		frameheight = 450;
		framewidth = 800;
		screenSize = new Dimension(1080,500);
		board = new Board("Battleground");
		widthratio = 1.35;
		heightratio = 1.6;
		name = "Bob";
		q = new Queue();
		client = new Client(name);
		listeners = new HangerOn(q,name,widthratio,heightratio);
		screen = new Screen(board, q, name, client);
		sboard = new ScreenBoard(board,heightratio,widthratio,listeners);
		winboard = new WinnerBoard(-1);
		
		audio = new Audio();
		music = audio.getBackgroundMusic();
		controls = new ButtonPanel(screen,board,audio,name,client);
		btn = new JButton();
		//weapons menu
		wepMenu = new NewWeaponsMenu(listeners,board,btn);
		board.addName("Bob");
		board.addName("Dave");
		board.addName("Sharon");
		board.addName("Liz");
		
		first = true;
		music_on = true;
		run = false;
		
		splash = new SplashSplash(10);
		
		
	}

	@Test
	public void test() {
		
		//button panel
		first = true;
		music_on = true;
		controls.ToggleBackgroundMusic(btn);
		
		//weapons menu tests
		wepMenu.setInvisible();
		assertFalse(wepMenu.isVisible());
		wepMenu.open();
		assertTrue(wepMenu.isVisible());
		int current = wepMenu.getCurrentWeapon();
		wepMenu.cycleRight();
		assert(current+1==wepMenu.getCurrentWeapon());
		current = wepMenu.getCurrentWeapon();
		wepMenu.cycleLeft();
		assert(current-1==wepMenu.getCurrentWeapon());
		wepMenu.select();
		assertFalse(wepMenu.isVisible());
		wepMenu.open();
		wepMenu.exit();
		assertFalse(wepMenu.isVisible());
		
		//screen
		screen.setVisible();
		assertTrue(screen.isVisible());
		screen.setInvisible();
		assertFalse(screen.isVisible());
		screen.start();
		assertTrue(screen.isVisible());
		screen.end();
		assertFalse(screen.isVisible());
		
		//hangeron
		listeners.setWep("WeaponTimedGrenade");
		listeners.setExp("1");
		
		//splash screen
		splash.showSplash();
		assertFalse(splash.isVisible());
		
		
		
	}
	

}