/**
 * Class has no main menu, just starts the game with 1 human player
 * Used for testing and has no javafx hence no errors occur on the lab machines
 * @author ksk523
 */
package UserInterface;

import java.util.Scanner;

public class bypassUI {
	
	/**
	 * Main method to run the game with 1 player
	 * @param args arguments passed when running main method (none of which are used)
	 */
	public static void main(String[] args) {
		mainMenuNetwork net = new mainMenuNetwork();
		net.runServer();
		net.connectToHost("127.0.0.1:4444", "a");
		net.startGame();
		
		//Can remove things after this comment when the game terminates correctly
		System.out.println("Type 'q' to exit: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		while (!str.equals("q")) {
			System.out.println("Type 'q' to exit: ");
			str = scanner.nextLine();
		}
		System.exit(1);
	}
	
}