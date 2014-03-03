package gameElements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import server.ServerPlayer;

public class GameInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String myName;
	private List<ServerPlayer> myCurrentPlayers = new ArrayList<ServerPlayer>();
	private List<ServerPlayer> myOriginalPlayers = new ArrayList<ServerPlayer>();
	private int maxPlayers;
	private boolean inProgress;

	public GameInfo(String name, int max) {
		myName = name;
		maxPlayers = max;
		inProgress = false;
	}
	
	public boolean addPlayer(ServerPlayer player){
		myCurrentPlayers.add(player);
		if (myCurrentPlayers.size() == maxPlayers){
			inProgress = true;
			myOriginalPlayers.addAll(myCurrentPlayers);
		}
		return inProgress;
	}
	
	public void removePlayer(ServerPlayer player){
		if (inProgress)
			System.out.println("someone tried to leave an in progress game");
		myCurrentPlayers.remove(player);
	}
	
	public String toString(){
		String token = myName + " --- " + myCurrentPlayers.size()+"/"+maxPlayers;
		if (inProgress){
			return token + " in Progress!";
		}
		return token + " waiting for more Players...";
	}
	
	public List<ServerPlayer> getPlayers(){
		return myCurrentPlayers;
	}
	
	public List<ServerPlayer> getOriginalPlayers(){
		return myOriginalPlayers;
	}

}
