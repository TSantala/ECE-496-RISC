package server;

import gameElements.Initialization;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.swing.SwingUtilities;

/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */
public class ObjectClient extends Thread implements ServerConstants{
	private Initialization myGUI;
	private ObjectOutputStream oos;
	
	public ObjectClient() {
	}

	public synchronized void run(){
		int port = 19999;

		System.out.println("ObjectClient initialized");
		try {

			InetAddress address = InetAddress.getByName("10.190.50.220");
			System.out.println("Address is: "+InetAddress.getLocalHost().getHostAddress());

			Socket connection = new Socket(address, port);
			oos = new ObjectOutputStream(connection.
					getOutputStream());
			//oos.writeObject(new Message("name John"));

			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			ObjectClientReader myReader = new ObjectClientReader(connection,this);
			myReader.start();
			
			myGUI = new Initialization(this);
			SwingUtilities.invokeLater(myGUI);

			/*while(true){
				String str=br.readLine();
				oos.writeObject(new Message(str));
				oos.flush();
				if (str.endsWith("exit")){
					break;
				}
			}*/
			
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
	public void printMessage(Message m){
		myGUI.printMessage(m.getMessage());
		System.out.println(m.getMessage());
	}
	
	public void sendMessage(Message m){
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			System.out.println("ObjectClient could not send the message.");
		}
	}
	
	public synchronized void closeClient(){
		this.notify();
	}
}