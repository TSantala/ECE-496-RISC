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
	
	public boolean hasPath(int from, int to){
		if(getTerritory(from) == null || getTerritory(to) == null)
			return false;
		List<Territory> visited = new ArrayList<Territory>();
		Stack<Territory> stack = new Stack<Territory>();
		
		
		
		return false;
	}
	
}
