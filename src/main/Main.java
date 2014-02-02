package main;

<<<<<<< HEAD
import javax.swing.SwingUtilities;

import gameElements.Initialization;
import server.ObjectClient;
import server.ObjectServer;
import server.Server;
=======
import gui.GameGUI;
>>>>>>> 2bf1e85e92b83adb8c9df8a860a0876a8c44f441
import server.SocketClient;

public class Main {

	public static void main(String[] args){
		//Initialization i = new Initialization();
		//SwingUtilities.invokeLater(i);
		//new Server();
		
		new GameGUI();

<<<<<<< HEAD
		/*Server myServer = new Server();
		myServer.start();
		SocketClient myClient = new SocketClient();
		myClient.start();*/
		
		ObjectServer myServer = new ObjectServer();
		myServer.start();
		ObjectClient myClient = new ObjectClient();
		myClient.start();
=======
		//Server myServer = new Server();
		//myServer.start();
		
		//SocketClient myClient = new SocketClient();
		//myClient.start();
>>>>>>> 2bf1e85e92b83adb8c9df8a860a0876a8c44f441
	}
}
