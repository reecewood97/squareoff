package GameLogic;

public class TurnMaster extends Thread {
	private Board board;
	private int i = 0;
	private boolean running = true;
	
	public TurnMaster(Board board){
		this.board = board;
	}
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
	public void resetTimer() {
			i = 0;
	}
	public void endItAll(){
		this.running = false;
	}

}
