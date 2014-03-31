package gameElements;
/*
 * Currently used for initialization
 * Will be used for add unit phase in implementation 2
 */
import java.io.Serializable;

public class UpgradePlayerCommand extends Command implements Serializable {

	private static final long serialVersionUID = 1L;

	public UpgradePlayerCommand(Player p){
		myPlayer = p;
	}

	public String toString(){
		String temp  = "Upgrade " +  myPlayer.getName() + ".\n";
		return temp;
	}

	@Override
	public void enact(GameModel gm) {
		gm.upgradePlayer(myPlayer);
	}
}
