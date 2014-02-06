package gameElements;

import java.util.List;

public class MoveCommand extends Command{

    public MoveCommand(Player p, Territory from, Territory to, List<Unit> units){
    	myPlayer = p;
    	myTerritoryFrom = from;
    	myTerritoryTo = to;
    	myUnits = units;
    }

	@Override
	public void enact(ServerGame sg) {
		sg.move(myPlayer, myTerritoryFrom, myTerritoryTo, myUnits, false);
	}


    
}
