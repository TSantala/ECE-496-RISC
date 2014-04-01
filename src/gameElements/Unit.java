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

	public Unit(int id, Player p){
		myID = id;
		myOwner = p;
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
		return new Unit(myID,myOwner);
	}

	public int getTechLevel(){
		return myTechLevel;
	}

	public void setTechLevel(int i){
		myTechLevel = i;
	}

	public boolean toggleSpy(){
		if(isSpy && myOwner.getTechAmount()>=5) {
			myOwner.adjustResource(new Technology(-5));
			isSpy = !isSpy;
			return true;
		}
		else if(!isSpy && myOwner.getTechAmount()>=35){
			myOwner.adjustResource(new Technology(-35));
			isSpy = !isSpy;
			return true;
		}
		return false;
	}

	public boolean upgradeUnit(){ //must differentiate between spy and not spy
		System.out.println("UPGRADING!! Not a spy...");
		if(myOwner.getTechLevel()>myTechLevel){
			if(myOwner.getTechAmount()>=UNIT_TECH_TREE.getCost(myTechLevel)){
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

	public int getCombatBonus(){
		return isSpy ? 0 : UNIT_TECH_TREE.getCombatBonus(myTechLevel);
	}

	public String getType(){
		return isSpy ? "Spy" : UNIT_TECH_TREE.getUnitType(myTechLevel);
	}

	public boolean isSpy(){
		return isSpy;
	}

	public void setTurnCount(int t){
		spyTurnCount = t;
	}

	public int getTurnCount(){
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
