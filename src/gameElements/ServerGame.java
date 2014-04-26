package gameElements;

import java.util.HashMap;
import java.util.List;

import server.Message;
import server.ObjectServer;
import server.PromptUnits;
import server.ServerPlayer;

public class ServerGame extends Thread{

	private GameInfo myInfo;
	private GameState myGame;
	private ObjectServer myServer;
	private int commandsReceived=0;
	private GameModel myModel;
	private CommandList turnCommands = new CommandList();
	private boolean unitsPlaced = false;
	private boolean inProgress = false;

	public ServerGame(String gameName, int numPlayers, ObjectServer os) {
		myInfo = new GameInfo(gameName, numPlayers);
		myServer = os;
	}

	public ServerGame(GameInfo info, GameState game, ObjectServer os){
		myInfo = info;
		myInfo.getPlayers().clear();
		myGame = game;
		myServer = os;
		myModel = new GameModel(myGame,this);
		if(!myInfo.getOriginalPlayers().isEmpty()){
			unitsPlaced = true;
			inProgress = true;
			this.start();
		}
	}

	public void run(){
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;

		while (true) {
			//perform db poll/check
			elapsedTime = System.currentTimeMillis() - startTime;
			if (elapsedTime > 1*10*1000){
				//startTime = System.currentTimeMillis();
				//System.out.println("It's been 10 seconds! processing commands");
				//this.processCommands();
			}
		}

	}

	public GameInfo getInfo(){
		return myInfo;
	}

	public void addPlayer(ServerPlayer p){
		if(myInfo.addPlayer(p) && !inProgress){
			myGame = new GameState(myInfo.getPlayers());
			myModel = new GameModel(myGame, this);
			this.updateGame(myGame);
			this.updateGame(new PromptUnits());
			inProgress = true;
		}
		else if (inProgress){
			this.updateGame();
		}
	}

	public void receiveCommandList(CommandList ls){
		turnCommands.addCommands(ls.getCommands());
		commandsReceived++;
		System.out.println(commandsReceived + " " + myInfo.getPlayers().size());
		if(commandsReceived>=myInfo.getPlayers().size()){
			this.processCommands();
			if (!unitsPlaced){
				unitsPlaced = true;
				this.start();
			}
		}		
	}

	public void processCommands(){
		System.out.println("All commands received! Sending to model! Numcommands = " + turnCommands.getCommands().size());
		myModel.performCommands(turnCommands);
		turnCommands.clear();
		commandsReceived = 0;
	}

	public void updateGame(Message m) {
		myServer.sendUpdatedGame(m, this);
		//this.editVision();
	}

	public void updateGame() {
		myServer.sendUpdatedGame(myGame, this);
		//this.editVision();
	}
	
	public void setGameState(GameState gs){
		myGame = gs;
	}

	public void editVision()
	{
	    System.out.println("editing vision now in servergame");
	    for (Player p : myGame.getPlayers())
	    {
	        GameMap map = new GameMap();
	        for (Territory t : myGame.getMap().getTerritories())
	        {
	            if (!t.getOwner().getName().equals(p.getName()) && !t.isAdjacentTo(p) && !t.hasSpy())
	            {//if territory is not yours AND you're not adjacent to it AND you don't have a spy there
	                //hide it from your view with a fogged territory
	                map.replaceTerritory(t); 
	            }
	        }
	        GameState individualGame = new GameState(map, myGame.getPlayers());
	        myServer.sendGameByPlayer(individualGame, p.getPlayer());
	    }
	}

	public SaveGame saveGame(){
		return new SaveGame(myInfo,myGame);
	}
	
	public GameState getSavedState(){
		return myServer.getSavedState(this);
	}

        public void endGame () 
        {
            myServer.endGame(myInfo.getName());
        }

}
