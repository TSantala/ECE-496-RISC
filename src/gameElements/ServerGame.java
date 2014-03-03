package gameElements;

import server.ObjectServer;
import server.ServerPlayer;

public class ServerGame extends Thread {
	private GameInfo myInfo;
	private GameState myGame;
	private ObjectServer myServer;
	private int commandsReceived=0;
	private GameModel myModel;
	private CommandList turnCommands = new CommandList();

	public ServerGame(String gameName, int numPlayers, ObjectServer os) {
		myInfo = new GameInfo(gameName, numPlayers);
		myServer = os;
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;

		while (true) {
		    //perform db poll/check
		    elapsedTime = System.currentTimeMillis() - startTime;
		    if (elapsedTime > 1*10*1000){
		    	startTime = System.currentTimeMillis();
		    	System.out.println("It's been 10 seconds! processing commands");
		    	this.processCommands();
		    }
		}
		
	}

	public GameInfo getInfo(){
		return myInfo;
	}

	public void addPlayer(ServerPlayer p){
		if(myInfo.addPlayer(p)){
			myGame = new GameState(myInfo.getPlayers());
			myModel = new GameModel(myGame, this);
			this.updateGame();
			this.start();
		}
	}

	public void receiveCommandList(CommandList ls){
		turnCommands.addCommands(ls.getCommands());
		commandsReceived++;
		System.out.println(commandsReceived + " " + myInfo.getPlayers().size());
		if(commandsReceived==myInfo.getPlayers().size()){
			this.processCommands();
		}		
	}
	
	public void processCommands(){
		System.out.println("All commands received! Sending to model! Numcommands = " + turnCommands.getCommands().size());
		myModel.performCommands(turnCommands);
		turnCommands.clear();
		commandsReceived = 0;
	}

	public void updateGame() {
		myServer.sendUpdatedGame(myGame, this);
	}

}
