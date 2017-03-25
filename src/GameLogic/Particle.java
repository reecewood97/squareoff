package GameLogic;

import java.awt.geom.Point2D;

public class Particle extends PhysObject {

	public Particle(Point2D.Double pos, double xvel, double yvel) {
		super(true, pos, 4, 4, false);
		this.setName("Particle");
		this.setXvel(xvel);
		this.setYvel(yvel);
	}

	public Particle(PhysObject other) {
		super(other);
		this.setName(other.getName());
		this.setXvel(other.getXvel());
		this.setYvel(other.getYvel());
	}

}
