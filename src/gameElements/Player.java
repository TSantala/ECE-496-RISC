package gameElements;

import java.io.Serializable;
import java.util.*;

import server.ServerPlayer;

public class Player implements Serializable, GameConstants {

	private static final long serialVersionUID = 7L;
	
	private List<Unit> myUnits = new ArrayList<Unit>();
	private List<Territory> myTerritories = new ArrayList<Territory>();
	private ServerPlayer myPlayer;
	
	private int myTechLevel=0;
	
	private Food myFood = new Food(40);
	private Technology myTech = new Technology(40);
	
	public Player(ServerPlayer p){
		myPlayer = p;
	}
	
	public ServerPlayer getPlayer(){
		return myPlayer;
	}
	
	public String getName(){
		return myPlayer.getName();
	}
	
	public int getTechLevel(){
		return myTechLevel;
	}
	
	public int getFoodAmount(){
		return myFood.getAmount();
	}
	
	public int getTechAmount(){
		return myTech.getAmount();
	}
	
	public List<Unit> getUnits(){
		Collections.sort(myUnits);
		return myUnits;
	}
	
	public boolean feedUnit(){
		if(myFood.getAmount()>0){
			myFood.increment(-1);
			return true;
		}
		return false;
	}
	
	public boolean upgradePlayer(){
		if(myTech.getAmount()>PLAYER_TECH_TREE.getCost(myTechLevel)){
			myTech.increment(-PLAYER_TECH_TREE.getCost(myTechLevel));
			myTechLevel++;
			System.out.println("PLAYER UPGRADE!!");
			return true;
		}
		System.out.println("Player couldn't afford to level up!");
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
	
	public boolean equals(Player p)
	{
	     return this.getName().equals(p.getName());
	}

}
