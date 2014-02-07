package server;

import java.io.Serializable;

public abstract class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public abstract boolean sendMessageToServer(ObjectServer os);
	public abstract void sendMessageToClient(ObjectClient oc);

}
