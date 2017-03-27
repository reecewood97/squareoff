package Audio;

import java.io.File;
import java.io.Serializable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * the thread for playing background music in the game
 * @author Fran
 *
 */
public class BackgroundMusic extends Thread implements Serializable{

	private static final long serialVersionUID = 8996454284431461725L;
	private boolean run = true;
	private AudioInputStream audioInputStream;
	private Clip music;
	
	/**
	 * constructor creates audio input stream
	 */
	public BackgroundMusic(){
		
		try{
			
			audioInputStream = AudioSystem.getAudioInputStream(
					new File("Files/Audio/fab.wav").getAbsoluteFile());
			music = AudioSystem.getClip();
			music.open(audioInputStream);

		
		}	
		catch(Exception ex) {

		}
	}	
	
	/**
	 * end method stops the music playing
	 */
	public void end(){
	
		run = false;
		music.stop();
	}
	
	
	/**
	 * run method plays the music
	 */
	@SuppressWarnings("static-access")
	public void run(){
				
			music.loop(music.LOOP_CONTINUOUSLY);
						
	}
	
}
