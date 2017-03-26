package UserInterface;

import static org.junit.Assert.*;

import org.junit.Test;

public class mainMenuTest {

	//mainMenu tests
	@SuppressWarnings("static-access")
	@Test
	public void testShowUI() {
		mainMenu m = new mainMenu();
		//m.launch();
		m.showUI();
		assertEquals(m.isHidden, false);
	}
	
}
