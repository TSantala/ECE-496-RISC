package main;

import gameElements.GameState;
import gameElements.ServerGame;
import server.ObjectClient;
import server.ObjectServer;


public class Main {
	
	private static int NUM_PLAYERS = 2;
	private static int NUM_TERRITORIES = 6;
	private static int NUM_START_UNITS = 10;

	public static void main(String[] args){
		
		GameState init = new GameState(NUM_PLAYERS,NUM_TERRITORIES);	// Numbers will be determined by start-wizard later...
		ServerGame sg = new ServerGame(init);
		
		ObjectServer myServer = new ObjectServer(sg);
		myServer.start();
		
		ObjectClient myClient1 = new ObjectClient(init);
		myClient1.start();
		
		ObjectClient myClient2 = new ObjectClient(init);
		myClient2.start();

		System.out.println("1");

	}
}
