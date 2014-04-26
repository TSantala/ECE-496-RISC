package gui;

import gameElements.Command;
import gameElements.GameModel;
import gameElements.Player;

public class DiplomacyCommand extends Command {

	private Player otherPlayer;
	private boolean allies;
	
	public DiplomacyCommand(Player from, Player to, boolean a){
		myPlayer = from;
		otherPlayer = to;
		allies = a;
	}
	
	@Override
	public void enact(GameModel sg) {
		// handled in server commandlist loop.
	}
	
	public Player getOtherPlayer(){
		return otherPlayer;
	}
	
	public boolean nowAlly(){
		return allies;
	}

}
