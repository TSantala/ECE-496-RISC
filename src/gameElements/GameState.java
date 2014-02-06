package gameElements;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    GameMap myMap;
    List<Player> myPlayers;
    
    public GameState(GameMap gm, List<Player> players){
    	myMap = gm;
    	myPlayers = players;
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
    
    public Player getPlayer(String name){
    	for(Player p : myPlayers){
    		if(p.getName().equals(name))
    			return p;
    	}
    	System.out.println("Get player returned null!! In GameState");
    	return null;
    }
    
    public Unit getUnit(int id){
    	for(Player p : myPlayers){
    		for(Unit u : p.getUnits()){
    			if(u.getID() == id)
    				return u;
    		}
    	}
    	System.out.println("Null return: Unit not found in GameState call!");
    	return null;
    }
    
    public GameState clone(){
    	GameMap mapClone = myMap.clone();
    	List<Player> playersClone = new ArrayList<Player>();
    	for(Player p : myPlayers){
    		playersClone.add(p.clone(mapClone));
    	}
    	return new GameState(mapClone,playersClone);
    }
    
}
