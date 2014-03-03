package server;

import gameElements.GameInfo;
import gameElements.GameState;
import gui.GameGUI;

import java.net.*;
import java.util.Collection;
import java.io.*;

import javax.swing.JOptionPane;

public class ObjectClient extends Thread implements ServerConstants{
	private GameGUI myGUI;
	private GameState myGame;
	private ObjectOutputStream oos;
	private ServerPlayer myPlayer;

	public ObjectClient(){

	}

	public synchronized void run(){
		int port = 19999;

		System.out.println("ObjectClient initialized");
		try {

			InetAddress address = InetAddress.getByName("10.190.219.83");
			System.out.println("Address is: "+InetAddress.getLocalHost().getHostAddress());

			Socket connection = new Socket(address, port);
			oos = new ObjectOutputStream(connection.getOutputStream());

			ObjectClientReader myObjectReader = new ObjectClientReader(connection,this);
			myObjectReader.start();

			myGUI = new GameGUI(this);
			myGUI.run();

			InitialConnect initial = new InitialConnect();
			this.sendMessage(initial);

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
			oos.reset();
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

/*	public void promptPlayers(){
		int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("You are beginning a new game.  How many players?" ,"2"));
		InitialConnect ic = new InitialConnect("");
		ic.setHost();
		ic.setNumPlayers(numPlayers);
		this.sendMessage(ic);
	}*/

	public GameState getGameState(){
		return myGame;
	}

	public GameGUI getGUI(){
		return myGUI;
	}

	public synchronized void closeClient(){
		this.notify();
	}

	public void setPlayer(String name, String pass) {
		myPlayer = new ServerPlayer(name, pass);
		myGUI.setPlayer(myPlayer);
	}

	public void promptTerritories() {
		this.printMessage("Please left click to assign a unit to a territory; right click to remove.");
		myGUI.getGameGraphic().assignUnits();
	}

	public void updateGameInfo(Collection<GameInfo> myUpdate) {
		myGUI.updateGameInfo(myUpdate);
	}
}