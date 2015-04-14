package units;

import java.util.ArrayList;

import utilities.Coords;

import attacks.Attack;

public abstract class Unit {
	protected String unitTitle;
	
	protected int range;
	protected int visionRadius;
	
	protected int hp;
	protected int armor;
	
	protected double x;
	protected double y;
	protected double direction;
	protected double baseMoveSpeed;
	protected double currentMoveSpeed;
	
	protected Attack attackStrategy;
	protected Coords destination;
	protected ArrayList<Coords> path;
	
	public Unit(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(float tpf) {
		if(destination != null) {
			changeDirectionToward(destination);
			move(tpf);
		}
	}
	
	public void move(float tpf) {
		double h = currentMoveSpeed*tpf;
		double deltaX = h*Math.cos(direction);
		double deltaY = h*Math.sin(direction);
		x += deltaX;
		y += deltaY;
		if(!path.isEmpty() && Math.abs(x - destination.getX()) < .5 && Math.abs(y - destination.getY()) < .5 ) {
			destination = path.remove(path.size() - 1);
		}
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void takeDamage(int damage) {
		hp -= damage;
	}
	
	public void changeDirectionToward(Coords coords) {
		double deltaX = coords.getX() - x;
		double deltaY = coords.getY() - y;
		double theta = Math.atan2(deltaX, deltaY);
		direction = theta;
		System.out.println(theta);
	}
	
	public void setDestination(Coords coords) {
		destination = coords;
	}
	
	public Coords getCoords() {
		return new Coords((int)Math.round(x), (int)Math.round(y));
	}
	
	public void setPath(ArrayList<Coords> path) {
		this.path = path;
	}
}
