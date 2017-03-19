package GameLogic;

public class TurnServant extends Thread {
	private Board board;
	private boolean running;

	public TurnServant(Board board) {
		this.board = board;
	}

	public void run() {
		running = true;
		try {
			for(int i = 20; i > 0 && running; i--) {
				sleep(1000);
				board.setTime(i);
				if (!running)
					System.err.println("Thread should be killed");
			} 
		}
		catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
}
