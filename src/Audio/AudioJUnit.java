package Audio;

import org.junit.Test;
import static org.junit.Assert.*;

public class AudioJUnit {

	@Test
	public void test(){
		
		Audio audio = new Audio();
		assertTrue(audio.getSound());
		audio.explosion();
		audio.splash();
		audio.click();
		
		audio.newMusic();
		audio.endMusic();
		
	}
}
