package gameElements;
/*
 * Wraps user attack into a command class which will tell server what is inside
 */
import java.io.Serializable;
import java.util.List;

public class AttackCommand extends Command implements Serializable {

	private static final long serialVersionUID = 2L;

	public AttackCommand(){
		//testing...
	}
	
    public AttackCommand(Player p, Territory from, Territory to, List<Unit> units){
        myPlayer = p;
    	myTerritoryFrom = from;
        myTerritoryTo = to;
        myUnits = units;
    }

    public String toString(){
        String temp  = "Attack: " +  myTerritoryFrom.getID() + " to: " + myTerritoryTo.getID() + " with " + myUnits.size() + " units.\n";
        return temp;
    }
	@Override
	public void enact(GameModel sg) {
		sg.attack(myPlayer, myTerritoryFrom, myTerritoryTo,myUnits);
	}
}
