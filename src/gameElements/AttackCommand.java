package gameElements;

import java.util.List;

public class AttackCommand extends Command{
	
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
