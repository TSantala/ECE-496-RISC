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
}
