package main;

import javax.swing.SwingUtilities;

import gameElements.Initialization;
import server.Server;

public class Main {

	public static void main(String[] args){
		Initialization i = new Initialization();
		SwingUtilities.invokeLater(i);
		//new Server();
	}
}
