

package Graphics; import java.awt.BorderLayout; import java.awt.Color; 
import java.awt.Dimension; import java.awt.Graphics; 
import java.awt.Graphics2D; 
import java.awt.RenderingHints; 
import java.awt.Toolkit; 
import javax.swing.JFrame; 
import GameLogic.Board; 
import Networking.Queue; 


@SuppressWarnings("serial") 
public class Screen extends JFrame {
	
	Dimension screenSize;
	double screenheight;
	double screenwidth;
	double framewidth = 800;
	double frameheight = 450;
	
	double widthratio;
	double heightratio;
	
	private ScreenBoard sboard;
	
	public Screen(Board newboard,Queue q){
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println(screenSize.getHeight());
		System.out.println(screenSize.getWidth());
		screenheight = screenSize.getHeight();
		screenwidth = screenSize.getWidth();
		
		heightratio = screenheight/frameheight;
		widthratio = screenwidth/framewidth;
		
		System.out.println(heightratio);
		System.out.println(widthratio);
		
		setBounds(0,0,(int)screenwidth,(int)screenheight);
		Color lightblue = new Color(135,206,250);

		//setUndecorated(true);
		
		
				
		ButtonPanel controls = new ButtonPanel(this,newboard);
		controls.setBackground(lightblue);
		
		setLayout(new BorderLayout());
		
		if(newboard.getWinner() == -1){
			
			this.sboard = new ScreenBoard(newboard, heightratio, widthratio);
			sboard.setBackground(lightblue);
			add(sboard, BorderLayout.CENTER);
			
			HangerOn listeners = new HangerOn(q);
			sboard = listeners.hangOn2(sboard);
		}
		else{
			
			WinnerBoard wboard = new WinnerBoard(newboard.getWinner());
			wboard.setBackground(Color.BLACK);
			controls.setBackground(Color.BLACK);
			add(wboard, BorderLayout.CENTER);
			
		}
		
		add(controls, BorderLayout.NORTH);
		
		setVisible(false);
	}
	
	
	/**
	 * paint method
	 * @param g Graphics
	 */
	@Override     
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	/**
	 * 
	 * show screen method
	 */
	public void setVisible(){
		setVisible(true);
	}     
	
	/**
	 * hide screen method
	 */
	public void setInvisible(){
		setVisible(false);
	}     
	
	/**
	 * repaint screen board
	 */
	public void updateSBoard(){
		sboard.repaint();
	} 

}