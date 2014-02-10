package gameElements;
/*
 * Very similar to AttackCommand right now...will be refactored soon to reduce redundant code
 */
import java.io.Serializable;
import java.util.List;

public class MoveCommand extends Command implements Serializable {
	
	private static final long serialVersionUID = 6L;

	public MoveCommand(){
		//testing...
	}

    public MoveCommand(Player p, Territory from, Territory to, List<Unit> units){
    	myPlayer = p;
    	myTerritoryFrom = from;
    	myTerritoryTo = to;
    	myUnits = units;
    }

    public String toString(){
        String temp  = "Move: " +  myTerritoryFrom.getID() + " to: " + myTerritoryTo.getID() + " with " + myUnits.size() + " units.\n";
        return temp;
    }
	@Override
	public void enact(GameModel sg) {
		sg.move(myPlayer, myTerritoryFrom, myTerritoryTo, myUnits, false);
	}


    
}
