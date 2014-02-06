package gameElements;

import java.util.*;

public class GameMap
{
	private List<Territory> myTerritories = new ArrayList<Territory>();
	
	public GameMap()
	{
		Territory t1 = new Territory(1);
		Territory t2 = new Territory(2);
		Territory t3 = new Territory(3);
		Territory t4 = new Territory(4);
		
		t1.addNeighbor(t4);
		t1.addNeighbor(t2);
		t2.addNeighbor(t1);
		t2.addNeighbor(t3);
		t3.addNeighbor(t2);
		t3.addNeighbor(t4);
		t4.addNeighbor(t3);
		t4.addNeighbor(t1);
		
		myTerritories.add(t1);
		myTerritories.add(t2);
		myTerritories.add(t3);
		myTerritories.add(t4);
		myTerritories.add(new Territory(5));
		myTerritories.add(new Territory(6));
		myTerritories.add(new Territory(7));
		myTerritories.add(new Territory(8));
		myTerritories.add(new Territory(9));
		myTerritories.add(new Territory(10));
		
	}
	
	public boolean hasPath(Territory from, Territory to, Player p){
		TerritoryGroup pt = p.getTerritories();
		if(!pt.containsTerritory(from) || !pt.containsTerritory(to))
			return false;
		List<Territory> visited = new ArrayList<Territory>();
		Stack<Territory> stack = new Stack<Territory>();
		stack.add(from);
		while(!stack.isEmpty()){
			Territory t = stack.pop();
			if(t.getID() == to.getID())
				return true;
			visited.add(t);
			for(Territory n : t.getNeighbors()){
				if(!visited.contains(n) && pt.containsTerritory(n))
					stack.add(n);
			}
		}
		return false;
	}
	
	public boolean canAttack(Territory from, Territory to, Player p){
		if(!p.getTerritories().containsTerritory(from))
			return false;
		if(p.getTerritories().containsTerritory(to))
			return false;
		return from.getNeighbors().contains(to);
	}
	
	public List<Territory> getTerritories(){
		return myTerritories;
	}
	
	public Territory getTerritory(int id){
		for(Territory t : myTerritories){
			if(t.getID() == id)
				return t;
		}
		System.out.println("Get territory returned null!! In GameMap");
		return null;
	}
	
}
