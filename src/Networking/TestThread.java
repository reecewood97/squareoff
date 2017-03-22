package Networking;

public class TestThread extends Thread {

	public String s, t;
	
	public TestThread(String s, String t) {
		this.s = s;
		this.t = t;
	}
	
	public void run() {
		try {
			sleep(1000);
		}
		catch(InterruptedException e) {
			
		}
	}
}
