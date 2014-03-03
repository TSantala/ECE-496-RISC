package server;

import gameElements.CommandList;
import gameElements.GameState;
import gameElements.GameModel;
import gameElements.Player;
import gameElements.ServerGame;
import gameElements.Territory;
import gameElements.Unit;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ObjectServer extends Thread implements ServerConstants{

	private HashMap<ObjectSocket, ServerPlayer> myConnections = new HashMap<ObjectSocket, ServerPlayer>();
	private HashMap<ServerPlayer, ObjectSocket> myPlayers = new HashMap<ServerPlayer, ObjectSocket>();
	private List<ServerPlayer> myLobby = new ArrayList<ServerPlayer>();
	private List<ServerGame> myGames = new ArrayList<ServerGame>();

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
				//myConnections.put(runnable, null);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
		catch (Exception e) {}
	}

	public Collection<ObjectSocket> getConnections(){
		return myConnections.keySet();
	}

	public synchronized void broadCastMessage(TextMessage m, ObjectSocket os) {
		for (ServerGame game : myGames){
			if (game.getInfo().getPlayers().contains(myConnections.get(os))){
				for (ServerPlayer player : game.getInfo().getPlayers()){
					myPlayers.get(player).sendMessage(new TextMessage(myConnections.get(os).getName()+": "+m.getMessage()));
				}
				return;
			}
		}

		for (ServerPlayer player : myLobby){
			myPlayers.get(player).sendMessage(new TextMessage(myConnections.get(os).getName()+": "+m.getMessage()));
		}
		/*for(ObjectSocket s : this.getConnections()){
			s.sendMessage(new TextMessage(myConnections.get(os).getName() + ": " + m.getMessage()));
		}*/
	}

	public void sendUpdatedGame(GameState gs, ServerGame serverGame){
		for(ServerPlayer player : serverGame.getInfo().getPlayers())
			myPlayers.get(player).sendMessage(gs);
	}

	public synchronized void receiveCommandList(CommandList ls){
		for (ServerGame game : myGames){
			if (game.getInfo().getPlayers().contains(myConnections.get(ls.getObjectSocket()))){
				game.receiveCommandList(ls);
			}
		}
	}

	public synchronized void removeConnection(ObjectSocket ms){
		myConnections.remove(ms);
	}

	public synchronized void joinGame(int myNum, ObjectSocket os) {
		if (myLobby.contains(myConnections.get(os))){
			myGames.get(myNum).addPlayer(myConnections.get(os));
			myLobby.remove(myConnections.get(os));
		}
		this.updateGameInfo();
	}

	public void BeginNewGame(int numPlayers, String name) {
		myGames.add(new ServerGame(name,numPlayers,this));
		this.updateGameInfo();
	}

	public synchronized void newPlayer(String name, String pass, ObjectSocket os){
		ServerPlayer temp = new ServerPlayer(name, pass);
		myPlayers.put(temp, os);
		myConnections.put(os, temp);
		myLobby.add(temp);
		os.sendMessage(new InitialConnect(name,pass));
	}

	public synchronized void updateGameInfo(){
		for(ObjectSocket s : this.getConnections())
			s.sendMessage(new GameInfoUpdate(myGames));
	}


	/*public void initialConnect(ObjectSocket os, InitialConnect ic){
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
	}*/

	/*public void setNumPlayers(int num){
		numPlayers = num;
		/////////// HERE IS WHERE I"M MAKING EXTRA CLIENTS FOR TESTING PURPOSES!
<<<<<<< HEAD
		System.out.println("Extra client created after the host chooses num of players.  ObjectServer ln 110");
		ObjectClient myClient2 = new ObjectClient();
		myClient2.start();
	}*/

}
