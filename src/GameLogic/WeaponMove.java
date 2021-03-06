package GameLogic;

import java.awt.geom.Point2D;

public class WeaponMove extends Move {
	
	private Point2D.Double pos;
	private double xvel;
	private double yvel;
	private String wepType;

	/**
	 * A weapon move signifies the player wants to use a weapon
	 * @param wepType The type of weapon to be fired
	 * @param pos The position of the weapon
	 * @param xvel The X velocity of the weapon
	 * @param yvel The Y velocity of the weapon
	 */
	public WeaponMove(String wepType, Point2D.Double pos, double xvel, double yvel) {
		super(0,0,"",false);
		this.setWeapon(true);
		this.pos = pos;
		this.xvel = xvel;
		this.yvel = yvel;
		this.wepType = wepType;
	}
	
	public String wepType() {
		return wepType;
	}
	
	public Point2D.Double getPos() {
		return pos;
	}
	
	public double getXvel() {
		return xvel;
	}
	
	public double getYvel() {
		return yvel;
	}

}
