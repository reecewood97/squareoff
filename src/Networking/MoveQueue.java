package Networking;

import java.util.concurrent.ArrayBlockingQueue;

import GameLogic.Move;

public class MoveQueue {
	private ArrayBlockingQueue<Move> q;
	
	public MoveQueue() {
		q = new ArrayBlockingQueue<Move>(100);	
	}

	public Move take() throws InterruptedException {
		return q.take();
	}	
	public void offer(Move move){
		q.offer(move);
	}
}


