package server;

import java.io.InputStreamReader;

public class ClientReader extends Thread implements ServerConstants{
	private InputStreamReader myISR;
	private SocketClient mySC;
	
	public ClientReader(InputStreamReader isr, SocketClient sc){
		myISR = isr;
		mySC = sc;
	}
	public void run(){
		int c;
		System.out.println("Client Reader running.");
		
		try{
			while(true){
				StringBuffer chat = new StringBuffer();
				while ( (c = myISR.read()) != CARRIAGE_RETURN)
					chat.append( (char) c);
				mySC.printMessage(chat.toString());
			}
		}
		catch (Exception e){
			System.out.println("ClientReader failed.");
		}
	}
}
