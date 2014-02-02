package server;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */
public class SocketClient extends Thread implements ServerConstants{
	public SocketClient() {
	}
	
	public void run(){
		/** Define a host server */
		//String host = "localhost";
		/** Define a port */
		int port = 19999;

		StringBuffer instr = new StringBuffer();
		String TimeStamp;
		System.out.println("SocketClient initialized");
		try {
			/** Obtain an address object of the server */
			//InetAddress address = InetAddress.getByName(host);
			//System.out.println("Address is: "+address.toString());

			InetAddress address = InetAddress.getByName("10.190.49.104");
			System.out.println("Address is: "+InetAddress.getLocalHost().getHostAddress());

			/** Establish a socket connection */
			Socket connection = new Socket(address, port);
			/** Instantiate a BufferedOutputStream object */
			BufferedOutputStream bos = new BufferedOutputStream(connection.
					getOutputStream());

			/** Instantiate an OutputStreamWriter object with the optional character
			 * encoding.
			 */
			OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
			TimeStamp = new java.util.Date().toString();
			//String process = "Calling the Socket Server on "+ address.getHostAddress() + " port " + port + "at " + TimeStamp +  (char) 13;
			String process = "name Timo"+CARRIAGE_RETURN;
			/** Write across the socket connection and flush the buffer */
			osw.write(process);
			osw.flush();

			/** Instantiate a BufferedInputStream object for reading
	            /** Instantiate a BufferedInputStream object for reading
			 * incoming socket streams.
			 */

			BufferedInputStream bis = new BufferedInputStream(connection.
					getInputStream());
			/**Instantiate an InputStreamReader with the optional
			 * character encoding.
			 */

			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

			/**Read the socket's InputStream and append to a StringBuffer */
			int c;
			while ( (c = isr.read()) != CARRIAGE_RETURN)
				instr.append( (char) c);
			System.out.println(instr);

			List<DummyClient> dummies = new ArrayList<DummyClient>();
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			ClientReader myReader = new ClientReader(isr,this);
			myReader.start();
			
			while(true){
				String str=br.readLine();
				if (str.endsWith("exit")){
					break;
				}
				else if (str.endsWith("dummy")){
					dummies.add(new DummyClient());
				}
				else {
					osw.write(str+ CARRIAGE_RETURN);
					osw.flush();
				}
			}
			
			for (DummyClient d : dummies){
				d.closeMe();
			}
			/** Close the socket connection. */
			connection.close();
		}
		catch (IOException f) {
			System.out.println("IOException: " + f);
		}
		catch (Exception g) {
			System.out.println("Exception: " + g);
		}
	}
	
	public void printMessage(String str){
		System.out.println(str);
	}
}