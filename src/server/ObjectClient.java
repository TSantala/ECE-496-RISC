package server;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */
public class ObjectClient extends Thread implements ServerConstants{
	public ObjectClient() {
	}

	public void run(){
		int port = 19999;

		System.out.println("ObjectClient initialized");
		try {

			InetAddress address = InetAddress.getByName("10.190.84.134");
			System.out.println("Address is: "+InetAddress.getLocalHost().getHostAddress());

			Socket connection = new Socket(address, port);
			ObjectOutputStream oos = new ObjectOutputStream(connection.
					getOutputStream());
			oos.writeObject(new Message("name John"));

			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			ObjectClientReader myReader = new ObjectClientReader(connection,this);
			myReader.start();

			while(true){
				String str=br.readLine();
				oos.writeObject(new Message(str));
				oos.flush();
				if (str.endsWith("exit")){
					break;
				}
			}
			
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
		System.out.println(m.getMessage());
	}
}