package Graphics;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Audio.Audio;
import Audio.BackgroundMusic;
import GameLogic.Board;
import GameLogic.ExplodeOnImpact;
import GameLogic.Explosion;
import GameLogic.Particle;
import GameLogic.PhysObject;
import GameLogic.Square;
import GameLogic.TargetLine;
import GameLogic.TerrainBlock;
import GameLogic.TimedGrenade;
import Networking.Client;
import Networking.Queue;
import UserInterface.mainMenu;

/**
 * class for testing graphics methods - most testing has been completed by user testing
 * @author Fran
 *
 */
public class GraphicsJUnit extends JFrame{

	//SCREEN CLASS
	private Dimension screenSize;
	private double frameheight;
	private double widthratio;
	private double heightratio;
	private ScreenBoard sboard;
	private HangerOn listeners;
	private ButtonPanel controls;
	private Client client;
	private Screen screen;
	private JButton btn;
	//SCREENBOARD CLASS
	private Board board;
	//HANGERON
	private Queue q;
	private String name, input;
	//WEAPONS MENU
    private Audio audio;
    private NewWeaponsMenu wepMenu;
	private BackgroundMusic music;
	//SPLASHSPLASH
	private SplashSplash splash;
	@Before
	public void setUp() throws Exception {
		
		//screen
		frameheight = 450;
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
		
		splash = new SplashSplash(10);
		
		
	}

	@Test
	public void test() {
		
		//toggle music
		controls.ToggleBackgroundMusic(btn);
		assertFalse(controls.musicOn());
		controls.ToggleBackgroundMusic(btn);
		assertTrue(controls.musicOn());
		
		//weapons menu tests
		wepMenu.setInvisible();
		assertFalse(wepMenu.isVisible());
		wepMenu.open();
		assertTrue(wepMenu.isVisible());
		int current = wepMenu.getCurrentWeapon();
		wepMenu.cycleRight();
		assertTrue(current+1==wepMenu.getCurrentWeapon());
		current = wepMenu.getCurrentWeapon();
		wepMenu.cycleLeft();
		assertTrue(current-1==wepMenu.getCurrentWeapon());
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
		
		//buttonpanel
		controls.openMainMenu(screen, board);
		assertFalse(screen.isVisible());
		
		//check maps
		Screen screen2 = new Screen(new Board("X"), q, input, client);
		screen2.setVisible();
		assertTrue(screen2.isVisible());
		
		Screen screen3 = new Screen(new Board("Battleground"), q, input, client);
		screen3.setVisible();
		assertTrue(screen3.isVisible());
		
		Screen screen4 = new Screen(new Board("Sandwich"), q, input, client);
		screen4.setVisible();
		assertTrue(screen4.isVisible());
		
		Screen screen5 = new Screen(new Board("No Hiding"), q, input, client);
		screen5.setVisible();
		assertTrue(screen5.isVisible());
		
		new Screen(new Board("Pyramid"), q, input, client);
		screen2.setVisible();
		assertTrue(screen2.isVisible());
		
		Screen screen7 = new Screen(new Board("Smile"), q, input, client);
		screen7.setVisible();
		assertTrue(screen7.isVisible());
		
		
		//painting 
		ArrayList<PhysObject> a = new ArrayList<PhysObject>();
		ExplodeOnImpact e = new ExplodeOnImpact(new Point2D.Double(10, 10),5.0,5.0,true);
		e.setInUse(true);
		TerrainBlock t = new TerrainBlock(1, 1, new Point2D.Double(4, 4), true);
		t.setInUse(true);
		Particle p = new Particle(new Point2D.Double(5,5), 100.0, 100.0);
		p.setInUse(true);
		Square sq = new Square(1, 1, 1, new Point2D.Double(4, 4));
		sq.setFacing("Left");
		Square sq2 = new Square(1, 1, 1, new Point2D.Double(400, 400));
		sq2.setFacing("Right");
		TargetLine tl = new TargetLine();
		tl.setInUse(true);
		Explosion ep = new Explosion(new Point2D.Double(400,400));
		ep.setInUse(true);
		a.add(e);
		a.add(t);
		a.add(p);
		a.add(sq);
		a.add(sq2);
		a.add(tl);
		a.add(ep);
		board.setObjects(a);
		Board newone = new Board("X");
		newone.setObjects(a);
		ScreenBoard sb = new ScreenBoard(newone, frameheight, frameheight, listeners);
		assertTrue(newone.getWeapons().get(0).getInUse());
		assertTrue(newone.getExplosion().get(0).getInUse());
		sb.repaint();
		screen.updateSBoard();
		//check manipulation
		assertTrue(board.getObjects().contains(e));
		assertTrue(board.getObjects().contains(t));
		assertTrue(board.getObjects().contains(p));
		assertTrue(board.getObjects().contains(tl));
		assertTrue(board.getObjects().contains(ep));
		
		
		
		
	}
	


}