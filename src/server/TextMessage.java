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
		//System.out.println("Message received!!! OSocket 59");
		if (myMessage.equals("exit")){
			return false;
		}
		if(myMessage.startsWith("/w")){
			String[] splitMessage = myMessage.split(" ");
			String playerTo = splitMessage[1];
			String toSend = "WHISPER - "+os.getPlayerName(myOS)+":";
			for(int i = 2; i<splitMessage.length; i++)
				toSend+=" "+splitMessage[i];
			boolean success = os.sendWhisper(playerTo,toSend);
			if(success) os.sendWhisper(os.getPlayerName(myOS),"Your whisper went through!");
			else os.sendWhisper(os.getPlayerName(myOS),"Whisper failed: player not found.");
			return true;
		}
		os.broadCastMessage(new TextMessage(myMessage),myOS);
		return true;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		oc.printMessage(myMessage);
	}
}
