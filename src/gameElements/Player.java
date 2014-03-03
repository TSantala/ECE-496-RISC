package gameElements;

import java.io.Serializable;
import java.util.*;

import server.ServerPlayer;

public class Player implements Serializable {

	private static final long serialVersionUID = 7L;
	private List<Unit> myUnits = new ArrayList<Unit>();
	private List<Territory> myTerritories = new ArrayList<Territory>();
	private ServerPlayer myPlayer;
	
	public Player(ServerPlayer p){
		myPlayer = p;
	}
	
	public ServerPlayer getPlayer(){
		return myPlayer;
	}

	public String getName(){
		return myPlayer.getName();
	}
	
	public List<Unit> getUnits(){
		return myUnits;
	}
	
	public void addUnit (Unit u){
		myUnits.add(u);
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
