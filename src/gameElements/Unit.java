package gameElements;

import java.io.Serializable;

public class Unit implements Serializable, GameConstants, Comparable<Unit>
{
	private static final long serialVersionUID = 10L;
	
	private Player myOwner;
	private int myID;
	private int myTechLevel=0;

	public Unit(int id){
		myID = id;
	}
	
	public Unit(Player p, int id){
		myOwner = p;
		myID = id;
	}
	
	public void setOwner(Player p){
		myOwner = p;
	}
	
	public Player getOwner(){
		return myOwner;
	}
	
	public int getID(){
		return myID;
	}
	
	public Unit clone(){
		return new Unit(myID);
	}
	
	public void upgrade(int researches){
		myTechLevel+=researches;
	}
	
	public int getTechLevel(){
		return myTechLevel;
	}
	
	public int getUpgradeCost(){
		return UNIT_TECH_TREE.getCost(myTechLevel++);
	}
	
	public int getCombatBonus(){
		return UNIT_TECH_TREE.getCombatBonus(myTechLevel);
	}
	
	public String getType(){
		return UNIT_TECH_TREE.getUnitType(myTechLevel);
	}
	
	@Override
	public int compareTo(Unit other){
		return other.getTechLevel() - myTechLevel;
	}

}
