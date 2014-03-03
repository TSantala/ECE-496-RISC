package server;

import gameElements.AddUnitCommand;
import gameElements.CommandList;
import gameElements.GameState;
import gameElements.Player;
import gameElements.Territory;

import java.net.*;
import java.io.*;

public class ObjectSocket extends Thread implements ServerConstants{

	private Socket myConnection;
	//private String myTimeStamp;
	private ObjectServer myServer;
	//private int myID;
	private ObjectOutputStream oos ;
	private ObjectInputStream ois;
	//private String id;

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
			oos.reset();
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			System.out.println("Object output stream broke in the thread.");
		}
	}

	/*public synchronized void promptNewGame(){
		try{
			InitialConnect ic = new InitialConnect("");
			ic.setHost();
			oos.writeObject(ic);
			oos.flush();
		} catch (IOException e) {
			System.out.println("Failed to prompt first user for a new game.");
		}
	}*/

	@Override
	public void run() {/*
		try {
			InitialConnect initial = (InitialConnect)ois.readObject();
			myServer.initialConnect(this, initial);
		} catch (Exception e){
			System.out.println("Error reading initial client state");
		}*/

		while(true){
			try {
				Message m = (Message)ois.readObject();
				m.setObjectSocket(this);
				m.sendMessageToServer(myServer);
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