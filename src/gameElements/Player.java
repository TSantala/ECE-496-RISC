package gameElements;

import java.util.*;

public class Player 
{
	private String myName;
	private List<Unit> myUnits;
	private TerritoryGroup myTerritories;
	//private List<Order> previousOrders;

	public Player(){
		myName = "johnandtimo";
		myUnits = new ArrayList<Unit>();
		myTerritories = new TerritoryGroup();
	}

	public void setName(String s){
		myName = s;
	}

	public String getName(){
		return myName;
	}
	
	public List<Unit> getUnits(){
		return myUnits;
	}
	
	public void setMyUnits (List<Unit> units){
		myUnits = units;
	}
	
	public void removeUnit(Unit u){
		myUnits.remove(u);
	}
	
	public TerritoryGroup getTerritories(){
		return myTerritories;
	}
	public void setTerritories (TerritoryGroup myTerritories) {
		this.myTerritories = myTerritories;
	}
}
