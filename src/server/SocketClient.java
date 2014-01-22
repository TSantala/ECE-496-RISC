package server;

import java.net.*;
import java.io.*;

/** The SocketClient class is a simple example of a TCP/IP Socket Client.
 *
 */
public class SocketClient {
	public static void main(String[] args) {
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
			
			InetAddress address = InetAddress.getByName("10.190.72.198");
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
			String process = "Calling the Socket Server on "+ address.getHostAddress() + " port " + port + "at" + TimeStamp +  (char) 13;

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
			while ( (c = isr.read()) != 13)
				instr.append( (char) c);
			System.out.println(instr);
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			while(true){
				String str=br.readLine();
				if (str.endsWith("exit"))
					break;
				/*Socket johnConnection = new Socket(address, port);
				BufferedOutputStream johnStream = new BufferedOutputStream(johnConnection.
						getOutputStream());
				OutputStreamWriter myWriter = new OutputStreamWriter(johnStream, "US-ASCII");
				myWriter.write(str+(char) 13);
				myWriter.flush();*/
				
				osw.write(str+(char) 13);
				osw.flush();
				/*StringBuffer johnstr = new StringBuffer();
				BufferedInputStream johnInput = new BufferedInputStream(johnConnection.
						getInputStream());
				InputStreamReader jsr = new InputStreamReader(johnInput, "US-ASCII");
				int j;
				while ( (j = jsr.read()) != 13)
					johnstr.append( (char) j);
				System.out.println(johnstr);
				johnConnection.close();*/
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
}