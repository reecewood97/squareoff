package Graphics;

import GameLogic.Board;

public class Updater extends Thread{
	private Screen newUI;
	
	public Updater(Screen newUI){
		this.newUI = newUI;
	}
	
	public void run(){
		while(true){
			newUI.updateSBoard();
			//System.out.println("The board is being updated in this side thread");
			try {
				sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
