package server;

public class LeaveRequest extends Message{

	public LeaveRequest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		os.leaveGame(myOS);
		return false;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		// should not happen
	}

}
