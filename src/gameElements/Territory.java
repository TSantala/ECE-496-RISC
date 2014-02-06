package gameElements;

import java.util.*;

public class Territory 
{
	private List<Unit> myUnits;
	private List<Territory> myNeighbors;
	private int myID;
	
	public Territory(int id)
	{
		myID = id;
		myUnits = new ArrayList<Unit>();
		myNeighbors = new ArrayList<Territory>();
	}
	
	public void setNeighbors(List<Territory> nghbrs)
	{
		myNeighbors = nghbrs;
	}
	
	public void addNeighbor(Territory neighbor){
		myNeighbors.add(neighbor);
	}
	
	public List<Territory> getNeighbors(){
		return myNeighbors;
	}
	
	public void setUnits(List<Unit> units)
	{
		myUnits = units;
	}
	
	public List<Unit> getUnits(){
		return myUnits;
	}
	
	public int getID() {
		return myID;
	}

	public void removeUnits(List<Unit> units) {
		for(Unit u : units){
			myUnits.remove(u);
		}
	}
	
	public void removeUnit(Unit u){
		myUnits.remove(u);
	}

	public void addUnits(List<Unit> units) {
		myUnits.addAll(units);
	}
	
	public void addUnit(Unit u){
		myUnits.add(u);
	}
	
	public Unit getUnit(int id){
		for(Unit u : myUnits){
			if(u.getID() == id)
				return u;
		}
		return null;
	}
	
	public Territory clone(){
		List<Unit> newUnits = new ArrayList<Unit>();
		for(Unit u : myUnits){
			newUnits.add(u.clone());
		}
		Territory toReturn = new Territory(myID);
		toReturn.addUnits(newUnits);
		return toReturn;
	}
}
