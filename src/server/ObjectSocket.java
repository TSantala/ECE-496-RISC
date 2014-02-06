package server;

import java.net.*;
import java.io.*;

public class ObjectSocket extends Thread implements ServerConstants{

	private Socket myConnection;
	//private String myTimeStamp;
	private ObjectServer myServer;
	//private int myID;
	private ObjectOutputStream oos ;
	private ObjectInputStream ois;
	private String id;

	public ObjectSocket(Socket socket, int id, ObjectServer server) {
		myConnection = socket;
		//myID = id;
		myServer = server;
		try {
			ois = new ObjectInputStream(myConnection.getInputStream());
			oos = new ObjectOutputStream(myConnection.getOutputStream());
		} catch (IOException e){
			System.out.println("Initializing the Object Socket Server input and output streams failed.");
		}

	}

	public synchronized void sendMessage(Message m) {
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			System.out.println("Object output stream broke in the thread.");
		}

	}

	@Override
	public void run() {
		while(true){
			try {
				Message m = (Message)ois.readObject();
				String process = m.getMessage();
				/*String[] input = process.split(" ");
				if (input[0].equals("name")){
					id = input[1];
					continue;
				}*/
				if (process.equals("exit")){
					myConnection.close();
					System.out.println("Server is closing the connection " + id);
					myServer.removeConnection(this);
					break;
				}

				System.out.println(process);

				myServer.broadCastMessage(new Message(id + ": " +m.getMessage()));

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