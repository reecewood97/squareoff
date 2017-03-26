package GameLogic;
/**
 * The turnMaster handles the timers of all players centrally, making changes if players end their turn early for any reason. 
 *
 */
public class TurnMaster extends Thread {
	private Board board;
	private int i = 0;
	private boolean running = true;
	
	/**
	 * The constructor
	 * @param board The board the turnMaster manages
	 */
	public TurnMaster(Board board){
		this.board = board;
	}
	/**
	 * The run method just keeps time, counting up to 500 units.
	 */
	public void run(){
		this.i = 0;
		while(running){
			try {
				sleep(40);
				i = i+1;
				board.setTime(i);
				if(i >= 500){ //Should be 500? changed it for ease of testing
					board.setFreeState(true);
					i = 0;
				}
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
		
	}
	/** 
	 * Used to reset the timer when turns are ended before their full duration.
	 */
	public void resetTimer() {
			i = 0;
	}

}
