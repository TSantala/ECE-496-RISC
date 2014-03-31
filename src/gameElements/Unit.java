package gameElements;

import java.io.Serializable;

public class Unit implements Serializable, GameConstants, Comparable<Unit>
{
	private static final long serialVersionUID = 10L;

	private Player myOwner;
	private int myID;
	private int myTechLevel=0;
	private boolean isSpy;
	private int spyTurnCount=0;

	public Unit(int id){
		myID = id;
		isSpy = false;
	}

	public Unit(Player p, int id){
		myOwner = p;
		myID = id;
		isSpy = false;
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

	public void setTechLevel(int i){
		myTechLevel = i;
	}

	public boolean upgradeUnit(){ //must differentiate between spy and not spy
		if (isSpy)
		{
			if(myOwner.getTechAmount()>5) //HARD CODED 5 AS VALUE TO RETURN SPY
			{
				myOwner.adjustResource(new Technology(-5));
				return true;
			}
			return false;
		}
		else{
			System.out.println("UPGRADING!! Not a spy...");
			if(myOwner.getTechLevel()>myTechLevel){
				if(myOwner.getTechAmount()>UNIT_TECH_TREE.getCost(myTechLevel)){
					myOwner.adjustResource(new Technology(-UNIT_TECH_TREE.getCost(myTechLevel)));
					myTechLevel++;
					return true;
				}
			}
			else{
				System.out.println("Player is not high enough tech level!");
			}
			return false;
		}
	}

	public int getCombatBonus(){
		return UNIT_TECH_TREE.getCombatBonus(myTechLevel);
	}

	public String getType(){
		return UNIT_TECH_TREE.getUnitType(myTechLevel);
	}

	public boolean isSpy(){
		return isSpy;
	}

	public void setTurnCount(int t) //sets spy turn count
	{
		spyTurnCount = t;
	}

	public int getTurnCount()
	{
		return spyTurnCount;
	}

	@Override
	public int compareTo(Unit other){
		int spyBonus = isSpy ? 2 : 0;
		return other.getTechLevel() - (myTechLevel+spyBonus);
	}

	@Override
	public String toString(){
		return this.getType() + " " + this.getID();
	}

}
