package GameLogic;

public class TurnServant extends Thread {
	private Board board;

	public TurnServant(Board board) {
		this.board = board;
	}

	public void run() {
		int i = 0;
		try {
			sleep(40);
			i = i + 1;
			board.setTime(i);
			System.out.println(i/25);
			if (i >= 500) { // Should be 500? changed it for ease of testing
				i = 0;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
