package server;

public class PromptUnits extends Message{

	public PromptUnits() {
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		// should never happne
		return false;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		oc.promptTerritories();
		
	}

}
