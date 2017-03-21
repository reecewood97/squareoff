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
		server.close();
	}

	@Test
	public void test() {
		//Bob tries to connect to a server that isn't running.
		System.err.println(1);
		assertFalse(bob.connect("127.0.0.1", 4444));
		assertFalse(bob.isConnected());
		assertNull(bob.getPlayers());
		
		ArrayList<String> expectedPlayers = new ArrayList<String>();
		expectedPlayers.add("Bob");
		
		//A server is started and Bob successfully joins it.
		System.err.println(2);
		server.start();
		assertTrue(bob.connect("127.0.0.1", 4444));
		assertTrue(bob.isConnected());
		assertEquals(expectedPlayers, bob.getPlayers());
		assertEquals(expectedPlayers, server.getPlayers());
		
		expectedPlayers.add("Jerry");
		
		//Jerry successfully joins the server and the list of players is updated for each player.
		System.err.println(3);
		assertTrue(jerry.connect("127.0.0.1", 4444));
		assertTrue(jerry.isConnected());
		assertEquals(expectedPlayers, jerry.getPlayers());
		assertEquals(expectedPlayers, bob.getPlayers());
		
		expectedPlayers.remove("Bob");
		
		//Bob leaves the server.	
		System.err.println(4);
		bob.disconnect();
		assertFalse(bob.isConnected());
		assertEquals(expectedPlayers, jerry.getPlayers());
		assertNull(bob.getPlayers());

		expectedPlayers.add("Bob");
		
		//Bob rejoins the server.
		System.err.println(5);
		assertTrue(bob.connect("127.0.0.1", 4444));
		assertTrue(bob.isConnected());
		assertEquals(expectedPlayers, bob.getPlayers());
		assertEquals(expectedPlayers, jerry.getPlayers());

		
		//ImposterBob fails to connect to the server.
		System.err.println(6);
		assertFalse(imposterBob.connect("127.0.0.1", 4444));
		assertFalse(imposterBob.isConnected());
		
		expectedPlayers.remove("Bob");
		
		//Bob is kicked from the server.
		System.err.println(7);
		assertTrue(server.kick("Bob"));	
		assertFalse(bob.isConnected());
		assertEquals(expectedPlayers, jerry.getPlayers());
		
		//Reset Jerry's connection.
		System.err.println(8);
		assertTrue(jerry.resetConnection());
		assertTrue(jerry.isConnected());
		assertEquals(expectedPlayers, jerry.getPlayers());
		
		assertTrue(bob.connect("127.0.0.1", 4444));
		
		//Close the server.
		System.err.println(9);
		server.close();
		assertFalse(bob.isConnected());
		assertFalse(jerry.isConnected());
		
		//New Server created and Bob and Jerry reconnect.
		System.err.println(10);
		server = new Server(4444);
		server.start();
		assertTrue(bob.connect("127.0.0.1", 4444));
		assertTrue(jerry.connect("127.0.0.1", 4444));
		
	}
	*/

}