package server;

import java.io.Serializable;

public abstract class Message implements Serializable {

	private static final long serialVersionUID = 11L;
	protected ObjectSocket myOS;
	
	public abstract boolean sendMessageToServer(ObjectServer os);
	public abstract void sendMessageToClient(ObjectClient oc);
	
	public void setObjectSocket(ObjectSocket os){
		myOS = os;
	}
	
	public ObjectSocket getObjectSocket(){
		return myOS;
	}

}
