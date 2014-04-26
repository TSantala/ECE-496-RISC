package server;

import gameElements.CommandList;
import gameElements.GameState;
import gameElements.Player;
import gameElements.SaveGame;
import gameElements.ServerGame;
import gui.AllianceRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ObjectServer extends Thread implements ServerConstants{

	private HashMap<ObjectSocket, ServerPlayer> myConnections = new HashMap<ObjectSocket, ServerPlayer>();
	private HashMap<ServerPlayer, ObjectSocket> myPlayers = new HashMap<ServerPlayer, ObjectSocket>();
	private List<ServerPlayer> myLobby = new ArrayList<ServerPlayer>();
	private List<ServerGame> myGames = new ArrayList<ServerGame>();

	public ObjectServer(boolean readData){
		if (readData){
			try{
				FileInputStream saveFile = new FileInputStream(".//serverData.sav");
				ObjectInputStream save = new ObjectInputStream(saveFile);

				@SuppressWarnings("unchecked")
				List<SaveGame> tempData = (List<SaveGame>) save.readObject();

				save.close();

				for(SaveGame game : tempData){
					myGames.add(new ServerGame(game.getInfo(),game.getState(),this));
				}
			}
			catch(Exception exc){
				exc.printStackTrace();
				System.out.println("couldn't read the data");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SaveGame> getSaveGameList(){
		FileInputStream saveFile;
		ObjectInputStream save;
		List<SaveGame> tempData = null;
		try {
			saveFile = new FileInputStream(".//serverData.sav");
			save = new ObjectInputStream(saveFile);
			tempData = (List<SaveGame>) save.readObject();
			save.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
		return tempData;
	}
	
	public GameState getSavedState(ServerGame current){
		List<SaveGame> list = this.getSaveGameList();
		System.out.println("got the list...");
		for(SaveGame sg : list){
			if(sg.getInfo().equals(current.getInfo()))
				return sg.getState();
		}
		System.out.println("SAVED GAMESTATE NOT FOUND!!!");
		return null;
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
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
		catch (Exception e) {
			System.out.println("error making a new socket connection in the server");
		}
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
	}

	public void sendUpdatedGame(Message m, ServerGame serverGame){
		for(ServerPlayer player : serverGame.getInfo().getPlayers())
			myPlayers.get(player).sendMessage(m);
		this.backupData();
	}
	
	public void sendGameByPlayer(Message m, ServerPlayer serverPlayer){
		myPlayers.get(serverPlayer).sendMessage(m);
		this.backupData();
	}

	public synchronized void receiveCommandList(CommandList ls){
		for (ServerGame game : myGames){
			if (game.getInfo().getPlayers().contains(myConnections.get(ls.getObjectSocket()))){
				game.receiveCommandList(ls);
			}
		}
	}

	public synchronized void removeConnection(ObjectSocket ms){
		for(ServerGame game : myGames){
			if(game.getInfo().getPlayers().contains(myConnections.get(ms))){
				game.getInfo().removePlayer(myConnections.get(ms));
			}
		}
		myPlayers.remove(myConnections.get(ms));
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
		this.updateGameInfo();
	}

	public synchronized void updateGameInfo(){
		for(ObjectSocket s : this.getConnections())
			s.sendMessage(new GameInfoUpdate(myGames));
	}

	public void leaveGame(ObjectSocket os) {
		for(ServerGame game : myGames){
			if(game.getInfo().getPlayers().contains(myConnections.get(os))){
				game.getInfo().removePlayer(myConnections.get(os));
				myLobby.add(myConnections.get(os));
				this.updateGameInfo();
			}
		}
	}

	private void backupData(){
		List<SaveGame> data = new ArrayList<SaveGame>();
		for(ServerGame game : myGames){
			data.add(game.saveGame());
		}

		try{
			FileOutputStream saveFile=new FileOutputStream(".//serverData.sav");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(data);
			save.close();
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
	}
	
	public void endGame(String serverGameName)
	{
	    for (Iterator<ServerGame> iterator = myGames.iterator(); iterator.hasNext();)
	    {
	        ServerGame sg = iterator.next();
	        if (sg.getInfo().getName().equals(serverGameName)) //found the game to remove 
	        {
	            iterator.remove();
	            break;
	        }
	    }
	    this.updateGameInfo();
	}
	
	public synchronized void sendAllianceRequest(AllianceRequest ar){
		ObjectSocket toTell = myPlayers.get(ar.getTo());
		toTell.sendMessage(ar);
	}
	
	public synchronized void acceptAllianceRequest(ServerPlayer justAccepted, ServerPlayer toTell){
		
	}

}
