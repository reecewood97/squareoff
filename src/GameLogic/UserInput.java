package GameLogic;

import java.util.concurrent.ArrayBlockingQueue;

public class UserInput {
	private ArrayBlockingQueue<String> q = new ArrayBlockingQueue<String>(100);	
	
	public UserInput() {
	}

	/**
	 * Gets the inputs of a user per game tick (25 ticks per second when writing this) as a string.
	 * @return The user inputs.
	 * @throws InterruptedException 
	 */
	public String getInputStrings() throws InterruptedException {
		return q.take();
	}	
	public void addInput(String input){
		q.offer(input);
	}
}


