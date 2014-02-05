package main;

<<<<<<< HEAD
//import javax.swing.SwingUtilities;
//import gameElements.Initialization;
import gui.GameGUI;
import server.ObjectClient;
import server.ObjectServer;
//import server.Server;
//import server.SocketClient;
=======
import javax.swing.SwingUtilities;

import gameElements.Initialization;
import server.ObjectClient;
import server.ObjectServer;
import server.Server;
//import gui.GameGUI;
import server.SocketClient;
>>>>>>> a00132ffcb4c5bf42fd7261edf697a98005dc2e4

public class Main {

	public static void main(String[] args){
		//Initialization i = new Initialization();
		//SwingUtilities.invokeLater(i);
		//new Server();
<<<<<<< HEAD

		new GameGUI();
=======
		
//		new GameGUI();
>>>>>>> a00132ffcb4c5bf42fd7261edf697a98005dc2e4

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
