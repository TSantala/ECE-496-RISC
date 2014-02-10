package gameElements;

import java.io.Serializable;
import java.util.*;

public class GameMap implements Serializable 
{
	private static final long serialVersionUID = 4L;

	private List<Territory> myTerritories = new ArrayList<Territory>();
	private int myTerritoryCount=0;

	public GameMap(int num)		// for now, just create a 'circle' with each connected to numeric neighbors and last to first
	{
		while(myTerritoryCount<num){
			Territory newT = this.createTerritory();
			myTerritories.add(newT);
		}
		for(int i = 0; i<num-1; i++){
			myTerritories.get(i).addNeighbor(myTerritories.get(i+1));
			myTerritories.get(i+1).addNeighbor(myTerritories.get(i));
		}
		myTerritories.get(num-1).addNeighbor(myTerritories.get(0));
		myTerritories.get(0).addNeighbor(myTerritories.get(num-1));
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
