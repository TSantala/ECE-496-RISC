package gameElements;
/*
 * What is sent to the clients every time, they build the GUI using information in this
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import server.Message;
import server.ObjectClient;
import server.ObjectServer;
import server.ServerPlayer;

public class GameState extends Message implements Serializable {

	private static final long serialVersionUID = 5L;
	
	private GameMap myMap;
	private List<Player> myPlayers = new ArrayList<Player>();
	private static final int DEFAULT_NUM_START_UNITS = 3;
	private static final int DEFAULT_NUM_START_TERRITORIES = 6;

	public GameState(GameMap gm, List<Player> players){
		myMap = gm;
		myPlayers = players;
	}

    public GameState(List<ServerPlayer> players) {
		for(ServerPlayer player : players){
			myPlayers.add(new Player(player));
		}
		// Create map of territories.
		myMap = new GameMap(DEFAULT_NUM_START_TERRITORIES);
		//myMap = new GameMap();
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

	public void upgradeUnit(Unit u) {
		this.getUnit(u.getID()).upgradeUnit();
	}

	public void upgradePlayer(Player p) {
		this.getPlayer(p.getName()).upgradePlayer();
	}
	
	public void makeSpy(Unit u){
		this.getUnit(u.getID()).toggleSpy();
	}

	public void clearMap() {
		//myMap.clear();			TO-DO...
	}

}
