package buildings;

import utilities.Coords;

public abstract class Building {
	//considering refactoring Unit and Building to share a superclass
	protected String buildingTitle;
	
	protected int visionRadius;
	
	protected Coords coords;
	protected int hp;
	protected int armor;

}
