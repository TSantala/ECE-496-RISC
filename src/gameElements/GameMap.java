package gameElements;

import java.util.*;

public class GameMap
{
	List<Territory> myTerritories;
	
	public GameMap()
	{
		
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
	
}
