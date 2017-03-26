package Audio;

import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * audio class controls music and sounds
 * @author Fran
 *
 */
public class Audio implements Serializable
{
	private static final long serialVersionUID = 3690097175800550800L;
	private boolean sound;
	private BackgroundMusic music; 
	
	/**
	 * constructor
	 */
	public Audio(){
		
		this.sound = true;
		music = new BackgroundMusic();
	}
	
	/**
	 * create new instance of music
	 */
	public void newMusic(){
		
		music = new BackgroundMusic();
	}
	
	
	/**
	 * returns background music thread
	 * @return background music
	 */
	public BackgroundMusic getBackgroundMusic(){
		return music;
	}
	
	
	/**
	 * end background music
	 */
	public void endMusic(){
		music.end();
	}
	
	/**
	 * play explosion sound
	 */
	public void explosion(){
		
		if(sound){
			try {
		      
				//create explosion sound
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
						new File("Files/Audio/EXPLOSION.wav").getAbsoluteFile());
		        Clip explosion = AudioSystem.getClip();
		        explosion.open(audioInputStream);
		       
		        FloatControl gainControl = 
		        	    (FloatControl) explosion.getControl(FloatControl.Type.MASTER_GAIN);
		        	gainControl.setValue(-12.0f); // Reduce volume by 12 decibels.
		        
		        //play
		        explosion.start();
		         
		    } 
			catch(Exception ex) {
		        
				System.err.println("Error playing sound");
		        
		    }
		}
	}
	
	/**
	 * play click sound for selections
	 */
	public void click(){
		
		if(sound){
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
				
				System.err.println("Error playing sound");
		     
			}
		}
	}
	
	
	/**
	 * play splash sound for water impacts
	 */
	public void splash(){
		
		if(sound){
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
				
				System.err.println("Error playing sound");
		       
			}
		}
	}
	
	
}