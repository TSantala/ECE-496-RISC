package gameElements;

import java.util.*;

/* 
 * This class if for initialization phase when each player is either assigned
 * or picks a list of territories to be their starting area.  This is a group.  
 */
public class TerritoryGroup 
{
	private List<Territory> myTerritoryList;
	private Player myPlayer;
	
	public TerritoryGroup(){
		myTerritoryList = new ArrayList<Territory>();
	}
	
	public Player getOwner(){
		return myPlayer;
	}
	
	public void addTerritory(Territory t){
		if(!myTerritoryList.contains(t))
			myTerritoryList.add(t);
	}
	
	public boolean containsTerritory(Territory t){
		return myTerritoryList.contains(t);
	}
	
	public void removeTerritory(Territory t) {
		if(myTerritoryList.contains(t))
			myTerritoryList.remove(t);
	}
}
