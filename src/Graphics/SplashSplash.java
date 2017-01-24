package Graphics;

import java.awt.*;
import javax.swing.*;

public class SplashSplash extends JWindow {

	private int duration;

	public SplashSplash(int d) {
		duration = d;
	}

	
	public void showSplash() {
	 
		Container contents = getContentPane();
		
		int width = 600;
		int height =200;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width-width)/2;
		int y = (screen.height-height)/2;
		setBounds(x,y,width,height);
	
		JLabel label = new JLabel(new ImageIcon("Images/Picture1.png"));
		contents.add(label, BorderLayout.CENTER);
		
		setVisible(true);

		try { 
			Thread.sleep(duration); 
		} 
		catch (Exception e) {}
	
			setVisible(false);
		}
		
}
