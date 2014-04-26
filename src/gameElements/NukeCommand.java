package gameElements;

import java.io.Serializable;

public class NukeCommand extends Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NukeCommand(Territory target){
		myTerritoryTo = target;
	}

	@Override
	public void enact(GameModel sg) {
		sg.nuclearAttack(myTerritoryTo);
	}

	public String toString(){
		String temp  = "Nuke: " +  myTerritoryTo.getID()+"\n";
		return temp;
	}

}
