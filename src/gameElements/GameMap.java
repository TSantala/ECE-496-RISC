package gameElements;

import java.util.*;

public class GameMap
{
	List<Territory> myTerritories;
	
	public GameMap()
	{
		
	}
	
	public Territory getTerritory(int id){
		for(Territory t : myTerritories){
			if(t.getID() == id){
				return t;
			}
		}
		return null;
	}
	
	public boolean hasPath(int from, int to, Player p){
		TerritoryGroup pt = p.getTerritories();
		if(this.getTerritory(from) == null || this.getTerritory(to) == null)
			return false;
		if(!pt.containsTerritory(this.getTerritory(from)) || !pt.containsTerritory(this.getTerritory(to)))
			return false;
		List<Territory> visited = new ArrayList<Territory>();
		Stack<Territory> stack = new Stack<Territory>();
		stack.add(getTerritory(from));
		while(!stack.isEmpty()){
			Territory t = stack.pop();
			if(t.getID() == to)
				return true;
			visited.add(t);
			for(Territory n : t.getNeighbors()){
				if(!visited.contains(n) && pt.containsTerritory(n))
					stack.add(n);
			}
		}
		return false;
	}
	
	public boolean canAttack(int from, int to, Player p){
		if(this.getTerritory(from) == null || this.getTerritory(to) == null)
			return false;
		if(!p.getTerritories().containsTerritory(this.getTerritory(from)))
			return false;
		if(p.getTerritories().containsTerritory(this.getTerritory(to)))
			return false;
		return this.getTerritory(from).getNeighbors().contains(this.getTerritory(to));
	}
	
}
