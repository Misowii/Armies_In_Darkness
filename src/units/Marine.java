package units;

import utilities.Coords;
import attacks.MachineGun;

public class Marine extends Unit {
	private static final String MARINE = "Marine";

	public Marine(int x, int y) {
		super(x, y);
		hp = 100;
		armor = 1;
		baseMoveSpeed = 5;
		currentMoveSpeed = baseMoveSpeed;
		unitTitle = MARINE;
		attackStrategy = new MachineGun();
		direction = 0;
		
	}

}
