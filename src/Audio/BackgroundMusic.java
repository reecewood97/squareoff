<<<<<<< HEAD:src/Graphics/BackgroundMusic.java
package graphics;
=======
package Audio;
>>>>>>> ad87e017eaa9288847a307119dbb434aa6e55a06:src/Audio/BackgroundMusic.java

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BackgroundMusic extends Thread{

	private boolean run = true;
	
	
	public void end(){
	
		System.out.println("goodbye");
		run = false;
	}
	
	public void run(){
		
		while(run){
			
			try{
				
			
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
						new File("Files/Audio/fab.wav").getAbsoluteFile());
				Clip music = AudioSystem.getClip();
				music.open(audioInputStream);
				
				music.start();
				Thread.sleep(1400);
				music.stop();
			}	
			catch(Exception ex) {
			    
				System.out.println("Error playing sound");
				ex.printStackTrace();
				System.out.println(ex.getMessage());
			}
			
			
		}
				
	}
	
}
