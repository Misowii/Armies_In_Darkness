package maps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

import units.Unit;
import utilities.Coords;



public class PathingMap {
	
	private class Node implements Comparable<Node> {
		private Node parent;
		private Coords coords;
		private int cost;
		
		public Node(Coords coords, int heuristicCost, Node parent) {
			this.coords = coords;
			this.parent = parent;
			if(parent != null) {
				cost = parent.cost + heuristicCost;
			}
			else cost = heuristicCost;
		}

		@Override
		public int compareTo(Node other) {
			return this.cost - other.cost;
		}
		
		public int getX() {
			return coords.getX();
		}
		
		public int getY() {
			return coords.getY();
		}

		public Coords getCoords() {
			return coords;
		}
	}
	
	private static final String OPEN = "OPEN";
	//private static final String WALL = "WALL";
	private static final String UNIT = "UNIT";
	//private static final String BUILDING = "BUILDING";
	private static final String BOUNDRY = "BOUNDRY";
	private String[][] grid;
	
	public PathingMap(int width, int height) {
		grid = new String[width][height];
		for(int x = 0; x < grid.length; ++x) {
			for(int y = 0; y < grid[x].length; ++y) {
				if(x == 0 || y == 0 || x == grid.length - 1  || y == grid[x].length - 1) {
					grid[x][y] = BOUNDRY;
				}
				else grid[x][y] = OPEN;
			}
		}
	}
	
	public void clearUnits() {
		for(int x = 0; x < grid.length; ++x) {
			for(int y = 0; y < grid[x].length; ++y) {
				if(grid[x][y] == UNIT) {
					grid[x][y] = OPEN;
				}
			}
		}
	}
	
	public void AddUnits(ArrayList<Unit> units) {
		for(Unit unit : units) {
			int x = (int)Math.round(unit.getX());
			int y = (int)Math.round(unit.getY()); 
			grid[x][y] = UNIT;
		}
	}
	
	public ArrayList<Coords> aStar(Coords start, Coords target) {
		Node firstNode = new Node(start, straightLineDist(start, target), null);
		HashSet<Coords> seen = new HashSet<Coords>();
		PriorityQueue<Node> frontier = new PriorityQueue<Node>();
		frontier.add(firstNode);
		
		while(!frontier.isEmpty()) {
			Node currentNode = frontier.poll();
			if(currentNode.getX() == target.getX() && currentNode.getY() == target.getY()) {
				return reconstructPath(currentNode);
			}
			seen.add(currentNode.getCoords());
			for(int xDiff = -1; xDiff <= 1; ++xDiff) {
				for(int yDiff = -1; yDiff <= 1; ++yDiff) {
					if(xDiff == 0 && yDiff == 0) {
						continue;
					}
					Coords nextCoords = new Coords(currentNode.getX() + xDiff, currentNode.getY() + yDiff);
					Node nextNode = new Node(nextCoords, straightLineDist(nextCoords,target), currentNode);
					for(Node n : frontier) {
						if(n.equals(nextNode) && nextNode.compareTo(n) < 0) {
							frontier.remove(n);
							break;
						}
					}
					if(grid[nextNode.getX()][nextNode.getY()] == OPEN) {
						frontier.add(nextNode);
					}
				}
			}
		}
		
		return null;
	}
	
	public ArrayList<Coords> reconstructPath(Node node) {
		ArrayList<Coords> path = new ArrayList<Coords>();
		while(node != null) {
			path.add(node.getCoords());
			node = node.parent;
		}
		return path;
	}
	
	public int straightLineDist(Coords start, Coords end) {
		int xDistSquared = (start.getX()-end.getX())*(start.getX()-end.getX());
		int yDistSquared = (start.getY()-end.getY())*(start.getY()-end.getY());
		return (int)Math.sqrt(xDistSquared + yDistSquared);
	}
	
	@Override
	public String toString() {
		String outString = "";
		for(int x = 0; x < grid.length; ++x) {
			for(int y = 0; y < grid[x].length; ++y) {
				outString += grid[x][y].charAt(0);
			}
			outString += '\n';
		}
		return outString;
	}
}
