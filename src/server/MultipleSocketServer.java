package server;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class MultipleSocketServer extends Thread {

	private Socket myConnection;
	private String myTimeStamp;
	private Server myServer;
	private int myID;

	MultipleSocketServer(Socket socket, int id, Server server) {
		myConnection = socket;
		myID = id;
		myServer = server;
	}

	@Override
	public void run() {
		while(true){
			try {
				BufferedInputStream is = new BufferedInputStream(myConnection.getInputStream());
				InputStreamReader isr = new InputStreamReader(is);
				int character;
				StringBuffer process = new StringBuffer();
				while((character = isr.read()) != 13) {
					process.append((char)character);
				}
				System.out.println(process);

				//myTimeStamp = new java.util.Date().toString();
				//String returnCode = "Timo test. MultipleSocketServer responded to "+myConnection.getPort()+" at "+ myTimeStamp;
				String returnCode = "Test ";

				for(Socket s : myServer.getConnections()){
					BufferedOutputStream os = new BufferedOutputStream(s.getOutputStream());
					OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
					osw.write(returnCode + "THIS IS WHAT YOU SENT: " + process + (char) 13);
					osw.flush();
				}

			}
			catch (Exception e) {
				System.out.println(e);
			}
			//		{
			//			try {
			//				myConnection.close();
			//			}
			//			catch (IOException e){}
			//		}
			
			synchronized(this){
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}