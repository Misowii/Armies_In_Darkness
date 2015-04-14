package main;

import java.util.ArrayList;

import units.Marine;
import units.Unit;
import utilities.Coords;

import maps.PathingMap;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		PathingMap map = new PathingMap(10,10);
		ArrayList<Unit> units = new ArrayList<Unit>();
		Marine marine = new Marine(5,5);
		marine.setDestination(new Coords(2,2));
		marine.setPath(map.aStar(marine.getCoords(), new Coords(8,3)));
		units.add(marine);
		while(true) {
			for(Unit unit : units) {
				unit.update(.1f);
			}
			map.clearUnits();
			map.AddUnits(units);
			System.out.println(map.toString());
			Thread.sleep(1000);
		}
	}
}
