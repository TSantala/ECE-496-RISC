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
		this.checkValidAttacks(cl.getCommands());
		this.checkAttackSwaps(cl.getCommands());
		this.displaceAttackingUnits(cl.getCommands());
	}

	public void move(Player p, Territory from, Territory to, List<Unit> units, boolean swapOverride){
		if(myGame.getMap().hasPath(from,to,p) || swapOverride){
			from.removeUnits(units);
			to.addUnits(units);
		}
		else{
			// ERROR: invalid input. Should never occur (check for this on client-side first).
			// Undo all changes (will store server-side gamestate before a final is sent out).
		}
	}

	public void attack(Player p, Territory from, Territory to, List<Unit> units){
		List<Unit> opposingUnits = to.getUnits();
		Player opponent = opposingUnits.get(0).getOwner();
		if(p.getName().equals(opponent.getName())){
			// swap must have occurred, you own it!
			this.move(p, from, to, units, false);
		}
		while(!units.isEmpty() && !opposingUnits.isEmpty()){
			Unit offense = units.get(units.size()-1);					// Pick final unit in arraylist for faster runtime.
			Unit defense = opposingUnits.get(opposingUnits.size()-1);
			if(Math.ceil(ATTACK_DIE*Math.random()) > Math.ceil(ATTACK_DIE*Math.random())){
				opponent.removeUnit(defense);
				to.removeUnit(defense);
			}
			else{
				p.removeUnit(offense);
			}
		}
		if(!units.isEmpty()){
			p.addTerritory(to);
			to.addUnits(units);
		}
	}

	public void checkAttackSwaps(List<Command> cl){
		for(int i = 0; i<cl.size()-1;i++){
			for(int j = i+1; j<cl.size();j++){
				Command attackA = cl.get(i);
				Command attackB = cl.get(j);
				if(attackA.getTo() == attackB.getFrom() && attackA.getFrom() == attackB.getTo()){
					// are attacking one another.
					Territory terA = attackA.getFrom();
					Territory terB = attackB.getFrom();
					if(terA.getUnits().size() == attackA.getUnits().size() && terB.getUnits().size() == attackA.getUnits().size()){
						// are committing all units. Should swap!
						move(attackA.getPlayer(),terA,attackA.getTo(),attackA.getUnits(),true);
						attackA.getPlayer().removeTerritory(terA);
						attackA.getPlayer().addTerritory(terB);
						
						move(attackB.getPlayer(),terB,attackB.getTo(),attackB.getUnits(),true);
						attackB.getPlayer().removeTerritory(terB);
						attackB.getPlayer().addTerritory(terA);
						
						// remove the swap-attacks from commandlist.
						cl.remove(i);
						cl.remove(j);
					}
				}
			}
		}
	}

	public void checkValidAttacks(List<Command> cl){
		for(Command c : cl){
			if(!myGame.getMap().canAttack(c.getFrom(),c.getTo(),c.getPlayer())){
				// ERROR !!! Revert state and re-do round.
			}					
		}
	}
	
	public void displaceAttackingUnits(List<Command> cl){
		for(Command c : cl){
			for(Unit u : c.getUnits()){
				c.getFrom().removeUnit(u);
			}
		}
	}

}
