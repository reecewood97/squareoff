package Audio;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class BackgroundMusic extends Thread implements Serializable{

	private boolean run = true;
	private AudioInputStream audioInputStream;
	private Clip music;
	
	
	public BackgroundMusic(){
		
		try{
			
			
			audioInputStream = AudioSystem.getAudioInputStream(
					new File("Files/Audio/fab.wav").getAbsoluteFile());
			music = AudioSystem.getClip();
			music.open(audioInputStream);
			System.out.println("1");
		
		}	
		catch(Exception ex) {
		    
			System.out.println("Error playing sound");
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			System.out.println("2");
		}
	}	
	
	public void end(){
	
		System.out.println("3");
		run = false;
	}
	
	public void run(){
		
		System.out.println("4");
		
			while(run){	
				//System.out.println("5");
				
				
				music.start();
				music.loop(music.LOOP_CONTINUOUSLY);
				
				/*
				try {
					System.out.println("6");
					Thread.sleep(1400);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("7");
				}
				
				music.stop();
				System.out.println("8");
				*/
			}
		
				
	}
	
}
