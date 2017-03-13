package Networking;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetworkingJUnit {
	
	Server server;
	Client bob, jerry, imposterBob;

	@Before
	public void setUp() throws Exception {
		//Starts the server.
		server = new Server(4444);
		
		bob = new Client("Bob");
		jerry = new Client("Jerry");
		imposterBob = new Client("Bob");
	}

	@After
	public void tearDown() throws Exception {
		
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
	}
	
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
