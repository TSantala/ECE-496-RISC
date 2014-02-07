package gameElements;

import java.io.Serializable;
import java.util.List;

public class AttackCommand extends Command implements Serializable {

	private static final long serialVersionUID = 1L;

	public AttackCommand(){
		//testing...
	}
	
    public AttackCommand(Player p, Territory from, Territory to, List<Unit> units){
        myPlayer = p;
    	myTerritoryFrom = from;
        myTerritoryTo = to;
        myUnits = units;
    }

	@Override
	public void enact(ServerGame sg) {
		sg.attack(myPlayer, myTerritoryFrom, myTerritoryTo,myUnits);
	}
}
