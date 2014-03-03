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
	private List<ServerPlayer> myPlayers = new ArrayList<ServerPlayer>();
	private int maxPlayers;
	private boolean inProgress;

	public GameInfo(String name, int max) {
		myName = name;
		maxPlayers = max;
		inProgress = false;
	}
	
	public boolean addPlayer(ServerPlayer player){
		myPlayers.add(player);
		if (myPlayers.size() == maxPlayers){
			inProgress = true;
		}
		return inProgress;
	}
	
	public void removePlayer(String player){
		if (inProgress)
			System.out.println("someone tried to leave an in progress game");
		myPlayers.remove(player);
	}
	
	public String toString(){
		String token = myName + " --- " + myPlayers.size()+"/"+maxPlayers;
		if (inProgress){
			return token + " in Progress!";
		}
		return token + " waiting for more Players...";
	}
	
	public List<ServerPlayer> getPlayers(){
		return myPlayers;
	}

}
