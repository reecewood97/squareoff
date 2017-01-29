package Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ButtonPanel extends JPanel{

	public ButtonPanel(){
		
		super();
			
		//display controls
		JButton help = new JButton("Help");
		help.addActionListener(e -> System.exit(0));
		
		//back to main menu
		JButton exit = new JButton("Exit");
		exit.addActionListener(e -> System.exit(0));
		
		//toggle sound
		JButton sound = new JButton("Sound");
		sound.addActionListener(e -> System.exit(0));
		
		add(help);
		add(sound);
		add(exit);
		
	}
}
