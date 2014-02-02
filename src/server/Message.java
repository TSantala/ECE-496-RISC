package server;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 8672323596598488884L;
	private String myMessage;

	public Message(String str) {
		myMessage = str;
	}
	
	public String getMessage(){
		return myMessage;
	}
}
