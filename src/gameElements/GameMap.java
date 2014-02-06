package gameElements;

import java.util.*;

public class GameMap
{
	private List<Territory> myTerritories;
	
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
