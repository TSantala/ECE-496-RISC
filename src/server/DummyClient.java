package server;

import java.net.*;
import java.io.*;

/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */
public class DummyClient implements ServerConstants{
	private Socket myConnection;
	private OutputStreamWriter osw;
	
	public DummyClient(){
		int port = 19999;

		System.out.println("DummyClient initialized.");
		try {
			/** Obtain an address object of the server */
			
			InetAddress address = InetAddress.getByName("10.190.49.104");
			
			/** Establish a socket connection */
			myConnection = new Socket(address, port);
			/** Instantiate a BufferedOutputStream object */
			BufferedOutputStream bos = new BufferedOutputStream(myConnection.
					getOutputStream());

			/** Instantiate an OutputStreamWriter object with the optional character
			 * encoding.
			 */
			osw = new OutputStreamWriter(bos, "US-ASCII");
			String process = "name " + Double.toString(Math.random())+CARRIAGE_RETURN;
			/** Write across the socket connection and flush the buffer */
			osw.write(process);
			osw.flush();
			
		}
		catch (IOException f) {
			System.out.println("IOException: " + f);
		}
		catch (Exception g) {
			System.out.println("Exception: " + g);
		}
	}
	
	public void closeMe(){
		try {
			System.out.println("dummy is closing itself");
			osw.write("exit"+CARRIAGE_RETURN);
			osw.flush();
			myConnection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}