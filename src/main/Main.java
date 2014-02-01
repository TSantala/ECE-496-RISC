package main;

import javax.swing.SwingUtilities;

import gameElements.Initialization;
import server.Server;
import server.SocketClient;

public class Main {

	public static void main(String[] args){
		Initialization i = new Initialization();
		SwingUtilities.invokeLater(i);
		//new Server();

		Server myServer = new Server();
		myServer.start();
		SocketClient myClient = new SocketClient();
		myClient.start();
	}
}
