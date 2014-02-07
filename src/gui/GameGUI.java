package gui;

import gameElements.AttackCommand;
import gameElements.Command;
import gameElements.CommandList;
import gameElements.GameState;
import gameElements.Player;
import gameElements.Territory;
import gameElements.Unit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import server.ObjectClient;
import server.ServerConstants;
import server.TextMessage;

/*
 * This is the class that is called when the game begins- has to be passed number of players 
 */
public class GameGUI extends JFrame implements ServerConstants {
	
	private JTextField input;
	private JTextArea output;
	private JTextArea tileInfo;
	private JScrollPane scrollingOutput;
	private ObjectClient myClient;
	
	private GameState myGame;
	private Player myPlayer;
	
	private Territory leftClick;		// maybe hue territory color with blue?
	private Territory rightClick;		// maybe hue territory color with red?
	private List<Unit> selectedUnits = new ArrayList<Unit>();;
	
	private CommandList myCommandList = new CommandList();

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
		f.setSize(800, 600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		Container pane = f.getContentPane();
		
		System.out.println("4");
		
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BorderLayout());
		
		JPanel textPane = new JPanel();
		textPane.setLayout(new BorderLayout());

		output = new JTextArea(5, 40);
		output.setEditable(false);
		scrollingOutput = new JScrollPane(output);
		textPane.add(scrollingOutput,BorderLayout.CENTER);

		input = new JTextField(40);
		input.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				myClient.sendMessage(new TextMessage(input.getText()));
				input.setText("");
			}			
		});
		textPane.add(input,BorderLayout.SOUTH);
		bottomPane.add(textPane,BorderLayout.CENTER);
		bottomPane.add(new CommitButton(this),BorderLayout.EAST);
		
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		GameGraphic game = new GameGraphic(this,myGame);
		mainPane.add(game,BorderLayout.CENTER);
		
		JPanel rightPane = new JPanel();
		rightPane.setLayout(new BorderLayout());
		tileInfo = new JTextArea(7, 7);
		tileInfo.setEditable(false);
		rightPane.add(tileInfo,BorderLayout.CENTER);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BorderLayout());
		buttonPane.add(new MoveButton(this,leftClick,rightClick,selectedUnits),BorderLayout.NORTH);
		buttonPane.add(new AttackButton(this,leftClick,rightClick,selectedUnits),BorderLayout.SOUTH);
		
		rightPane.add(buttonPane,BorderLayout.SOUTH);
		
		mainPane.add(rightPane,BorderLayout.EAST);
		
		System.out.println("6");
		pane.add(bottomPane,BorderLayout.SOUTH);
		pane.add(mainPane,BorderLayout.CENTER);

		f.setVisible(true);
	}
	
	public void printMessage(String s){
		output.setText(output.getText() + "\n" + s);
		output.revalidate();
		output.setCaretPosition(output.getDocument().getLength());
	}
	
	public void addCommand(Command c){
		myCommandList.addCommand(c);
	}
	
	public void sendCommandList(){
		// pop up an "Are you sure?" message maybe?
		myClient.sendMessage(myCommandList);
		myCommandList.getCommands().clear();
	}
	
	public Player getPlayer(){
		return myPlayer;
	}

}
