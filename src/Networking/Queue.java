package Networking;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * A class containing an object queue.
 * @author David
 *
 */
public class Queue {
	private ArrayBlockingQueue<Object> q = new ArrayBlockingQueue<Object>(100);	

	/**
	 * Takes an object from the head of the queue.
	 * @return The object at the head of the queue.
	 */
	public Object take() {
		try {
			return q.take();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		//Not really necessary, but the compiler complained otherwise.
		return null;
	}	
	
	/**
	 * Add an object to the back of the queue.
	 * @param obj The object to be added.
	 */
	public void offer(Object obj){
		q.offer(obj);
	}
	
	/**
	 * Clear the queue.
	 */
	public void clear() {
		q.clear();
	}
}


