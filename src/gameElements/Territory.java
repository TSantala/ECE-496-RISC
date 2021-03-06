package gameElements;

import java.io.Serializable;
import java.util.*;

public class Territory implements Serializable, GameConstants 
{

	private static final long serialVersionUID = 8L;

	private Player myOwner;
	private List<Unit> myUnits = new ArrayList<Unit>();
	protected List<Territory> myNeighbors;
	private String myID;
	private List<Resource> myResources= new ArrayList<Resource>();
	private int interceptors = 0;

	public Territory(String id)
	{
		myID = id;
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

	public void removeNeighbor(Territory neighbor){
		if (myNeighbors.contains(neighbor)){
			myNeighbors.remove(neighbor);
		}
		else{
			System.out.println("could not remove territory because it was not found");
		}
	}

	public List<Territory> getNeighbors(){
		return myNeighbors;
	}

	public void setUnits(List<Unit> units)
	{
		myUnits = units;
	}

	public List<Unit> getUnits(){
		Collections.sort(myUnits);
		return myUnits;
	}

	public String getID() {
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
		myOwner.addUnit(u);
	}

	public Unit getUnit(int id){
		for(Unit u : myUnits){
			if(u.getID() == id)
				return u;
		}
		return null;
	}

	public boolean isAdjacentTo (Player p){ 
		for (Territory t : p.getTerritories()){
			if (myNeighbors.contains(t))
				return true;
		}
		return false;
	}

	public boolean hasSpy(Player p){
		for (Unit u : myUnits){
			if (u.isSpy()){
				if(u.getOwner().onTeam(p))
					return true;
			}
		}
		return false;
	}

	public String getUnitInfo(){
		if(myUnits.size()==0) return "  No units!\n";
		String toReturn = "";
		List<Unit> sortedUnits = this.getUnits();
		int spies = 0;
		for(Unit u : sortedUnits){
			if(u.isSpy()){
				spies++;
			}
		}
		if(spies>0)
			toReturn += "  ("+spies+") Spy\n";
		int[] levels = new int[6];
		for(Unit u : sortedUnits){
			if(!u.isSpy()){
				levels[u.getTechLevel()]++;
			}
		}
		for(int i = 0; i < levels.length; i++){
			if(levels[i] > 0)
				toReturn+="  ("+levels[i]+") "+UNIT_TECH_TREE.getUnitType(i)+"\n";
		}
		return toReturn;
	}

	public boolean hasUnitHere(Player p){
		for(Unit u : myUnits){
			if(u.getOwner().getName().equals(p.getName()))
				return true;
		}
		return false;
	}

	public int hasInterceptors() {
		return interceptors;
	}

	public void placeInterceptor(){
		interceptors++;
	}

	public void removeInterceptor() {
		interceptors--;
	}

}
