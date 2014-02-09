package server;

import gameElements.GameState;
import gui.GameGUI;

import java.net.*;
import java.io.*;

public class ObjectClient extends Thread implements ServerConstants{
	private GameGUI myGUI;
	private GameState myGame;
	private ObjectOutputStream oos;
	
	public ObjectClient(GameState gs) {
		myGame = gs;
	}

	public synchronized void run(){
		int port = 19999;

		System.out.println("ObjectClient initialized");
		try {

			InetAddress address = InetAddress.getByName("192.168.56.1");
			System.out.println("Address is: "+InetAddress.getLocalHost().getHostAddress());

			Socket connection = new Socket(address, port);
			oos = new ObjectOutputStream(connection.getOutputStream());
			
			ObjectClientReader myObjectReader = new ObjectClientReader(connection,this);
			myObjectReader.start();

			myGUI = new GameGUI(this);
			myGUI.run();
			
			this.wait();
			
			connection.close();
		}
		catch (IOException f) {
			System.out.println("IOException: " + f);
		}
		catch (Exception g) {
			System.out.println("Exception: " + g);
		}
	}
	
	public void printMessage(String s){
		myGUI.printMessage(s);
	}
	
	public void sendMessage(Message m){
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			System.out.println("ObjectClient could not send the message.");
		}
	}
	
	public void receiveGameState(GameState gs){
		myGame = gs;
		myGUI.updateGameState(gs);
	}
	
	public GameState getGameState(){
		return myGame;
	}
	
	public GameGUI getGUI(){
		return myGUI;
	}
	
	public synchronized void closeClient(){
		this.notify();
	}
}