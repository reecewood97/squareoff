package Graphics;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Audio
{
	
	private boolean sound;
	private BackgroundMusic music = new BackgroundMusic();
	
	public Audio(){
		
		this.sound = true;
		
	}
	
	public BackgroundMusic getBackgroundMusic() {
		
		return music;
	}
	
	public void startBackgroundMusic(){
		
		//music.start();
	}
	
	public void endBackgroundMusic(){
		
		music.end();
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
	
	public void enableSound(){
		
		sound = true;
	}
	
	public void disableSound(){
		sound = false;
	}
	
	
  
}