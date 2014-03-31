package main;

import server.ObjectClient;
import server.ObjectServer;

public class Main {

	public static void main(String[] args){
		
		ObjectServer myServer = new ObjectServer(false);
		myServer.start();

	}
}
