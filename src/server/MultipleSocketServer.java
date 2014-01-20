package server;

import java.net.*;
import java.io.*;

public class MultipleSocketServer implements Runnable {

	private Socket myConnection;
	private String myTimeStamp;
	private int myID;
	
	public static void main(String[] args) {
		int port = 19999;
		int count = 0;
		try{
			ServerSocket socket1 = new ServerSocket(port);
			System.out.println("MultipleSocketServer Initialized");
			while (true) {
				Socket connection = socket1.accept();
				Runnable runnable = new MultipleSocketServer(connection, ++count);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
		catch (Exception e) {}
	}
	MultipleSocketServer(Socket s, int i) {
		myConnection = s;
		myID = i;
	}

	@Override
	public void run() {
		try {
			BufferedInputStream is = new BufferedInputStream(myConnection.getInputStream());
			InputStreamReader isr = new InputStreamReader(is);
			int character;
			StringBuffer process = new StringBuffer();
			while((character = isr.read()) != 13) {
				process.append((char)character);
			}
			System.out.println(process);
			//need to wait 10 seconds to pretend that we're processing something
			try {
				Thread.sleep(2000);
			}
			catch (Exception e){}
			myTimeStamp = new java.util.Date().toString();
			String returnCode = "MultipleSocketServer responded to "+myConnection.getPort()+" at "+ myTimeStamp + (char) 13;
			BufferedOutputStream os = new BufferedOutputStream(myConnection.getOutputStream());
			OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
			osw.write(returnCode);
			osw.flush();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			try {
				myConnection.close();
			}
			catch (IOException e){}
		}
	}
}