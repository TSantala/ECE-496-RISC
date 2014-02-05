package gameElements;

import java.util.List;

public class ServerGame {

	private GameState myGame;

	public ServerGame(GameState gs){
		myGame = gs;
	}

	public GameState getGameState(){
		return myGame;
	}

	public void move(int from, int to, List<Unit> units){
		if(myGame.getMap().hasPath(from,to)){
			myGame.getMap().getTerritory(from).removeUnits(units);
			myGame.getMap().getTerritory(to).addUnits(units);
		}
		else{
			// ERROR: invalid input. Should never occur (check for this on client-side first).
		}
	}

	public void attack(int from, int to, List<Unit> units){

	}

}
