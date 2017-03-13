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
			for(int i = 0; i < 20 && running; i++) {
				sleep(1000);
				board.setTime(i);
			} 
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void end() {
		running = false;
	}
}