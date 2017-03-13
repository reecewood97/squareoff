package GameLogic;

public class TurnServant extends Thread {
	private Board board;

	public TurnServant(Board board) {
		this.board = board;
	}

	public void run() {
		int i = 0;
		boolean running = true;
		while (running) {
			try {
				sleep(1000);
				i = i + 1;
				board.setTime(i);
				//System.out.println(i / 25);
				if (i >= 20) { // Should be 500? changed it for ease of testing
					running = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
