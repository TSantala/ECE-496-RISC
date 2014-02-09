package gameElements;

import java.io.Serializable;
import java.util.List;

public class AddUnitCommand extends Command implements Serializable {

	private static final long serialVersionUID = 2L;

	public AddUnitCommand(){
		//testing...
	}
	
    public AddUnitCommand(Player p, Territory from, List<Unit> units){
        myPlayer = p;
    	myTerritoryFrom = from;
        myUnits = units;
    }

    public String toString(){
        String temp  = "Add" +  myUnits.size() + "units to: " + myTerritoryFrom.getID()+ "\n";
        return temp;
    }
	@Override
	public void enact(GameModel sg) {
		sg.placeUnits(myPlayer, myTerritoryFrom, myUnits);
	}
}
