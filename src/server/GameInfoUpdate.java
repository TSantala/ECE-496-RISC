package server;

import gameElements.ServerGame;
import gameElements.GameInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameInfoUpdate extends Message{
	private Collection<GameInfo> myUpdate = new ArrayList<GameInfo>();

	public GameInfoUpdate(List<ServerGame> myGames) {
		for(ServerGame game : myGames)
			myUpdate.add(game.getInfo());
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		// should not happen
		return false;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		oc.updateGameInfo(myUpdate);
	}

}
