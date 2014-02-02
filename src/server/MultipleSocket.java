package server;

import java.net.*;
import java.io.*;

public class MultipleSocket extends Thread implements ServerConstants{

	private Socket myConnection;
	//private String myTimeStamp;
	private Server myServer;
	//private int myID;
    private OutputStreamWriter osw ;
    private InputStreamReader isr;
    private String id;
    
	MultipleSocket(Socket socket, int id, Server server) {
		myConnection = socket;
		//myID = id;
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
				while((character = isr.read()) != CARRIAGE_RETURN) {
					process.append((char)character);
					//System.out.println((char)character);
				}
				String[] input = process.toString().split(" ");
				if (input[0].equals("name"))
					id = input[1];
				if (process.toString().equals("exit")){
					myConnection.close();
					System.out.println("Server is closing the connection " + id);
					myServer.removeConnection(this);
					break;
				}
					
				System.out.println(process);
				
				myServer.broadCastMessage(id + ": " + process.toString() + CARRIAGE_RETURN);

			}
			catch (Exception e) {
				System.out.println(e);
				try {
					myConnection.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					break;
				}
				break;
			}
		}
	}
}