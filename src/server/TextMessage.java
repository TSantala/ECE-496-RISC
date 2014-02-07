package server;

import java.io.Serializable;

public class TextMessage extends Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String myMessage;

	public TextMessage(String str) {
		myMessage = str;
	}
	
	public String getMessage(){
		return myMessage;
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		System.out.println("Message received!!! OSocket 59");
		if (myMessage.equals("exit")){
			return false;
		}
		os.broadCastMessage(new TextMessage(myMessage));
		return true;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		oc.printMessage(myMessage);
	}
}
