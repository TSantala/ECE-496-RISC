package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread implements ServerConstants{
	
	HashSet<MultipleSocket> myConnections = new HashSet<MultipleSocket>();
	
	public Server(){
	}
	
	public void run(){
		
		int port = 19999;
		int count = 0;
		try{
			ServerSocket socket1 = new ServerSocket(port);
			System.out.println("Server Initialized");
			while (true) {
				Socket connection = socket1.accept();
				MultipleSocket runnable = new MultipleSocket(connection, ++count, this);
				myConnections.add(runnable);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
		catch (Exception e) {}
	}
	
	public Collection<MultipleSocket> getConnections(){
		//System.out.println("Number of connections = "+myConnections.size());
		return myConnections;
	}
	
	public void broadCastMessage(String str) {
		for(MultipleSocket s : this.getConnections()){
			s.sendMessage(str + CARRIAGE_RETURN);
		}
	}

}
