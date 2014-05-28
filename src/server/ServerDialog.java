package server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class ServerDialog {
	
	private ObjectServer myServer;
	
	public ServerDialog(ObjectServer os){
		myServer = os;
		
        final JFrame frame = new JFrame("Server Running");
        try {
        	JTextArea myText = new JTextArea("  Server Address is: "+InetAddress.getLocalHost().getHostAddress()+"\n"+
        		"  Close this dialog to close server!", 20, 10);
        	myText.setEditable(false);
			frame.add(myText);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            	System.out.println("Closing called!");
                myServer.close();
                frame.dispose();
            }
        });
        frame.setSize(300,100);
        frame.setVisible(true);
	}

}
