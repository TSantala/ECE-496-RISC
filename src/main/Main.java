package main;

import server.Server;
import server.SocketClient;

public class Main {

	public static void main(String[] args){
		// new RiscGame();
		Server myServer = new Server();
		myServer.start();
		SocketClient myClient = new SocketClient();
		myClient.start();
	}

}
