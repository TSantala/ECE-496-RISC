package gameElements;
/*
 * Every user to server communication will include some kind of command
 */
import java.io.Serializable;
import java.util.List;

public abstract class Command implements Serializable //VERY IMPORTANT TO SERIALIZE
{
	private static final long serialVersionUID = 12L;
	
	protected Territory myTerritoryFrom;
	protected Territory myTerritoryTo;
	protected List<Unit> myUnits;
	protected Player myPlayer;
	
	public abstract void enact(GameModel sg);

	public Territory getFrom() {
		return myTerritoryFrom;
	}
	
	public Territory getTo(){
		return myTerritoryTo;
	}
	
	public List<Unit> getUnits(){
		return myUnits;
	}
	
	public Player getPlayer(){
		return myPlayer;
	}
}
