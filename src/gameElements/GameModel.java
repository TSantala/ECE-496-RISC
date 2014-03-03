package gameElements;

import java.util.ArrayList;
import java.util.List;

import server.ObjectServer;
import server.ServerConstants;

public class GameModel implements ServerConstants {

	private ServerGame myServer;
	private GameState myPrevious;
	private GameState myGame;
	private int unitID=0;

	public GameModel(GameState gs, ServerGame sg){
		myGame = gs;
		myPrevious = gs;
		myServer = sg;
	}

	public GameState getGameState(){
		return myGame;
	}

	public void placeUnits(Player p, Territory t, List<Unit> units){

		if (units.size() > 0){
			Player serverPlayer = myGame.getPlayer(p.getName());
			Territory serverTerritory = myGame.getMap().getTerritory(t.getID());
			serverPlayer.addUnits(units);
			serverTerritory.addUnits(units);
		}
	}

	public void performCommands(CommandList cl){
		
		myPrevious = myGame.clone();
		
		List<Command> placeCommands = cl.getCommands(AddUnitCommand.class);
		for(Command place : placeCommands){
			place.enact(this);
			cl.removeCommand(place);
		}

		cl = this.createServerCommandList(cl);
		
		for(Player p : myGame.getPlayers()){
			System.out.println(p.getName()+" units: "+p.getNumToFeed());
		}
		
		//for upgrading
//		List<Command> upgradeCommands = cl.getCommands(UpgradeCommand.class);
//		for(Command upgrade : upgradeCommands)
//		{
//		    upgrade.enact(this);
//		    cl.removeCommand(upgrade);
//		}

		List<Command> moveCommands = cl.getCommands(MoveCommand.class);
		for(Command move : moveCommands){
			if(!(this.move(move.getPlayer(),move.getFrom(),move.getTo(),move.getUnits(),false))) return;
			cl.removeCommand(move);
		}
		
		// In this first implementation, only Attack commands are now left.
		// first check validity of attacks.
		if(!checkValidAttacks(cl.getCommands())) return;
		// check if attack swaps and enact them.
		this.checkAttackSwaps(cl.getCommands());
		// attacking units don't defend; remove them from the territories.
		this.displaceAttackingUnits(cl.getCommands());
		// combine attack commands from same player to same destination.
		this.combineGroupAttacks(cl.getCommands());
		// attack!
		for(Command attack : cl.getCommands())
			attack.enact(this);
		// add 1 unit to each territory.

		this.endOfRoundAddUnitsAndResources();
		
		for(Player p : myGame.getPlayers()){
			System.out.println(p.getName()+" units: "+p.getNumToFeed());
		}
		
		// return updated game after commands enacted to clients.
		this.sendUpdatedGameState();
				
	}

	private CommandList createServerCommandList(CommandList cl) {
		CommandList toReturn = new CommandList();
		List<Command> moveCommands = cl.getCommands(MoveCommand.class);
		List<Command> placeCommands = cl.getCommands(AddUnitCommand.class);

		for(Command c : moveCommands){
			cl.removeCommand(c);
			toReturn.addCommand(new MoveCommand(myGame.getPlayer(c.getPlayer().getName()),
					myGame.getMap().getTerritory(c.getFrom().getID()),
					myGame.getMap().getTerritory(c.getTo().getID()),
					this.getServerUnits(c.getUnits())));
		}
		for(Command c : placeCommands){
			cl.removeCommand(c);
			for(Unit u : c.getUnits()){
				this.addNewUnit(myGame.getMap().getTerritory(c.getFrom().getID()));
			}
		}
		for(Command c : cl.getCommands()){
			System.out.println("In the attack loop");
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

	public boolean move(Player p, Territory from, Territory to, List<Unit> units, boolean swapOverride){
		if(myGame.getMap().hasPath(from,to,p) || swapOverride){
			from.removeUnits(units);
			to.addUnits(units);
		}
		else{
			this.redoTurnErrorFound("Invalid move!");
			return false;
		}
		return true;
	}

	public void attack(Player p, Territory from, Territory to, List<Unit> units){

		List<Unit> opposingUnits = to.getUnits();
		Player opponent = to.getOwner();
		
		if(opposingUnits.size()!=0){
			
			if(p.getName().equals(opponent.getName())){
				// swap must have occurred, you own it already!
				this.move(p, from, to, units, false);
			}
			while(!units.isEmpty() && !opposingUnits.isEmpty()){
				Unit offense = units.get(units.size()-1);					// Pick final unit in arraylist for faster runtime.
				Unit defense = opposingUnits.get(opposingUnits.size()-1);
				if(Math.ceil(ATTACK_DIE*Math.random()) > Math.ceil(ATTACK_DIE*Math.random())){
					System.out.println("Attacker wins!");
					opponent.removeUnit(defense);
					to.removeUnit(defense);
				}
				else{
					System.out.println("Defender wins!");
					p.removeUnit(offense);
					units.remove(offense);
				}
			}
		}
		if(!units.isEmpty()){
			p.addTerritory(to);
			opponent.removeTerritory(to);
			to.addUnits(units);
		}
	}

	public boolean checkValidAttacks(List<Command> cl){
		for(Command c : cl){
			if(!myGame.getMap().canAttack(c.getFrom(),c.getTo(),c.getPlayer())){
				this.redoTurnErrorFound("Invalid attack!");
				return false;
			}					
		}
		return true;
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
						System.out.println("SWAP OCCURRED");
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
						System.out.println("SWAP FINISHED");
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
		for(int i = 0; i<cl.size();i++){
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

	private void addNewUnit(Territory t){
	    //not sure if you want to consume food here timo, or consume at the end of every turn 
	    //if(t.getOwner().getFood != 0)  
		t.addUnit(new Unit(t.getOwner(),unitID++));
	}

	private void endOfRoundAddUnitsAndResources(){
		for(Territory t : myGame.getMap().getTerritories()){
			this.addNewUnit(t);
			t.harvestResources();
		}
	}

	private void redoTurnErrorFound(String message){
		System.out.println("AN ERROR HAS OCCURRED!!!: "+message);
		myGame = myPrevious;
		// return myGame (unaltered) to the clients, send error message, and request turn startover.
		sendUpdatedGameState();
	}

	private void sendUpdatedGameState(){
		System.out.println("Model logic completed!!!");
		myServer.updateGame();
	}

}
