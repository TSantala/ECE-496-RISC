package gameElements;

import java.util.List;

public class MoveCommand extends Command{
	
	private int myTerritoryFrom;
	private int myTerritoryTo;
	private List<Unit> myUnits;


    public MoveCommand(int from, int to, List<Unit> units){
    	myTerritoryFrom = from;
    	myTerritoryTo = to;
    	myUnits = units;
    }


	public int getFrom() {
		return myTerritoryFrom;
	}
	
	public int getTo(){
		return myTerritoryTo;
	}
	
	public List<Unit> getUnits(){
		return myUnits;
	}


	@Override
	public void enact(ServerGame sg) {
		sg.move(myTerritoryFrom, myTerritoryTo, myUnits);
	}


    
}
