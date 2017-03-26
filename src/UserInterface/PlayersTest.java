package UserInterface;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayersTest {

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
