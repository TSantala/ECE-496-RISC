package gameElements;

import java.util.List;

public class GameState {

    GameMap myMap;
    List<Player> myPlayers;
    public GameState()
    {
        
    }
    
    public void move(int from, int to, List<Unit> units){
    	myMap.getTerritory(from).removeUnits(units);
    	myMap.getTerritory(to).addUnits(units);
    }
    
}
