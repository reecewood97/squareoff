package Networking;

import java.util.concurrent.ArrayBlockingQueue;

import GameLogic.Move;

public class MoveQueue {
	private ArrayBlockingQueue<Move> q = new ArrayBlockingQueue<Move>(100);	

	public Move getMove() throws InterruptedException {
		return q.take();
	}	
	public void addMove(Move move){
		q.offer(move);
	}
}


