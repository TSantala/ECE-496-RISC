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
    private OutputStreamWriter osw ;
    private InputStreamReader isr;
    
	MultipleSocketServer(Socket socket, int id, Server server) {
		myConnection = socket;
		myID = id;
		myServer = server;
		try {
		BufferedInputStream is = new BufferedInputStream(myConnection.getInputStream());
		isr = new InputStreamReader(is);
		BufferedOutputStream os = new BufferedOutputStream(myConnection.getOutputStream());
		osw = new OutputStreamWriter(os, "US-ASCII");
		} catch (IOException e){
			System.out.println("Initializing the Multiple Socket Server input and output streams failed.");
		}
		
	}
	
    public synchronized void sendMessage(String m) {
    	try {
			osw.write(m);
			osw.flush();
		} catch (IOException e) {
			System.out.println("Output stream buffer broke in the thread.");
		}
        
    }
	
	@Override
	public void run() {
		while(true){
			try {
				int character;
				StringBuffer process = new StringBuffer();
				while((character = isr.read()) != 13) {
					process.append((char)character);
				}
				System.out.println(process);

				//myTimeStamp = new java.util.Date().toString();
				//String returnCode = "Timo test. MultipleSocketServer responded to "+myConnection.getPort()+" at "+ myTimeStamp;
				String returnCode = "Test ";

				myServer.broadCastMessage(returnCode + "THIS IS WHAT YOU SENT: " + process + (char) 13);
				

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
			
			/*synchronized(this){
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
		}
	}
}