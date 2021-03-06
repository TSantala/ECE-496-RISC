package gameElements;

import java.io.Serializable;
import java.util.*;

import server.ServerPlayer;

public class Player implements Serializable, GameConstants {

	private static final long serialVersionUID = 7L;

	private List<Unit> myUnits = new ArrayList<Unit>();
	private List<Territory> myTerritories = new ArrayList<Territory>();
	private ServerPlayer myPlayer;

	private List<Player> myAllies = new ArrayList<Player>();

	private int myTechLevel = 0;

	private int nukeTurns = 0;
	private boolean nukesReady = false;

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
		if(myTechLevel==6) return false;
		if(myTech.getAmount()>PLAYER_TECH_TREE.getCost(myTechLevel)){
			myTech.increment(-PLAYER_TECH_TREE.getCost(myTechLevel));
			myTechLevel++;
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

	public void checkNukesReady(){
		if(myTechLevel==6){
			nukeTurns++;
			if(nukeTurns > 2){
				nukesReady = true;
			}
		}
	}

	public boolean isNukeReady(){
		return nukesReady;
	}

	public boolean equals(Player p){
		return this.getName().equals(p.getName());
	}

	public void addAlly(Player p){
		if(!myAllies.contains(p))
			myAllies.add(p);
	}
	
	public List<Player> getAllies(){
		return myAllies;
	}
	
	public List<Territory> getAllAlliedTerritories(){
		List<Territory> pt = this.getTerritories();
		for(Player ally : this.getAllies()){
			pt.addAll(ally.getTerritories());
		}
		return pt;
	}

	public boolean onTeam(Player p){
		if(this.equals(p)) return true;
		for(Player q : myAllies){
			if(p.equals(q)) return true;
		}
		return false;
	}
	
	public String toString(){
		return myPlayer.getName();
	}

	public void removeAlly(Player other) {
		if(myAllies.contains(other))
			myAllies.remove(other);
	}

	public String getAlliesString() {
		String toReturn = "";
		for(Player p : myAllies){
			toReturn += p.getName()+"\n";
		}
		return toReturn;
	}
	
	public boolean isAlly(Player other){
		for(Player q : myAllies){
			if(q.equals(other)) return true;
		}
		return false;
	}

}
