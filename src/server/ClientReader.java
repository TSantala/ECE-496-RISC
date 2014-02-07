package server;

import java.io.InputStreamReader;

public class ClientReader extends Thread implements ServerConstants{
	private InputStreamReader myISR;
	private ObjectClient myOC;

	public ClientReader(InputStreamReader isr, ObjectClient oc){
		myISR = isr;
		myOC = oc;
	}
	public void run(){
		int c;
		System.out.println("Client Reader running.");

		try{
			while(true){
				StringBuffer chat = new StringBuffer();
				while ( (c = myISR.read()) != CARRIAGE_RETURN)
					chat.append( (char) c);
				myOC.printMessage(chat.toString());
			}
		}
		catch (Exception e){
			System.out.println("ClientReader failed.");
		}
	}
}
