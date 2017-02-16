package Graphics;

import java.awt.*;
import javax.swing.*;


/**
 * class creates splash screen
 * @author Fran
 *
 */
@SuppressWarnings("serial")
public class SplashSplash extends JWindow {

	private int duration;

	/**
	 * constructor
	 * @param duration The length of time the splashscreen is visible for
	 */
	public SplashSplash(int duration) {
		this.duration = duration;
	}

	
	/**
	 * show splash screen
	 * 
	 */
	public void showSplash() {
	 
		Container contents = getContentPane();
		
		//set splash screen dimensions
		int width = 600;
		int height =200;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width-width)/2;
		int y = (screen.height-height)/2;
		setBounds(x,y,width,height);
		
		//fill in splash screen with image
		JLabel label = new JLabel(new ImageIcon("Files/Images/Picture1.png"));
		contents.add(label, BorderLayout.CENTER);
		
		//show splash screen
		setVisible(true);
		
		//close screen after duration up
		try { 
			Thread.sleep(duration); 
		} 
		catch (Exception e) {
	
			
			e.printStackTrace();
		}
		finally
		{
			
			setVisible(false);
		}
	}
}
