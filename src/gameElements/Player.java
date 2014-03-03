package gameElements;

import java.io.Serializable;
import java.util.*;

import server.ServerPlayer;

public class Player implements Serializable, GameConstants {

	private static final long serialVersionUID = 7L;
	
	private Collection<Unit> myUnits = new PriorityQueue<Unit>();
	private List<Territory> myTerritories = new ArrayList<Territory>();
	private ServerPlayer myPlayer;
	
	private int myTechLevel=0;
	
	private Food myFood = new Food(INIT_RESOURCES);
	private Technology myTech = new Technology(INIT_RESOURCES);
	
	public Player(ServerPlayer p){
		myPlayer = p;
	}
	
	public ServerPlayer getPlayer(){
		return myPlayer;
	}
	
	public String getName(){
		return myPlayer.getName();
	}
	
	public Collection<Unit> getUnits(){
		return myUnits;
	}
	
	public boolean feedUnit(){
		if(myFood.getAmount()>0){
			myFood.increment(-1);
			return true;
		}
		return false;
	}
	
	public Collection<Unit> getReverseOrderUnits(){
		Stack<Unit> reversed = new Stack<Unit>();
		for(Unit u : myUnits){
			reversed.add(u);
		}
		return reversed;
	}
	
	public void addUnit (Unit u){
		myUnits.add(u);
	}
	
	public void addUnits(List<Unit> units) {
		myUnits.addAll(units);
	}
	
	public void removeUnit(Unit u){
		myUnits.remove(u);
	}
	
	public List<Territory> getTerritories(){
		return myTerritories;
	}

	public void addTerritory(Territory t){
		myTerritories.add(t);
		t.setOwner(this);
	}
	
	public void removeTerritory(Territory t){
		myTerritories.remove(t);
	}
	
	public boolean containsTerritory(Territory t){
		return myTerritories.contains(t);
	}

	public void adjustResource(Food r) {
		myFood.increment(r.getAmount());
	}
	
	public void adjustResource(Technology t){
		myTech.increment(t.getAmount());
	}
	
	public int getNumToFeed(){
		return myUnits.size();
	}
	
	public Player clone(GameMap clonedMap){
		Player toReturn = new Player(myPlayer);
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
			Territory clonedT = clonedMap.getTerritory(t.getID());
			toReturn.addTerritory(clonedT);
			clonedT.setOwner(toReturn);
		}
		return toReturn;
	}

}
