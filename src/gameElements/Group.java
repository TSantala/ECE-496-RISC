package gameElements;

import java.util.*;

/* 
 * This class if for initialization phase when each player is either assigned
 * or picks a list of territories to be their starting area.  This is a group.  
 */
public class Group 
{
	private List<Territory> territoryList;
	private Player owner;
	public Group()
	{
		territoryList = new ArrayList<Territory>();
	}
	public Player getOwner()
	{
		return owner;
	}
	public void addTerritory(Territory t)
	{
		territoryList.add(t);
	}
}
