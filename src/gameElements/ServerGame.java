package gameElements;

import java.util.ArrayList;
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
		
		cl = this.createServerCommandList(cl);
		
		List<Command> moveCommands = cl.getCommands(MoveCommand.class);
		for(Command move : moveCommands){
			move.enact(this);
			cl.removeCommand(move);
		}
		// In this first implementation, only Attack commands are now left.
		// first check validity of attacks.
		this.checkValidAttacks(cl.getCommands());
		// check if attack swaps and enact them.
		this.checkAttackSwaps(cl.getCommands());
		// attacking units don't defend; remove them from the territories.
		this.displaceAttackingUnits(cl.getCommands());
		// combine attack commands from same player to same destination.
		this.combineGroupAttacks(cl.getCommands());
		
		for(Command attack : cl.getCommands())
			attack.enact(this);
	}

	private CommandList createServerCommandList(CommandList cl) {
		CommandList toReturn = new CommandList();
		List<Command> moveCommands = cl.getCommands(MoveCommand.class);
		for(Command c : moveCommands){
			cl.removeCommand(c);
			toReturn.addCommand(new MoveCommand(myGame.getPlayer(c.getPlayer().getName()),
					myGame.getMap().getTerritory(c.getFrom().getID()),
					myGame.getMap().getTerritory(c.getTo().getID()),
					this.getServerUnits(c.getUnits())));
		}
		for(Command c : cl.getCommands()){
			toReturn.addCommand(new AttackCommand(myGame.getPlayer(c.getPlayer().getName()),
					myGame.getMap().getTerritory(c.getFrom().getID()),
					myGame.getMap().getTerritory(c.getTo().getID()),
					this.getServerUnits(c.getUnits())));
		}
		return toReturn;
	}

	private List<Unit> getServerUnits(List<Unit> units) {
		List<Unit> toReturn = new ArrayList<Unit>();
		for(Unit u : units){
			toReturn.add(myGame.getUnit(u.getID()));
		}
		return toReturn;
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
			// swap must have occurred, you own it already!
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

	public void checkValidAttacks(List<Command> cl){
		for(Command c : cl){
			if(!myGame.getMap().canAttack(c.getFrom(),c.getTo(),c.getPlayer())){
				// ERROR !!! Revert state and re-do round.
			}					
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
	
	public void displaceAttackingUnits(List<Command> cl){
		for(Command c : cl){
			for(Unit u : c.getUnits()){
				c.getFrom().removeUnit(u);
			}
		}
	}
	
	private void combineGroupAttacks(List<Command> cl) {
		List<Command> newList = new ArrayList<Command>();
		List<Integer> added = new ArrayList<Integer>();
		for(int i = 0; i<cl.size()-1;i++){
			if(added.contains(i)) continue;
			Command attackA = cl.get(i);
			for(int j = 0; j<cl.size();j++){
				if(i==j) continue;
				Command attackB = cl.get(j);
				if(attackA.getPlayer().getName().equals(attackB.getPlayer().getName()) 
						&& attackA.getTo().getID() == attackB.getTo().getID()){
						// same player attacking same place.
						attackA.getUnits().addAll(attackB.getUnits());
						added.add(j);
				}
			}
			newList.add(attackA);
		}
		cl.clear();
		cl.addAll(newList);
	}

}
