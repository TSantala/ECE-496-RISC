package gameElements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {

	private static final long serialVersionUID = 1L;
	
	GameMap myMap;
	List<Player> myPlayers = new ArrayList<Player>();

	public GameState(int numPlayers, int numTerritories){
		for(int i = 0; i<numPlayers; i++){
			myPlayers.add(new Player("Player "+myPlayers.size()));
		}
		
		// Create map of territories.
		myMap = new GameMap(numTerritories);
		
		// Randomly assign initial territories.
		List<Territory> initialTerritories = new ArrayList<Territory>();
		initialTerritories.addAll(myMap.getTerritories());
		int player = 0;
		while(!initialTerritories.isEmpty()){
			player = (player+1)%numPlayers;
			int territory = (int) Math.floor(initialTerritories.size()*Math.random());
			myPlayers.get(player).addTerritory(initialTerritories.get(territory));
			initialTerritories.remove(territory);
		}
	}

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
