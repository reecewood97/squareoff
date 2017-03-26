package UserInterface;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class mainMenuNetworkTest {

	@Test
	public void testConnectToHost() {
		mainMenuNetwork net = new mainMenuNetwork("map1");
		net.runServer();
		assertEquals(net.connectToHost("localhost", "test"), 0);
		net.closeServer();
	}

	@Test
	public void testResetServer() {
		mainMenuNetwork net = new mainMenuNetwork("map1");
		net.runServer();
		net.connectToHost("localhost", "test");
		ArrayList<String> preResetList = net.getPlayers();
		net.resetServer();
		assertEquals(net.getPlayers(), preResetList);
		net.closeServer();
	}

	@Test
	public void testDisconnect() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.Disconnect();
		assertEquals(net.isConnected(), false);
		net.closeServer();
	}

	@Test
	public void testIsConnected() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		assertEquals(net.isConnected(), true);
		net.closeServer();
	}

	@Test
	public void testRunServer() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		assertEquals(net.isConnected(), true);
		net.closeServer();
	}
	
	@Test
	public void testGetPlayers() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		assertEquals(net.getPlayers().get(0), "test");
		net.closeServer();
	}

	@Test
	public void testCloseServer() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.closeServer();
		net.connectToHost("localhost", "test");
		assertEquals(net.isConnected(), false);
	}
	
	/*
	@Test
	public void testStartGame() {
		mainMenuNetwork net = new mainMenuNetwork("map1");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.startGame();
		assertEquals(net.inGame(), true);
		net.closeServer();
	}

	@Test
	public void testInGame() {
		mainMenuNetwork net = new mainMenuNetwork("map1");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.startGame();
		assertEquals(net.inGame(), true);
		net.closeServer();
	}
	*/
}
