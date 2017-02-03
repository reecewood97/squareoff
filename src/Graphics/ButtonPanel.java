package Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel{

	public ButtonPanel() {
		
		super();
		
		/*
		ImageIcon image = new ImageIcon("Files/Images/Picture1.png");
		JButton help = new JButton(image);
		help.addActionListener(e -> System.exit(0));
		help.setBorderPainted(false); 
        help.setContentAreaFilled(false); 
        help.setFocusPainted(false); 
        help.setOpaque(false);
		
		//back to main menu
        ImageIcon image2 = new ImageIcon("Files/Images/x.png");

		
		JButton exit = new JButton(image2);
		exit.addActionListener(e -> System.exit(0));
		exit.addActionListener(e -> System.exit(0));
		exit.setBorderPainted(false); 
        exit.setContentAreaFilled(false); 
        exit.setFocusPainted(false); 
        exit.setOpaque(false);
		
		*/
		//toggle sound
		JButton sound = new JButton("Sound");
		sound.addActionListener(e -> System.exit(0));
		
		//add(help);
		add(sound);
		//add(exit);
		
	}
}
