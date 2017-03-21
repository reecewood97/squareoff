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
	//SCREENBOARD CLASS
	private Board board;
	//HANGERON
	private Queue q;
	private String name, input, keysPressed;
	private boolean running,targetInUse;
	private double xr;
	private double yr;
	//WEAPONS MENU
    private Audio audio;
    private String[] weaponArray = {"Bomb","Missile","Grenade"};
    private int currentWeapon = 0;
    private JButton image;
    private boolean weaponselected;
	//BUTTON PANEL
    private boolean music_on = true;
	private boolean first = true;
	//SPLASHSPLASH
	//WINNERBOARD
	private int playernum;

	/*
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		
	}
	*/

}