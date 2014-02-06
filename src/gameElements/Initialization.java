package gameElements;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import server.Message;
import server.ObjectClient;

/*
 * This is the class that is called when the game begins- has to be passed number of players 
 */
public class Initialization implements Runnable
{
	private JTextField input;
	private JTextArea output;
	private ObjectClient myClient;

	public Initialization(ObjectClient client){
		myClient = client;
	}

	@Override
	public void run() {
		JFrame f = new JFrame("RISC");
		f.setSize(1000, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		Container pane = f.getContentPane();

		output = new JTextArea(5, 40);
		JScrollPane scrollingOutput = new JScrollPane(output); 
		output.setEditable(false);

		input = new JTextField(40);
		input.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				myClient.sendMessage(new Message(input.getText()));
				input.setText("");
			}			
		});

		JPanel b1 = new JPanel(); //will become visual component

		pane.add(b1,BorderLayout.PAGE_START);
		pane.add(scrollingOutput, BorderLayout.CENTER);
		pane.add(input,BorderLayout.PAGE_END);

		f.pack();
		f.setVisible(true);

	}
	
	public void printMessage(String s){
		output.setText(output.getText() + "\n" + s);
	}

}
