package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import gameElements.GameClient;
import gameElements.GameServer;
import gameElements.UmbraRoom;

public class Main {
	private final static int UMBRA_PORT = 30480;

	public static void main(String[] args){
		System.out.println("Wahoo!");
		// new RiscGame();
		(new Thread(new UmbraRoom())).start();
		try {
			ServerSocket ss=new ServerSocket(UMBRA_PORT);
			Socket s = ss.accept();
			new GameClient(new Socket());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("shit, done fucked up");
		}
	}

}
