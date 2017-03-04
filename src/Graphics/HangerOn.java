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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import GameLogic.UserInput;
import Networking.Queue;
/**
 * This is a simple listener class meant to seperate the function from the form with the UI
 */
public class HangerOn implements KeyListener,MouseListener { 
	private Queue q;
	private String name;

	
	/**
	 * Constructor
	 * @param q A blocking queue that's used to hold input ready to be sent to the server
	 * @param name The client name is used to "sign" the moves they send to the server to discern whos turn it is.
	 */
	public HangerOn(Queue q,String name){
		this.q =q;
		this.name = name;
	
	}	
	 
	 ScreenBoard panel;
	 
	 /**
	  * One of two big listeners, this handles keys pressed on the keyboard, formats them and adds to the queue.
	  */
	 @Override
	 public void keyPressed(KeyEvent e) {
		 String keyString;
		 int keyCode = e.getKeyCode();
		 keyString = "Pressed " +KeyEvent.getKeyText(keyCode) + " " + name;
		 //System.out.println(keyString);
		 q.offer(keyString);
		 panel.grabFocus();
		 } 
	 
	 @Override
	 public void keyReleased(KeyEvent e) { 
	 }
	 
	 @Override
	 public void keyTyped(KeyEvent e) {
	 } 	
	 
	 /**
	  * This is the method for which the class is named, when given a panel we add our formatted listeners to it.
	  * @param panel The panel we want to apply listeners to.
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
		 if(e.getButton() == 1){
			 String clickedEvent = "Clicked " + e.getPoint();
			 q.offer(clickedEvent);
			 //System.out.println("works!");
			 panel.grabFocus();
		 }
		 //panel.grabFocus();
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
	 
	 /**
	  * A method called in the graphics package, the choice of weapon is worked out by the UI and sent here to be sent to the server
	  * @param type The type of weapon that was selected.
	  */
	 public void setWep(String type){
		 String setWep = "setWep "+type;
		 System.out.println(type);
		 q.offer(setWep);
	 }
	 
	 /**
	  * A method used for updating graphics animations on the server side.
	  * @param size The current size of the explosion.
	  */
	 public void setExp(String size){
		 
		 String setExp = "setExp " + size;
		 q.offer(setExp);
	 }
	 
	 public void setUse(String bool){
		 
		 String setUse = "setUse " + bool;
		 q.offer(setUse);
	 }
	 
	 public void setTargetLine(String b){
		 
		 String setTar = "setTar " + b;
		 q.offer(setTar);
	 }
	 
	
 }
