package server;

import gameElements.CommandList;
import gameElements.GameState;
import gameElements.GameModel;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ObjectServer extends Thread implements ServerConstants{

	private HashMap<ObjectSocket, String> myConnections = new HashMap<ObjectSocket, String>();
	private int numPlayers;
	private HashMap<String, ObjectSocket> myPlayers = new HashMap<String, ObjectSocket>();
	private int commandsReceived=0;
	private GameModel myModel;
	private CommandList turnCommands = new CommandList();
	private static final int DEFAULT_NUM_TERRITORIES = 6;

	public ObjectServer(GameModel sg, int playerCount){
		myModel = sg;
		sg.setServer(this);
		numPlayers = playerCount;
	}

	public ObjectServer(){

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
				myConnections.put(runnable, "Default");
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
		catch (Exception e) {}
	}

	public Collection<ObjectSocket> getConnections(){
		//System.out.println("Number of connections = "+myConnections.size());
		return myConnections.keySet();
	}

	public void broadCastMessage(TextMessage m, ObjectSocket os) {
		for(ObjectSocket s : this.getConnections()){
			s.sendMessage(new TextMessage(myConnections.get(os) + ": " + m.getMessage()));
		}
	}

	public void sendUpdatedGame(GameState gs){
		for(ObjectSocket s : this.getConnections())
			s.sendMessage(gs);
	}

	public void receiveCommandList(CommandList ls){
		turnCommands.addCommands(ls.getCommands());
		commandsReceived++;
		System.out.println(commandsReceived + " " + numPlayers);
		if(commandsReceived==numPlayers){
			System.out.println("All commands received! Sending to model! Numcommands = " + turnCommands.getCommands().size());
			myModel.performCommands(turnCommands);
			turnCommands.clear();
			commandsReceived = 0;
		}		
	}

	public void initialConnect(ObjectSocket os, InitialConnect ic){
		myConnections.put(os, ic.getName());
		myPlayers.put(ic.getName(),os);
		if (myPlayers.keySet().size() == 1){
			os.promptNewGame();
		}
		else if (myPlayers.keySet().size() < numPlayers){
			for(ObjectSocket connection : myConnections.keySet()){
				connection.sendMessage(new TextMessage(ic.getName()+" has joined the game!\nWaiting for additional Players... "+myPlayers.size()+"/"+numPlayers+"."));
			}
		}
		else{
			System.out.println("server sending start game information!");
			GameState gs = new GameState(myConnections.values(), DEFAULT_NUM_TERRITORIES);
			myModel = new GameModel(gs);
			myModel.setServer(this);
			this.sendUpdatedGame(gs);
			for(String player : myPlayers.keySet()){
				myPlayers.get(player).sendMessage(new InitialConnect(player));
			}
		}
	}

	public void setNumPlayers(int num){
		numPlayers = num;
		/////////// HERE IS WHERE I"M MAKING EXTRA CLIENTS FOR TESTING PURPOSES!
		System.out.println("Extra client created after the host chooses num of players.  ObjectServer ln 110");
		ObjectClient myClient2 = new ObjectClient();
		myClient2.start();
	}

	public void removeConnection(ObjectSocket ms){
		myConnections.remove(ms);
	}

}
