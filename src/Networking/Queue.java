package Networking;

import java.util.concurrent.ArrayBlockingQueue;

import GameLogic.Move;

public class Queue {
	private ArrayBlockingQueue<Object> q = new ArrayBlockingQueue<Object>(100);	

	public Object take() throws InterruptedException {
		return q.take();
	}	
	public void offer(Object move){
		q.offer(move);
	}
}

