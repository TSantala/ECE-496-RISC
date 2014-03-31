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

	public int getTechLevel(){
		return myTechLevel;
	}

	public boolean upgradeUnit(){
		if(myOwner.getTechLevel()>myTechLevel){
			if(myOwner.getTechAmount()>UNIT_TECH_TREE.getCost(myTechLevel)){
				myOwner.adjustResource(new Technology(-UNIT_TECH_TREE.getCost(myTechLevel)));
				myTechLevel++;
				return true;
			}
		}
		return false;
	}

	public int getCombatBonus(){
		return UNIT_TECH_TREE.getCombatBonus(myTechLevel);
	}

	public String getType(){
		return UNIT_TECH_TREE.getUnitType(myTechLevel);
	}

	@Override
	public int compareTo(Unit other){
		int spyBonus = isSpy ? 2 : 0;
		return other.getTechLevel() - (myTechLevel+spyBonus);
	}

}
