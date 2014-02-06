package gameElements;

import java.util.*;

public class Player{
	private String myName;
	private List<Unit> myUnits = new ArrayList<Unit>();
	private List<Territory> myTerritories = new ArrayList<Territory>();

	public Player(String name){
		myName = name;
	}

	public void setName(String s){
		myName = s;
	}

	public String getName(){
		return myName;
	}
	
//	public List<Unit> getUnits(){
//		return myUnits;
//	}
//	
//	public void setUnits (List<Unit> units){
//		myUnits = units;
//	}
//	
//	public void removeUnit(Unit u){
//		myUnits.remove(u);
//	}
	
	public List<Territory> getTerritories(){
		return myTerritories;
	}

	public void addTerritory(Territory t){
		myTerritories.add(t);
	}
	
	public void removeTerritory(Territory t){
		myTerritories.remove(t);
	}
	
	public boolean containsTerritory(Territory t){
		return myTerritories.contains(t);
	}
	
	public Player clone(GameMap clonedMap){
		Player toReturn = new Player(myName);
		List<Unit> clonedUnits = new ArrayList<Unit>();
		for(Unit u : myUnits){
			for(Territory t : clonedMap.getTerritories()){
				if(t.getUnit(u.getID()) != null){
					clonedUnits.add(t.getUnit(u.getID()));
					t.getUnit(u.getID()).setOwner(toReturn);
				}
			}
		}
		for(Territory t : myTerritories){
			toReturn.addTerritory(clonedMap.getTerritory(t.getID()));
		}
		return toReturn;
	}

}
