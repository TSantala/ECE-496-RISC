package main;

import javax.swing.SwingUtilities;

import gameElements.Initialization;
import server.ObjectClient;
import server.ObjectServer;
import server.Server;
import server.SocketClient;

public class Main {

	public static void main(String[] args){
		//Initialization i = new Initialization();
		//SwingUtilities.invokeLater(i);
		//new Server();

		/*Server myServer = new Server();
		myServer.start();
		SocketClient myClient = new SocketClient();
		myClient.start();*/
		
		ObjectServer myServer = new ObjectServer();
		myServer.start();
		ObjectClient myClient = new ObjectClient();
		myClient.start();
	}
}
