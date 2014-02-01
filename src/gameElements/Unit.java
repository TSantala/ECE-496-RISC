package gameElements;

public class Unit 
{
	private Player owner;
	//private Stat stats;
	public Unit(Player p)
	{
		owner = p;
	}
	public Player getOwner()
	{
		return owner;
	}
}
