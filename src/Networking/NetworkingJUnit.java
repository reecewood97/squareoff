package Networking;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetworkingJUnit {
	
	Server server;
	Client bob, jerry, imposterBob, mary, sherly, danny;
	ArrayList<String> expectedPlayers;

	@Before
	public void setUp() throws Exception {
		server = new Server("map1",4444);
		
		bob = new Client("Bob");
		jerry = new Client("Jerry");
		imposterBob = new Client("Bob");
		mary = new Client("Mary");
		sherly = new Client("Sherly");
		danny = new Client("Danny");
		
		expectedPlayers = new ArrayList<String>();
	}

	@After
	public void tearDown() throws Exception {
		server.close();
	}

	@Test
	public void test() {
		//Bob tries to connect to a server that isn't running.
		System.err.println(1);
		assertTrue(bob.connect("127.0.0.1", 4444) == 1);
		assertFalse(bob.isConnected());
		assertNull(bob.getPlayers());
		
		expectedPlayers.add("Bob");
		
		//A server is started and Bob successfully joins it.
		System.err.println(2);
		server.start();
		wait(1000);
		assertTrue(bob.connect("127.0.0.1", 4444) == 0);
		assertTrue(bob.isConnected());
		assertEquals(expectedPlayers, bob.getPlayers());
		assertEquals(expectedPlayers, server.getPlayers());
		
		expectedPlayers.add("Jerry");
		
		//Jerry successfully joins the server and the list of players is updated for each player.
		System.err.println(3);
		assertTrue(jerry.connect("127.0.0.1", 4444) == 0);
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
		assertTrue(bob.connect("127.0.0.1", 4444) == 0);
		assertTrue(bob.isConnected());
		assertEquals(expectedPlayers, bob.getPlayers());
		assertEquals(expectedPlayers, jerry.getPlayers());

		
		//ImposterBob fails to connect to the server.
		System.err.println(6);
		assertTrue(imposterBob.connect("127.0.0.1", 4444) == 2);
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
		
		assertTrue(bob.connect("127.0.0.1", 4444) == 0);
		
		//Close the server.
		System.err.println(9);
		server.close();
		assertFalse(bob.isConnected());
		assertFalse(jerry.isConnected());
		
		//New Server created and Bob and Jerry reconnect.
		System.err.println(10);
		server = new Server("map1", 4444);
		server.start();
		wait(1000);
		assertTrue(bob.connect("127.0.0.1", 4444) == 0);
		assertTrue(jerry.connect("127.0.0.1", 4444) == 0);
		
		//Reset the server.
		System.err.println(11);
		server.reset();
		wait(1000);
		assertTrue(bob.isConnected());
		assertTrue(jerry.isConnected());
		
		//More clients join.
		System.err.println(12);
		assertTrue(mary.connect("127.0.0.1", 4444) == 0);
		assertTrue(sherly.connect("127.0.0.1", 4444) == 0);
		//Too many clients already connected.
		assertTrue(danny.connect("127.0.0.1", 4444) == 2);
		
		server.kick("mary");
		server.kick("Sherly");
		
		//Start the game.
		System.err.println(13);
		server.startGame();
		assertTrue(bob.inGame());
		assertTrue(jerry.inGame());
		
		wait(1000);
		
		//Reset connection.
		System.err.println(14);
		server.reset();
		wait(1000);
		assertTrue(bob.isConnected());
		assertTrue(jerry.isConnected());
		assertFalse(bob.inGame());
		assertFalse(jerry.inGame());
		
		//Start game again.
		System.err.println(15);
		server.startGame();
		assertTrue(bob.inGame());
		assertTrue(jerry.inGame());
		
		wait(1000);
		
		//Bob disconnects and is replaced by an AI.
		System.err.println(16);
		bob.disconnect();
		wait(1000);
		assertTrue(server.getPlayers().contains("AI 1"));
	}
	
	/**
	 * Just in case.
	 * @param millis How long to wait.
	 */
	private void wait(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
