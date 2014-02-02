package gameElements;

public class Unit 
{
	private Player myOwner;
	private String myID;

	public Unit(Player p)
	{
		myOwner = p;
	}
	public Player getOwner()
	{
		return myOwner;
	}
	public String getID(){
		return myID;
	}
}
