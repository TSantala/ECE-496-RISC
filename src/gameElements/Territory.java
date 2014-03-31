package gameElements;

import java.io.Serializable;
import java.util.*;

public class Territory implements Serializable 
{

	private static final long serialVersionUID = 8L;
	
	private Player myOwner;
	private List<Unit> myUnits;
	private List<Territory> myNeighbors;
	private int myID;
	private List<Resource> myResources= new ArrayList<Resource>();
	
	public Territory(int id)
	{
		myID = id;
		myUnits = new ArrayList<Unit>();
		myNeighbors = new ArrayList<Territory>();
		myResources.add(new Food(10));
		myResources.add(new Technology(10));
	}
	
	public void setOwner(Player p){
		myOwner = p;
	}
	
	public Player getOwner(){
		return myOwner;
	}
	
	public void harvestResources(){
		for(Resource r : myResources){
			r.harvest(myOwner);
		}
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
		int initSize = myUnits.size();
		for(Unit u : units){
			myUnits.remove(u);
		}
		int finalSize = myUnits.size();
		System.out.println("Init size: " + initSize + " list size: "+ units.size() + " finalSize: "+finalSize);
		//assert finalSize == (initSize - units.size());
	}
	
	public void removeUnit(Unit u){
		myUnits.remove(u);
	}

	public void addUnits(List<Unit> units) {
		myUnits.addAll(units);
	}
	
	public void addUnit(Unit u){
		myUnits.add(u);
		myOwner.addUnit(u);
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
