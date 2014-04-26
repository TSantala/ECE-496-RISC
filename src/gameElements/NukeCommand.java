package gameElements;

import java.io.Serializable;

public class NukeCommand extends Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NukeCommand(Territory target, Player attacker){
		myTerritoryTo = target;
		myPlayer = attacker;
	}

	@Override
	public void enact(GameModel sg) {
		sg.nuclearAttack(myPlayer, myTerritoryTo);
	}

	public String toString(){
		String temp  = "Nuke: " +  myTerritoryTo.getID()+"\n";
		return temp;
	}

}
