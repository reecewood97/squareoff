package GameLogic;

public class TurnMaster extends Thread{
	private Board board;
	
	public TurnMaster(Board board){
		this.board = board;
	}
	public void run(){
		while(true){
			try {
				sleep(20000);
				board.incrementTurn();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
