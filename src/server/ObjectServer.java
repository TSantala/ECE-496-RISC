package server;

import gameElements.CommandList;
import gameElements.GameState;
import gameElements.ServerGame;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ObjectServer extends Thread implements ServerConstants{
	
	private HashSet<ObjectSocket> myConnections = new HashSet<ObjectSocket>();
	private int myNumPlayers;
	private ServerGame myModel;
	
	public ObjectServer(ServerGame sg){
		myModel = sg;
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
	
	public void sendUpdatedGame(GameState gs){
		for(ObjectSocket s : this.getConnections())
			s.sendGameState(gs);
	}
	
	public void receiveCommandList(CommandList ls){
		System.out.println("Got it!!!");
	}
	
	public void removeConnection(ObjectSocket ms){
		myConnections.remove(ms);
	}

}
