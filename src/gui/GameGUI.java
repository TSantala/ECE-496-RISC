package gui;

import gameElements.CommandList;
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
	private GameState myGame;

	public GameGUI(ObjectClient client){
		myClient = client;
		myGame = myClient.getGameState();
		System.out.println("2");
	}
	
	public void updateGameState(GameState gs){
		myGame = gs;
	}

	public void run() {
		System.out.println("3");
		
		JFrame f = new JFrame("RISC");
		f.setSize(1000, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		Container pane = f.getContentPane();
		
		System.out.println("4");
		
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
		
		System.out.println("5");
		GameGraphic game = new GameGraphic(this,myGame); 
		subPane.add(game,BorderLayout.CENTER);
		subPane.add(scrollingOutput,BorderLayout.SOUTH);
		
		System.out.println("6");
		pane.add(subPane,BorderLayout.CENTER);
		pane.add(input,BorderLayout.PAGE_END);

		f.setVisible(true);
	}
	
	public void printMessage(String s){
		output.setText(output.getText() + "\n" + s);
		output.revalidate();
		output.setCaretPosition(output.getDocument().getLength());
	}
	
	public void sendCommandList(CommandList cl){
		myClient.sendCommandList(cl);
	}

}
