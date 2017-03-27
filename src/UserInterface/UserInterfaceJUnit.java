package UserInterface;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Networking.Server;

public class UserInterfaceJUnit {

	//mainMenu test
	
	@SuppressWarnings("static-access")
	@Test
	public void testShowUI() {
		mainMenu m = new mainMenu();
		//m.launch();
		m.showUI();
		assertEquals(m.isHidden, false);
	}
	
	//mainMenuNetwork tests
	@Test
	public void testConnectToHost() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		assertEquals(net.connectToHost("localhost", "test"), 0);
		net.closeServer();
	}

	@Test
	public void testResetServer() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
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
	public void testInGame() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.startGame();
		assertEquals(net.inGame(), true);
		net.closeServer();
	}
	
	@Test
	public void testStartGame() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.startGame();
		assertEquals(net.inGame(), true);
		net.closeServer();
	}
	
	
	@Test
	public void testSetEasyAIDifficulty() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.setAIDifficulty(1);
		assertEquals(net.getAIDifficulty(), Server.EASY_AI);
		net.closeServer();
	}
	
	
	@Test
	public void testSetNormalAIDifficulty() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.setAIDifficulty(2);
		assertEquals(net.getAIDifficulty(), Server.NORMAL_AI);
		net.closeServer();
	}
	
	@Test
	public void testSetHardAIDifficulty() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		net.setAIDifficulty(3);
		assertEquals(net.getAIDifficulty(), Server.HARD_AI);
		net.closeServer();
	}
	
	@Test
	public void testGetAIDifficulty() {
		mainMenuNetwork net = new mainMenuNetwork("Battleground");
		net.runServer();
		net.connectToHost("localhost", "test");
		assert( (net.getAIDifficulty()==Server.EASY_AI) | (net.getAIDifficulty()==Server.NORMAL_AI) | (net.getAIDifficulty()==Server.HARD_AI) );
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
	
	//Players tests
	@Test
	public void testPlayers() {
		String str1 = "test";
		String str2 = "test 2";
		Players p = new Players(str1, str2);
		assertEquals(p.getPlayerID(),str1);
		assertEquals(p.getPlayerName(), str2);
	}

	@Test
	public void testGetPlayerID() {
		String str1 = "test";
		Players p = new Players(str1, null);
		assertEquals(p.getPlayerID(),str1);
	}

	@Test
	public void testSetPlayerID() {
		String str1 = "test";
		Players p = new Players(str1, null);
		String str2 = "test 2";
		p.setPlayerID(str2);
		assertEquals(p.getPlayerID(),str2);
	}

	@Test
	public void testGetPlayerName() {
		String str1 = "test";
		Players p = new Players(null, str1);
		assertEquals(p.getPlayerName(),str1);
	}

	@Test
	public void testSetPlayerName() {
		String str1 = "test";
		Players p = new Players(null, str1);
		String str2 = "test 2";
		p.setPlayerName(str2);
		assertEquals(p.getPlayerName(),str2);
	}
	
	
}
