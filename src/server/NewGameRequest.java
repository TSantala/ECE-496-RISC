package server;

public class NewGameRequest extends JoinRequest{
	private String myName;

	public NewGameRequest(int numPlayers, String gameName) {
		super(numPlayers);
		myName = gameName;
	}
	
	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		os.BeginNewGame(myNum, myName);
		return false;
	}

}
