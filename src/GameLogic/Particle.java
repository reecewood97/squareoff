package GameLogic;

import java.awt.geom.Point2D;

public class Particle extends PhysObject {

	/**
	 * Creates a particle with the given attributes
	 * @param pos The position of the particle
	 * @param xvel The X velocity of the particle
	 * @param yvel The Y velocity of the particle
	 */
	public Particle(Point2D.Double pos, double xvel, double yvel) {
		super(true, pos, 4, 4, false);
		this.setName("Particle");
		this.setXvel(xvel);
		this.setYvel(yvel);
	}

	/**
	 * Creates a shallow copy of a Particle
	 * @param other The particle to be copied
	 */
	public Particle(PhysObject other) {
		super(other);
		this.setName(other.getName());
		this.setXvel(other.getXvel());
		this.setYvel(other.getYvel());
	}

}
