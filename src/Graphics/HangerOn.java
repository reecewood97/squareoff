/*
This is a buddy for the UI - create an istance of this class wherever you make the frame for the game board  
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
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import GameLogic.UserInput;
import Networking.Queue;

public class HangerOn implements KeyListener,MouseListener { 
	private Queue q;
	
	public HangerOn(Queue q){
		this.q =q;
	}
	
	 //ArrayBlockingQueue<String> q = new ArrayBlockingQueue<String>(100);		
	 
	 ScreenBoard panel;
	 
//	 public String getMoveStr() throws InterruptedException{
//		 String ret = q.take();	
//		 return ret;
//		 }
	 
	 @Override
	 public void keyPressed(KeyEvent e) {
		 String keyString;
		 int keyCode = e.getKeyCode();
		 keyString = "Pressed " +KeyEvent.getKeyText(keyCode);
		 q.add(keyString);
		 panel.grabFocus();
		 } 
	 
	 @Override
	 public void keyReleased(KeyEvent e) { 
	 }
	 
	 @Override
	 public void keyTyped(KeyEvent e) {
	 } 	
	 
	 public ScreenBoard hangOn2(ScreenBoard panel) {
		 this.panel = panel;
		 panel.addKeyListener(this);
		 panel.addMouseListener(this);
		 panel.setFocusable(true);
		 panel.grabFocus();
		 return panel;
	 } 
	 
	 
	 @Override
	 public void mouseClicked(MouseEvent e) {
		 String clickedEvent = "Clicked " + e.getPoint();
		 q.add(clickedEvent);
		 //System.out.println("works!");
		 panel.grabFocus();
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
	 //String releasedEvent = "Released " + e.getPoint();
	 //q.offer(releasedEvent);
	 }
 }
