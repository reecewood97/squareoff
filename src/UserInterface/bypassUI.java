package UserInterface;

import java.util.Scanner;

public class bypassUI {
	
	public static void main(String[] args) {
		mainMenuNetwork net = new mainMenuNetwork();
		net.runServer();
		net.connectToHost("127.0.0.1:4444", "a");
		net.startGame();
		
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