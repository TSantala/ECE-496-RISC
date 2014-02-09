package server;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class InitialConnect extends Message implements Serializable{
	
	private static final long serialVersionUID = 8285448565930920999L;
	private String myID;
	private boolean host = false;
	private int numPlayers = 0;

	public InitialConnect() {
		myID =  JOptionPane.showInputDialog("What is your name?" ,"Player 1");
	}
	
	public InitialConnect(String id){
		myID = id;
	}
	
	public String getName(){
		return myID;
	}
	
	public void setHost(){
		host = true;
	}
	
	public void setNumPlayers(int num){
		numPlayers = num;
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		if (host){
			os.setNumPlayers(numPlayers);
		}
		else {
			
		}
		return false;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
		if(host){
			oc.promptPlayers();
		}
		else {
			oc.setPlayer(myID);
			oc.promptTerritories();
		}
		
	}

}
