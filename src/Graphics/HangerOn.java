/*
This is a buddy for the UI - create an instance of this class wherever you make the frame for the game board  
 * * then you just call hangOn2() with the frame as the argument - you get the frame back with listeners nicely  
 * * attached with the methods that David will need.  
 * 
 * *  * Only worry I have is that the frame wont ever be in focus- in which case we may need to make the methods  
 * * more generic to just return whatever kind of component is needed.*/
package Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Networking.Queue;

/**
 * This is a simple listener class meant to seperate the function from the form
 * with the UI
 */
public class HangerOn extends Thread implements KeyListener, MouseListener {

	private Queue q;
	private String name, input, keysPressed;
	private ScreenBoard panel;
	private boolean running, targetInUse;
	private double xr;
	private double yr;

	/**
	 * Constructor
	 * 
	 * @param q
	 *            A blocking queue that's used to hold input ready to be sent to
	 *            the server
	 * @param name
	 *            The client name is used to "sign" the moves they send to the
	 *            server to discern who's turn it is.
	 */
	public HangerOn(Queue q, String name, Double xr, Double yr) {
		this.q = q;
		this.name = name;
		this.targetInUse = false;
		input = "Pressed    " + name;
		keysPressed = "";
		this.xr = xr;
		this.yr = yr;
	}

	/**
	 * Thread run method.
	 */
	public void run() {
		panel.grabFocus();
		running = true;
		try {
			while (running) {
				if (!(panel.hasFocus())) {
					input = "Pressed    " + name;
					keysPressed = "";
				}
				q.offer(input);
				sleep(40);
			}
		} catch (InterruptedException e) {
			
			System.exit(1);
		}
	}

	/**
	 * Kills the thread.
	 */
	public void end() {
		running = false;
	}

	/**
	 * One of two big listeners, this handles keys pressed on the keyboard,
	 * formats them and adds to the queue.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (KeyEvent.getKeyText(e.getKeyCode())) {
		case "W":
			input = "Pressed " + input.substring(8, 9) + "W " + name;
			break;
		case "A":
			input = "Pressed " + "A" + input.substring(9, input.length());
			if (!keysPressed.contains("A")) {
				keysPressed += "A";
			}
			break;
		case "D":
			input = "Pressed " + "D" + input.substring(9, input.length());
			if (!keysPressed.contains("D")) {
				keysPressed += "D";
			}
			break;
		default:
			break;

		}

		panel.grabFocus();
	}

	/**
	 * Used to let the board know when a key is released and we can stop sending
	 * that move.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (KeyEvent.getKeyText(e.getKeyCode())) {
		case "W":
			input = "Pressed " + input.substring(8, 9) + "  " + name;
			break;
		case "A":
			keysPressed = keysPressed.replace("A", "");
			if (keysPressed.contains("D")) {
				input = "Pressed " + "D" + input.substring(9, input.length());
			} else {
				input = "Pressed " + " " + input.substring(9, input.length());
			}
			break;
		case "D":
			keysPressed = keysPressed.replace("D", "");
			if (keysPressed.contains("A")) {
				input = "Pressed " + "A" + input.substring(9, input.length());
			} else {
				input = "Pressed " + " " + input.substring(9, input.length());
			}
			break;
		case "L":

			if (targetInUse) {
				
				targetInUse = false;
			}
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * This is the method for which the class is named, when given a panel we
	 * add our formatted listeners to it.
	 * 
	 * @param panel
	 *            The panel we want to apply listeners to.
	 * @return The panel with the listeners.
	 */
	public ScreenBoard hangOn2(ScreenBoard panel) {
		this.panel = panel;
		panel.addKeyListener(this);
		panel.addMouseListener(this);
		panel.setFocusable(true);
		panel.grabFocus();
		return panel;
	}

	/**
	 * A second listener, deals with formatting and sending mouse input.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 2)
			System.out.println(e);
		if (e.getButton() == 1) {
			int x = (int) (e.getPoint().getX() / xr);
			int y = (int) (((450 * yr) - (e.getPoint().getY()) + 70) / yr);

			String clickedEvent = "Clicked " + x + " " + y + " " + name;
			q.offer(clickedEvent);
			panel.grabFocus();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * A method called in the graphics package, the choice of weapon is worked
	 * out by the UI and sent here to be sent to the server
	 * 
	 * @param type
	 *            The type of weapon that was selected.
	 */
	public void setWep(String type) {
		
		String setWep = "setWep," + type + "," + name;
		q.offer(setWep);
	}

	/**
	 * A method used for updating graphics explosion animations on the server
	 * side.
	 * 
	 * @param size
	 *            The current size of the explosion.
	 */
	public void setExp(String size) {

		String setExp = "setExp " + size;
		q.offer(setExp);
	}

	/**
	 * A method used for updating graphics explosion animations on the server
	 * side.
	 * 
	 * @param bool
	 *            Whether the explosion should be shown or not
	 * @author fran
	 */
	public void setUse(String bool) {

		String setUse = "setUse " + bool;
		q.offer(setUse);
	}

	/**
	 * Called by the weapons selection menu, draws the targetting line for all
	 * players.
	 * 
	 * @param b
	 *            The weapon being set in use
	 * @param b2
	 *            Where the targetting lines should draw to.
	 */
	public void setTargetLine(String b, boolean b2) {

		this.targetInUse = b2;
		String setTar = "setTar " + b;
		q.offer(setTar);
	}

	/**
	 * Grabs the focus of Java
	 */
	public void grab() {
		panel.grabFocus();

	}

}
