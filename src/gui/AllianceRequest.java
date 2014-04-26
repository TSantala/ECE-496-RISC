package gui;

import java.io.Serializable;

import gameElements.Player;
import server.Message;
import server.ObjectClient;
import server.ObjectServer;

public class AllianceRequest extends Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player from;
	private Player to;
	
	public AllianceRequest(Player f, Player t){
		from = f;
		to = t;
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		os.sendAllianceRequest(this);
		return true;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		oc.receiveAllianceRequest(this);
	}
	
	public Player getFrom(){
		return from;
	}
	
	public Player getTo(){
		return to;
	}

}
