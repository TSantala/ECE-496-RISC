package gameElements;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import server.*;

public class GameModel implements ServerConstants, Serializable {

	private static final long serialVersionUID = 1L;
	private ServerGame myServerGame;
	private GameState myGame;
	private int unitID=0;

	public GameModel(GameState gs, ServerGame sg){
		myGame = gs;
		myServerGame = sg;
	}

	public GameState getGameState(){
		return myGame;
	}

	public void performCommands(CommandList cl){

		for(Player p : myGame.getPlayers())
			System.out.println("Food:"+p.getFoodAmount());

		cl = this.createServerCommandList(cl);

		//		List<Command> tradeOrders = cl.getCommands(TradeCommand.class);
		//		for(Command c: tradeOrders)
		//		{
		//		    this.trade(c);
		//		}

		//		List<Command> allianceOrders = cl.getCommands(AllianceCommand.class);
		//		for(Command c : allianceOrders)
		//		{
		//		    this.makeAlliance(c);
		//		}

		List<Command> spies = cl.getCommands(SpyCommand.class);
		for(Command c : spies){
			this.makeSpies(c.getUnits());
			cl.removeCommand(c);
		}

		List<Command> upgradePlayers = cl.getCommands(UpgradePlayerCommand.class);
		for(Command c : upgradePlayers){
			this.upgradePlayer(c.getPlayer());
			cl.removeCommand(c);
		}

		List<Command> upgradeUnits = cl.getCommands(UpgradeUnitCommand.class);
		for(Command upgrade : upgradeUnits)
		{
			this.upgradeUnits(upgrade.getUnits());
			cl.removeCommand(upgrade);
		}

		List<Command> moveCommands = cl.getCommands(MoveCommand.class);
		for(Command move : moveCommands){
			//if(!(this.move(move.getPlayer(),move.getFrom(),move.getTo(),move.getUnits()))) return;
			this.move(move.getPlayer(),move.getFrom(),move.getTo(),move.getUnits());
			cl.removeCommand(move);
		}

		List<Command> attackCommands = cl.getCommands(AttackCommand.class);

		// first check validity of attacks.
		attackCommands = this.checkValidAttacks(attackCommands);
		//if(!checkValidAttacks(cl.getCommands())) return;

		// check if attack swaps and enact them.  ******CHECK MIDCOMBAT ATTACKS NOW*******
		this.checkAttackSwaps(attackCommands);

		// attacking units don't defend; remove them from the territories.
		this.displaceAttackingUnits(attackCommands);

		// combine attack commands from same player to same destination.
		this.combineGroupAttacks(attackCommands);

		// attack!
		for(Command attack : attackCommands)
			attack.enact(this);

		List<Command> intCommands = cl.getCommands(InterceptorCommand.class);
		for(Command interceptor : intCommands)
			interceptor.enact(this);

		List<Command> nukeCommands = cl.getCommands(NukeCommand.class);
		for(Command nuke : nukeCommands)
			nuke.enact(this);

		// add 1 unit to each territory.
		this.endOfRoundAddUnits();

		// feed units and remove if out of food
		this.feedUnits();

		// harvest resources from owned territories
		this.harvestTerritories();

		this.catchSpies();

		for(Player p : myGame.getPlayers())
			p.checkNukesReady();

		// return updated game after commands enacted to clients.
		this.sendUpdatedGameState();

		this.checkWin();

	}

	private CommandList createServerCommandList(CommandList cl) {
		CommandList toReturn = new CommandList();

		List<Command> playerCommands = cl.getCommands(UpgradePlayerCommand.class);
		List<Command> moveCommands = cl.getCommands(MoveCommand.class);
		List<Command> placeCommands = cl.getCommands(AddUnitCommand.class);
		List<Command> unitCommands = cl.getCommands(UpgradeUnitCommand.class);
		List<Command> spyCommands = cl.getCommands(SpyCommand.class);
		List<Command> nukeCommands = cl.getCommands(NukeCommand.class);
		List<Command> intCommands = cl.getCommands(InterceptorCommand.class);
		List<Command> allyCommands = cl.getCommands(DiplomacyCommand.class);

		
		for(Command c : placeCommands){
			cl.removeCommand(c);
			for(int i = 0; i < c.getUnits().size(); i++){
				this.addNewUnit(myGame.getMap().getTerritory(c.getFrom().getID()),
						myGame.getPlayer(c.getPlayer().getName()));
			}
		}
		
		for(Command c : allyCommands){
			cl.removeCommand(c);
			DiplomacyCommand dc = (DiplomacyCommand) c;
			this.setDiplomacy(myGame.getPlayer(dc.getPlayer().getName()), 
					myGame.getPlayer(dc.getOtherPlayer().getName()), dc.nowAlly());
		}
		
		for(Command c : nukeCommands){
			cl.removeCommand(c);
			toReturn.addCommand(new NukeCommand(myGame.getMap().getTerritory(c.getTo().getID())));
		}
		
		for(Command c : intCommands){
			cl.removeCommand(c);
			toReturn.addCommand(new InterceptorCommand(myGame.getMap().getTerritory(c.getTo().getID()),
					myGame.getPlayer(c.getPlayer().getName())));
		}

		for(Command c : spyCommands){
			cl.removeCommand(c);
			toReturn.addCommand(new SpyCommand(this.getServerUnits(c.getUnits())));
		}

		for(Command c : playerCommands){
			cl.removeCommand(c);
			toReturn.addCommand(new UpgradePlayerCommand(myGame.getPlayer(c.getPlayer().getName())));
		}

		for(Command c : unitCommands){
			cl.removeCommand(c);
			toReturn.addCommand(new UpgradeUnitCommand(this.getServerUnits(c.getUnits())));
		}

		for(Command c : moveCommands){
			cl.removeCommand(c);
			toReturn.addCommand(new MoveCommand(myGame.getPlayer(c.getPlayer().getName()),
					myGame.getMap().getTerritory(c.getFrom().getID()),
					myGame.getMap().getTerritory(c.getTo().getID()),
					this.getServerUnits(c.getUnits())));
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

	public void move(Player p, Territory from, Territory to, List<Unit> units){
		boolean allSpies = true;
		for (Unit u : units)
		{
			if (!u.isSpy())
			{
				allSpies = false;
				break;
			}
		}
		System.out.println("ALL SPIES? " + allSpies);
		boolean ownersNotDifferent = !to.getOwner().getName().equals(from.getOwner().getName());
		System.out.println(ownersNotDifferent + " OWNERS ARE DIFFERENT?");
		if (allSpies) //spies moving
		{
			if (from.getNeighbors().contains(to)) //is 1 away
			{
				from.removeUnits(units);
				to.addUnits(units);
				return;
			}
		}
		else if(myGame.getMap().hasPath(from,to,p) && to.getOwner().onTeam(from.getOwner()))
		{
			from.removeUnits(units);
			to.addUnits(units);
		}

		else
		{
			//this.redoTurnErrorFound("Invalid move!");
			this.broadcastGameMessage("Player "+p.getName()+" has committed an invalid move. Ignoring command.");		
		}
	}

	public void attack(Player p, Territory from, Territory to, List<Unit> units){

		List<Unit> opposingUnits = (List<Unit>) to.getUnits();
		Player opponent = to.getOwner();

		if(opposingUnits.size()!=0){

			if(p.getName().equals(opponent.getName())){
				// swap must have occurred, you own it already!
				this.move(p, from, to, units);
			}
			while(!units.isEmpty() && !opposingUnits.isEmpty()){
				Unit offense = units.get(units.size()-1);					// Pick final unit in arraylist for faster runtime.
				Unit defense = opposingUnits.get(opposingUnits.size()-1);
				if(Math.ceil(ATTACK_DIE*Math.random()) + offense.getCombatBonus() > 
				Math.ceil(ATTACK_DIE*Math.random()) + defense.getCombatBonus()){
					System.out.println("ATTACKER wins!");
					opponent.removeUnit(defense);
					to.removeUnit(defense);
				}
				else{
					System.out.println("Defender wins!");
					p.removeUnit(offense);
					units.remove(offense);
				}
				opposingUnits = to.getUnits();
			}
		}
		if(!units.isEmpty()){
			System.out.println("Territory has been conquered!");
			p.addTerritory(to);
			opponent.removeTerritory(to);
			to.addUnits(units);
		}
		else{
			System.out.println("Defender has survived!");
		}
	}


	/*
	 * Returns winner of the middleAttack
	 */
	public void middleAttack(Player p, Territory from, Territory to, List<Unit> units)
	{
		List<Unit> opposingUnits = to.getUnits();
		Player opponent = to.getOwner();

		if(opposingUnits.size()!=0){

			while(!units.isEmpty() && !opposingUnits.isEmpty()){
				Unit offense = units.get(units.size()-1);                    // Pick final unit in arraylist for faster runtime.
				Unit defense = opposingUnits.get(opposingUnits.size()-1);
				if(Math.ceil(ATTACK_DIE*Math.random()) > Math.ceil(ATTACK_DIE*Math.random())){
					System.out.println("ATTACKER wins!");
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
		else if (units.isEmpty()){ //aggressor lost
			opponent.addTerritory(to);
			p.removeTerritory(to);
			to.addUnits(opposingUnits);
		}
	}

	public List<Command> checkValidAttacks(List<Command> cl){
		List<Command> toReturn = new ArrayList<Command>();
		for(Command c : cl)
		{
			if(!myGame.getMap().canAttack(c.getFrom(),c.getTo(),c.getPlayer()))
			{
				//this.redoTurnErrorFound("Invalid attack!");
				this.broadcastGameMessage("Player "+c.getPlayer().getName()+" has committed an invalid attack. Ignoring command.");
			}
			else{
				toReturn.add(c);
			}
		}
		return toReturn;
	}

	public void checkAttackSwaps(List<Command> cl){
		for(int i = 0; i<cl.size()-1;i++){
			for(int j = i+1; j<cl.size();j++){
				Command attackA = cl.get(i);
				Command attackB = cl.get(j);
				if(attackA.getTo() == attackB.getFrom() && attackA.getFrom() == attackB.getTo())
				{
					// are attacking one another, no longer swap, now fight
					Territory terA = attackA.getFrom();
					Territory terB = attackB.getFrom();
					System.out.println("MID ATTACK OCCURRED");
					// are committing all units. Should attack in mid!
					Player attackingPlayer;
					if(Math.random() < 0.5)
					{
						attackingPlayer = attackA.getPlayer();
						middleAttack(attackingPlayer, terA, terB, terA.getUnits());
					}
					else
					{
						attackingPlayer = attackB.getPlayer();
						middleAttack(attackingPlayer, terB, terA, terB.getUnits());
					}
					// remove the mid attacks from commandlist.
					cl.remove(j);
					cl.remove(i);
					System.out.println("MID ATTACKS FINISHED");
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
		t.addUnit(new Unit(unitID++,t.getOwner()));
	}

	private void addNewUnit(Territory t, Player p){
		t.addUnit(new Unit(unitID++,p));
	}

	private void endOfRoundAddUnits(){
		for(Territory t : myGame.getMap().getTerritories()){
			this.addNewUnit(t);
		}
	}

	private void feedUnits(){
		List<Unit> toRemove = new ArrayList<Unit>();
		for(Territory t : myGame.getMap().getTerritories()){
			Player p = t.getOwner();
			for(Unit u : t.getUnits()){
				if(!p.feedUnit()){
					toRemove.add(u);
				}
			}
			while(!toRemove.isEmpty()){
				Unit removing = toRemove.remove(0);
				t.removeUnit(removing);
				removing.getOwner().removeUnit(removing);
			}
		}
	}

	private void harvestTerritories(){
		for(Territory t : myGame.getMap().getTerritories()){
			t.harvestResources();
		}
	}

	private void catchSpies(){
		List<Unit> toKill = new ArrayList<Unit>();
		List<Territory> killOn = new ArrayList<Territory>();
		for (Territory t : myGame.getMap().getTerritories()){
			for (Unit u : t.getUnits()){
				if(u.isSpy()){
					int percentChance = u.getTurnCount()*7+1;
					if(Math.random()*100 < percentChance){
						toKill.add(u);
						killOn.add(t);
					}
					else {
						u.setTurnCount(u.getTurnCount()+1);
					}
				}
			}
		}
		while(!toKill.isEmpty())
			this.killSpy(toKill.remove(0), killOn.remove(0));
	}

	private void killSpy(Unit spy, Territory territory){
		territory.removeUnit(spy);
	}

	private void sendUpdatedGameState(){
		System.out.println("Model logic completed!!!");

		myServerGame.setGameState(myGame);
		myServerGame.updateGame();
	}

	public void upgradeUnits(List<Unit> l) {
		for(Unit u : l)
			myGame.upgradeUnit(u);
	}

	public void upgradePlayer(Player p){
		myGame.upgradePlayer(p);
		if (p.getTechLevel() == 6){
			this.broadcastGameMessage("A PLAYER NOW HAS NUKES. YOU ARE NOT PREPARED.");
		}
	}

	public void broadcastGameMessage(String message){
		myServerGame.updateGame(new TextMessage(message));
	}

	public void makeSpies(List<Unit> l) {
		for(Unit u : l)
			myGame.makeSpy(u);
	}

	public void checkWin(){
		int numAlive = myGame.getPlayers().size();
		for (Player p : myGame.getPlayers()){
			if (p.getTerritories().size() == 0){
				numAlive--;
			}
		}
		if (numAlive == 1){
			String name = myGame.getMap().getTerritories().get(0).getOwner().getName();
			this.broadcastGameMessage(name + " HAS WON!  CONGRATULATIONS!");
			myServerGame.endGame();
		}
	}

	public void nuclearAttack(Territory target) {
		if(target.hasInterceptors()>0){
			this.broadcastGameMessage("A nuclear missile has been disarmed by an interceptor");
			target.removeInterceptor();
			return;
		}
		this.broadcastGameMessage("NUCLEAR MISSILE HAS LANDED. Ouch...");
		List<Unit> toRemove = new ArrayList<Unit>();
		for(Unit u : target.getUnits()){
			toRemove.add(u);
		}
		while(!toRemove.isEmpty()){
			Unit u = toRemove.remove(0);
			u.getOwner().removeUnit(u);
			target.removeUnit(u);
		}
		target.getOwner().removeTerritory(target);
		myGame.getMap().destroyTerritory(target);
	}

	public void buyInterceptor(Territory to, Player p) {
		if(p.getTechAmount() >= 10){
			p.adjustResource(new Technology(-10));
			to.placeInterceptor();
		}
	}

	public void setDiplomacy(Player myPlayer, Player otherPlayer, boolean allies) {
		if(allies){
			myPlayer.addAlly(otherPlayer);
			otherPlayer.addAlly(myPlayer);
		}
		else{
			myPlayer.removeAlly(otherPlayer);
			otherPlayer.removeAlly(myPlayer);
		}
	}

}
