package gameElements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import server.Message;
import server.ObjectClient;
import server.ObjectServer;

public class GameState extends Message implements Serializable {

	private static final long serialVersionUID = 5L;
	
	private GameMap myMap;
	private List<Player> myPlayers = new ArrayList<Player>();
	private static final int DEFAULT_NUM_START_UNITS = 1;

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
	
	public GameState(Collection<String> players, int numTerritories){
		for(String s : players){
			myPlayers.add(new Player(s));
		}
		
		// Create map of territories.
				myMap = new GameMap(numTerritories);
				
				// Randomly assign initial territories.
				List<Territory> initialTerritories = new ArrayList<Territory>();
				initialTerritories.addAll(myMap.getTerritories());
				int player = 0;
				while(!initialTerritories.isEmpty()){
					player = (player+1)%myPlayers.size();
					int territory = (int) Math.floor(initialTerritories.size()*Math.random());
					myPlayers.get(player).addTerritory(initialTerritories.get(territory));
					initialTerritories.remove(territory);
				}
	}

	public GameState(GameMap gm, List<Player> players){
		myMap = gm;
		myPlayers = players;
	}

	/*
	 * FOR TESTING! Just allocating starting units in constructor
	 */
	public GameState (int numPlayers, int numTerritories, int numStartUnits) {
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
            
            //distribute units to each territory 
            List<Territory> p1t = myPlayers.get(0).getTerritories();
            List<Territory> p2t = myPlayers.get(1).getTerritories();
            int id = 0;
            for (Territory t : p1t)
            {
                for (int i=0; i < numStartUnits/p1t.size(); i++)
                {
                    t.addUnit(new Unit(myPlayers.get(0), i));
                }
            }
            for (Territory t : p2t)
            {
                for (int i=0; i < numStartUnits/p2t.size(); i++)
                {
                    t.addUnit(new Unit(myPlayers.get(1), i+14)); //ids must be unique
                }
            }
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

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		// should never happen
		return false;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		oc.receiveGameState(this);
	}
	
	public int startUnits(){
		return DEFAULT_NUM_START_UNITS;
	}

}
