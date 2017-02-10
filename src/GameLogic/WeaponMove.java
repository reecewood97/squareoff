package gameLogic;

import java.awt.geom.Point2D;

public class WeaponMove extends Move {
	
	private Point2D.Double pos;
	private double xvel;
	private double yvel;
	private String wepType;

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
	
	public double yvel() {
		return yvel;
	}

}
