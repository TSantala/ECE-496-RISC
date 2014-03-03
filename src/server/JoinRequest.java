package server;

public class JoinRequest extends Message{
	protected int myNum;

	public JoinRequest(int gameNum) {
		myNum = gameNum;
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		os.joinGame(myNum, myOS);
		return false;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		// shouldn't happen
	}

}
