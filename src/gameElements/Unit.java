package gameElements;

public class Unit 
{
	private Player myOwner;
	private int myID;

	public Unit(Player p, int id){
		myOwner = p;
		myID = id;
	}
	
	public Player getOwner(){
		return myOwner;
	}
	
	public int getID(){
		return myID;
	}
}
