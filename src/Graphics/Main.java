package Graphics;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class Main {
	
	public Main() {
	
	}
	
	
	public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
	   
		
		Audio audio = new Audio();
		audio.backgroundMusic();
		SplashSplash splashscreen = new SplashSplash(10000);
		splashscreen.showSplash();
		Screen newui = new Screen();
		newui.run();
		
			
			Audio audio2 = new Audio();
			audio2.explosion();
			audio2.click();
			
		
		
	}
	
}