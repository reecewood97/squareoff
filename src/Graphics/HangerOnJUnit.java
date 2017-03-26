package Graphics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import GameLogic.Board;
import Networking.Queue;

public class HangerOnJUnit {
	private HangerOn h;
	private Queue testQ;

	@Before
	public void setUp() {
		this.testQ = new Queue();
		String name = "sam";
		Double xr = 1.0;
		Double yr = 1.0;
		this.h = new HangerOn(testQ,name,xr,yr);
	}
	
	@After
	public void tearDown() {
		h.interrupt();
	}
	
	@Test
	public void test() {
		//Start off by checking the methods we call
		h.setWep("test");
		assertTrue(testQ.take().toString().contains("test"));
		
		h.setExp("test2");
		assertTrue(testQ.take().toString().contains("test2"));
		
		h.setUse("test3");
		assertTrue(testQ.take().toString().contains("test3"));
		
		h.setTargetLine("test4", false);
		assertTrue(testQ.take().toString().contains("test4"));
		
		ScreenBoard board = new ScreenBoard(new Board("Battleground"),1.0,1.0,h);
		h.hangOn2(board);
		board.grabFocus();
		assertTrue(board.isFocusable());
		
		h.start();
		assertTrue(testQ.take().toString().contains("sam"));
		
		
	}
}
