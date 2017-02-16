package Audio;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * audio class controls music and sounds
 * @author Fran
 *
 */
public class Audio
{
	
	private boolean sound;
	private BackgroundMusic music = new BackgroundMusic();
	
	/**
	 * constructor
	 */
	public Audio(){
		
		this.sound = true;
		
	}
	
	
	/**
	 * get background music
	 * @return music The background music
	 */
	public BackgroundMusic getBackgroundMusic() {
		
		return music;
	}
	
	/**
	 * plays background music
	 */
	public void startBackgroundMusic(){
		
		music.start();
	}
	
	
	/**
	 * stops background music
	 */
	public void endBackgroundMusic(){
		
		music.end();
	}
	
	/**
	 * play explosion sound
	 */
	public void explosion(){
		
		try {
	      
			//create explosion sound
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
					new File("Files/Audio/EXPLOSION.wav").getAbsoluteFile());
	        Clip explosion = AudioSystem.getClip();
	        explosion.open(audioInputStream);
	       
	        //play
	        explosion.start();
	         
	    } 
		catch(Exception ex) {
	        
			System.out.println("Error playing sound");
	        ex.printStackTrace();
	    }
		
	}
	
	/**
	 * play click sound for selections
	 */
	public void click(){
		
		try {
			
			//create click sound
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
					new File("Files/Audio/CLICK.wav").getAbsoluteFile());
	        Clip click = AudioSystem.getClip();
	        click.open(audioInputStream);
	       
	        //play
	        click.start();
	        
		}
		catch(Exception ex){
			
			System.out.println("Error playing sound");
	        ex.printStackTrace();
		}
	}
	
	
	/**
	 * play splash sound for water impacts
	 */
	public void splash(){
		
		try {
			
			//create splash sound
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
					new File("Files/Audio/splash.wav").getAbsoluteFile());
	        Clip splash = AudioSystem.getClip();
	        splash.open(audioInputStream);
	       
	        //play
	        splash.start();
	        
		}
		catch(Exception ex){
			
			System.out.println("Error playing sound");
	        ex.printStackTrace();
		}
	}
	
	/**
	 * turn on sounds
	 */
	public void enableSound(){
		
		sound = true;
	}
	
	
	/**
	 * turn off sounds
	 */
	public void disableSound(){
		sound = false;
	}
}