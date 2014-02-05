package gameElements;

import java.util.List;

import server.ServerConstants;

public class ServerGame implements ServerConstants {

	private GameState myGame;

	public ServerGame(GameState gs){
		myGame = gs;
	}

	public GameState getGameState(){
		return myGame;
	}
	
	public void performCommands(CommandList cl){

		List<Command> moveCommands = cl.getCommands(MoveCommand.class);
		for(Command move : moveCommands){
			move.enact(this);
			cl.removeCommand(move);
		}
		// In this implementation, only Attack commands are now left.
		// first check for simple place-swaps ... full commits to each other's territory.
		for(Command attack : cl.getCommands()){
			
		}
	}

	public void move(int from, int to, List<Unit> units){
		if(myGame.getMap().hasPath(from,to,units.get(0).getOwner())){
			myGame.getMap().getTerritory(from).removeUnits(units);
			myGame.getMap().getTerritory(to).addUnits(units);
		}
		else{
			// ERROR: invalid input. Should never occur (check for this on client-side first).
			// Undo all changes (will store server-side gamestate before a final is sent out).
		}
	}

	public void attack(int from, int to, List<Unit> units){
		if(myGame.getMap().canAttack(from,to,units.get(0).getOwner())){
			List<Unit> opposingUnits = myGame.getMap().getTerritory(to).getUnits();
			while(!units.isEmpty() && !opposingUnits.isEmpty()){
				Unit offense = units.get(units.size()-1);					// Pick final unit in arraylist for faster runtime.
				Unit defense = opposingUnits.get(opposingUnits.size()-1);
				if(Math.ceil(ATTACK_DIE*Math.random()) > Math.ceil(ATTACK_DIE*Math.random())){
					defense.getOwner().removeUnit(defense);
					myGame.getMap().getTerritory(to).removeUnit(defense);
				}
				else{
					offense.getOwner().removeUnit(offense);
					myGame.getMap().getTerritory(from).removeUnit(offense);
				}
			}
		}
		else{
			// ERROR: invalid input. Should never occur (check for this on client-side first).
			// Undo all changes (will store server-side gamestate before a final is sent out).
		}
	}

}
