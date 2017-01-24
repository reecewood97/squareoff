package Graphics;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Audio
{
	public Audio(){
	}
	
	@SuppressWarnings("static-access")
	public void backgroundMusic() {
	
		    
			try {
		       
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files/Audio/back.wav").getAbsoluteFile());
		        Clip music = AudioSystem.getClip();
		        music.open(audioInputStream);
		        music.start();
				music.loop(music.LOOP_CONTINUOUSLY);
		        
		        
		    }
			catch(Exception ex) {
		    
				System.out.println("Error playing sound");
		        ex.printStackTrace();
		    }
		
		
		
	}
	
	public void explosion(){
		
		try {
	      
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files/Audio/EXPLOSION.wav").getAbsoluteFile());
	        Clip explosion = AudioSystem.getClip();
	        explosion.open(audioInputStream);
	        explosion.start();
	         
	    } 
		catch(Exception ex) {
	        
			System.out.println("Error playing sound");
	        ex.printStackTrace();
	    }
		
	}
	
	public void click(){
		
		try {
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files/Audio/CLICK.wav").getAbsoluteFile());
	        Clip click = AudioSystem.getClip();
	        click.open(audioInputStream);
	        click.start();
	        
		}
		catch(Exception ex){
			
			System.out.println("Error playing sound");
	        ex.printStackTrace();
		}
	}
	
  
}