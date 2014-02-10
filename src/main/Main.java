package main;

import server.ObjectClient;
import server.ObjectServer;

public class Main {
	public static void main(String[] args){
		
		//GameState init = new GameState(NUM_PLAYERS,NUM_TERRITORIES, NUM_START_UNITS);	// Numbers will be determined by start-wizard later...
		//GameModel gm = new GameModel(init);
		
		//ObjectServer myServer = new ObjectServer(gm,NUM_PLAYERS);
		ObjectServer myServer = new ObjectServer();
		myServer.start();
		
		//ObjectClient myClient1 = new ObjectClient();
		//myClient1.start();

	}
}
