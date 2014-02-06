package main;

//import javax.swing.SwingUtilities;
//import gameElements.Initialization;
import javax.swing.SwingUtilities;

import gameElements.Initialization;
import gui.GameGUI;
import server.ObjectClient;
import server.ObjectServer;
//import server.Server;
//import server.SocketClient;

public class Main {

	public static void main(String[] args){
		ObjectServer myServer = new ObjectServer();
		myServer.start();
		ObjectClient myClient = new ObjectClient();
		myClient.start();
		//new Server();
		
//		new GameGUI();

		/*Server myServer = new Server();
		myServer.start();
		SocketClient myClient = new SocketClient();
		myClient.start();*/

		/*
		*/
	}
}
