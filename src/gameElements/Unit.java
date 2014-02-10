package gameElements;

import java.io.Serializable;

public class Unit implements Serializable 
{
	private static final long serialVersionUID = 10L;
	
	private Player myOwner;
	private int myID;

	public Unit(int id){
		myID = id;
	}
	
	public Unit(Player p, int id){
		myOwner = p;
		myID = id;
		p.addUnit(this);
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
}
