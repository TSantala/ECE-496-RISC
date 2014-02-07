package server;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ObjectClientReader extends Thread implements ServerConstants{
	private ObjectClient myOC;
	private Socket mySocket;

	public ObjectClientReader(Socket sc, ObjectClient OC){
		mySocket = sc;
		myOC = OC;
	}
	
	public void run(){
		System.out.println("ObjectClientReader running.");
		try{
			ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
			while(true){
				Message m = (Message) ois.readObject();
				m.sendMessageToClient(myOC);
			}
		}
			catch (Exception e){
				System.out.println("ObjectClientReader failed.");
				e.printStackTrace();
			}
		}
}