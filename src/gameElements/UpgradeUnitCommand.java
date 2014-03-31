package gameElements;
/*
 * Currently used for initialization
 * Will be used for add unit phase in implementation 2
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UpgradeUnitCommand extends Command implements Serializable {

	private static final long serialVersionUID = 1L;

	public UpgradeUnitCommand(List<Unit> lu){
		myUnits = new ArrayList<Unit>(lu);
	}

	public String toString(){
		String temp  = "Upgrade " +  myUnits.size() + " units.\n";
		return temp;
	}

	@Override
	public void enact(GameModel gm) {
		gm.upgradeUnits(myUnits);
	}
}
