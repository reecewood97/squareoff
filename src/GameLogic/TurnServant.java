package GameLogic;

/**
 * TurnServant is a small version of TurnMaster, all it deals with is a timer for showing on the local board.
 */
public class TurnServant extends Thread {
	private Board board;
	private boolean running;

	/**
	 * The constructor
	 * @param board The board that the timer is keeping tabs on
	 */
	public TurnServant(Board board) {
		this.board = board;
	}

	/** 
	 * In the run method the class just handles counting for 20 secs.
	 */
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
