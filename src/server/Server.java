package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
	
	HashSet<MultipleSocketServer> myConnections = new HashSet<MultipleSocketServer>();
	
	public Server(){
		
		int port = 19999;
		int count = 0;
		try{
			ServerSocket socket1 = new ServerSocket(port);
			System.out.println("Server Initialized");
			while (true) {
				Socket connection = socket1.accept();
				MultipleSocketServer runnable = new MultipleSocketServer(connection, ++count, this);
				myConnections.add(runnable);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
		catch (Exception e) {}
	}
	
	public Collection<MultipleSocketServer> getConnections(){
		System.out.println("Number of connections = "+myConnections.size());
		for(MultipleSocketServer s : myConnections)
			System.out.println(s.toString());
		return myConnections;
	}
	public void broadCastMessage(String str) {
		for(MultipleSocketServer s : this.getConnections()){
			s.sendMessage(str);
		}
	}

}
