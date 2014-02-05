package gameElements;

import java.util.List;

public class GameState {

    GameMap myMap;
    List<Player> myPlayers;
    
    public GameState()
    {
    }
    
    public void setMap(GameMap gm){
    	myMap = gm;
    }
    
    public GameMap getMap(){
    	return myMap;
    }
    
    public void setPlayers(List<Player> players){
    	myPlayers = players;
    }
    
    public List<Player> getPlayers(){
    	return myPlayers;
    }
    
}
