package Graphics;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BackgroundMusic extends Thread{

	public void run(){
		
		try {
		       
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files/Audio/back.wav").getAbsoluteFile());
	        Clip music = AudioSystem.getClip();
	        music.open(audioInputStream);
	        music.start();
	 
	        music.loop(Clip.LOOP_CONTINUOUSLY);
	        
	        
	    }
		catch(Exception ex) {
	    
			System.out.println("Error playing sound");
	        ex.printStackTrace();
	        System.out.println(ex.getMessage());
	    }
	
		
	}
}
