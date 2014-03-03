package server;

import java.net.*;
import java.io.*;

public class ObjectSocket extends Thread implements ServerConstants{

	private Socket myConnection;
	private ObjectServer myServer;
	private ObjectOutputStream oos ;
	private ObjectInputStream ois;

	public ObjectSocket(Socket socket, int id, ObjectServer server) {
		myConnection = socket;
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
			oos.reset();
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
				m.setObjectSocket(this);
				m.sendMessageToServer(myServer);
			}
			catch (Exception e) {
				System.out.println("The socket connection was closed unexpectedly");
				myServer.removeConnection(this);
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