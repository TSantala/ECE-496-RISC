package server;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class InitialConnect extends Message implements Serializable{
	
	private static final long serialVersionUID = 8285448565930920999L;
	private String myID;
	private String myPass;

	public InitialConnect() {
		myID =  JOptionPane.showInputDialog("What is your name?" ,"Player 1");
		myPass =  JOptionPane.showInputDialog("What is your password?" ,"password");
	}

	public InitialConnect(String name, String pass) {
		myID = name;
		myPass = pass;
	}

	@Override
	public boolean sendMessageToServer(ObjectServer os) {
		os.newPlayer(myID, myPass, myOS);
		return false;
	}

	@Override
	public void sendMessageToClient(ObjectClient oc) {
			oc.setPlayer(myID, myPass);
	}

}
