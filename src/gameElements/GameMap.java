package gameElements;

import java.util.*;

public class GameMap
{
	private List<Territory> myTerritories = new ArrayList<Territory>();
	private int myTerritoryCount=0;
	
	public GameMap()
	{
		Territory t1 = this.createTerritory();
		Territory t2 = this.createTerritory();
		Territory t3 = this.createTerritory();
		Territory t4 = this.createTerritory();
		
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
//		myTerritories.add(new Territory(5));
//		myTerritories.add(new Territory(6));
//		myTerritories.add(new Territory(7));
//		myTerritories.add(new Territory(8));
//		myTerritories.add(new Territory(9));
//		myTerritories.add(new Territory(10));
	}
	
	public Territory createTerritory(){
		myTerritoryCount++;
		return new Territory(myTerritoryCount);
	}
		
	public GameMap(List<Territory> map){
		myTerritories = map;
	}
	
	public boolean hasPath(Territory from, Territory to, Player p){
		List<Territory> pt = p.getTerritories();
		if(!pt.contains(from) || !pt.contains(to))
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
				if(!visited.contains(n) && pt.contains(n))
					stack.add(n);
			}
		}
		return false;
	}
	
	public boolean canAttack(Territory from, Territory to, Player p){
		if(!p.containsTerritory(from))
			return false;
		if(p.containsTerritory(to))
			return false;
		return from.getNeighbors().contains(to);
	}
	
	public Territory getTerritory(int id){
		for(Territory t : myTerritories){
			if(t.getID() == id)
				return t;
		}
		System.out.println("Get territory returned null!! In GameMap");
		return null;
	}
	
	public List<Territory> getTerritories(){
		return myTerritories;
	}
	
	public GameMap clone(){
		List<Territory> clonedTerritories = new ArrayList<Territory>();
		for(Territory t : myTerritories){
			clonedTerritories.add(t.clone());
		}
		GameMap toReturn = new GameMap(clonedTerritories);
		for(Territory t : clonedTerritories){
			Territory original = this.getTerritory(t.getID());
			for(Territory n : original.getNeighbors())
				t.addNeighbor(toReturn.getTerritory(n.getID()));
		}
		return toReturn;
	}
	
}
