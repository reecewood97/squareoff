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
import GameLogic.Board;
import Networking.Client;
import Networking.Queue;

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
    private boolean music_on = true;
	private boolean first = true;
	//SPLASHSPLASH
	//WINNERBOARD
	private int playernum;

	/*
	@Before
	public void setUp() throws Exception {
		
		//screen
		frameheight = 450;
		framewidth = 800;
		screenSize = new Dimension(1080,500);
		board = new Board("map1");
		widthratio = 1.35;
		heightratio = 1.6;
		sBoard = new ScreenBoard(board,heightratio,widthratio,hangerOn);
		listeners = new HangerOn(queue,name,xwidthratio,heightratio);
		controls = new ButtonPanel(screen, Board board, Audio audio,String name, Client client)
		client = new Client(name);
		
		//weapons menu
		wepMenu = new NewWeaponsMenu(hangerOn,board);
		audio = new Audio();
		currentWeapon = 2;
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		
		//newweaponsmenu
		
		
		
		
	}
	*/

}