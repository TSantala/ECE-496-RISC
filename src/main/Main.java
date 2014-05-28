package main;


import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import server.ObjectClient;
import server.ObjectServer;
import server.ServerDialog;

public class Main {

	public static void main(String[] args){
		
		ObjectServer myServer = new ObjectServer(false);
		myServer.start();
		
		new ServerDialog(myServer);

//		String serverAddress =  JOptionPane.showInputDialog("What server would you like to connect to?" ,"ADDRESS");
//
//		ObjectClient myClient1 = new ObjectClient(serverAddress);
//		myClient1.start();


	}
}
