package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
	
	HashSet<Socket> myConnections = new HashSet<Socket>();
	
	public Server(){
		
		int port = 19999;
		int count = 0;
		try{
			ServerSocket socket1 = new ServerSocket(port);
			System.out.println("Server Initialized");
			while (true) {
				Socket connection = socket1.accept();
				myConnections.add(connection);
				Runnable runnable = new MultipleSocketServer(connection, ++count, this);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
		catch (Exception e) {}
	}
	
	public Collection<Socket> getConnections(){
		System.out.println("Number of connections = "+myConnections.size());
		for(Socket s : myConnections)
			System.out.println(s.toString());
		return myConnections;
	}

}
