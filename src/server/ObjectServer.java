package server;

import gameElements.CommandList;
import gameElements.GameState;
import gameElements.GameModel;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ObjectServer extends Thread implements ServerConstants{
	
	private HashSet<ObjectSocket> myConnections = new HashSet<ObjectSocket>();
	private int numPlayers;
	private int commandsReceived=0;
	private GameModel myModel;
	private CommandList turnCommands = new CommandList();
	
	public ObjectServer(GameModel sg, int playerCount){
		myModel = sg;
		sg.setServer(this);
		numPlayers = playerCount;
	}
	
	public void run(){
		
		int port = 19999;
		int count = 0;
		try{
			ServerSocket socket1 = new ServerSocket(port);
			System.out.println("Server Initialized");
			while (true) {
				Socket connection = socket1.accept();
				ObjectSocket runnable = new ObjectSocket(connection, ++count, this);
				myConnections.add(runnable);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
		catch (Exception e) {}
	}
	
	public Collection<ObjectSocket> getConnections(){
		//System.out.println("Number of connections = "+myConnections.size());
		return myConnections;
	}
	
	public void broadCastMessage(TextMessage m) {
		for(ObjectSocket s : this.getConnections()){
			s.sendMessage(m);
		}
	}
	
	public void updateGameStates(GameState gs){
		for(ObjectSocket s : this.getConnections()){
			s.sendGameState(gs);
		}
	}
	
	public void sendUpdatedGame(GameState gs){
		for(ObjectSocket s : this.getConnections())
			s.sendGameState(gs);
	}
	
	public void receiveCommandList(CommandList ls){
		turnCommands.addCommands(ls.getCommands());
		commandsReceived++;
		System.out.println(commandsReceived + " " + numPlayers);
		if(commandsReceived==numPlayers){
			System.out.println("All commands received! Sending to model!");
			myModel.performCommands(ls);
			turnCommands.clear();
			commandsReceived = 0;
		}		
	}
	
	public void removeConnection(ObjectSocket ms){
		myConnections.remove(ms);
	}

}
