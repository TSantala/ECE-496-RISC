package gui;

import gameElements.GameState;
import gameElements.ServerGame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import server.Message;
import server.ObjectClient;

/*
 * This is the class that is called when the game begins- has to be passed number of players 
 */
public class GameGUI extends JFrame
{
	private JTextField input;
	private JTextArea output;
	private JScrollPane scrollingOutput;
	private ObjectClient myClient;

	public GameGUI(ObjectClient client){
		myClient = client;
	}

	public void run() {
		JFrame f = new JFrame("RISC");
		f.setSize(1000, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		Container pane = f.getContentPane();
		
		JPanel subPane = new JPanel();
		subPane.setLayout(new BorderLayout());

		output = new JTextArea(5, 40);
		scrollingOutput = new JScrollPane(output); 
		output.setEditable(false);

		input = new JTextField(40);
		input.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				myClient.sendMessage(new Message(input.getText()));
				input.setText("");
			}			
		});

		GameGraphic game = new GameGraphic(this,new GameState(2,6)); 
		subPane.add(game,BorderLayout.CENTER);
		subPane.add(scrollingOutput,BorderLayout.SOUTH);
		
		pane.add(subPane,BorderLayout.CENTER);
		pane.add(input,BorderLayout.PAGE_END);

		f.setVisible(true);

	}
	
	public void printMessage(String s){
		output.setText(output.getText() + "\n" + s);
		output.revalidate();
		output.setCaretPosition(output.getDocument().getLength());
	}

}
